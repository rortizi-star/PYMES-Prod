package gob.gt.com.lab.demo.service.accounts;

import gob.gt.com.lab.demo.entity.accounts.*;
import gob.gt.com.lab.demo.entity.Supplier;
import gob.gt.com.lab.demo.entity.Customer;
import gob.gt.com.lab.demo.repository.accounts.*;
import gob.gt.com.lab.demo.repository.SupplierRepository;
import gob.gt.com.lab.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {
    @Autowired
    private AccountPayableRepository accountPayableRepository;
    @Autowired
    private AccountReceivableRepository accountReceivableRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // --- Cuentas por pagar ---
    public List<AccountPayable> getAllPayables() {
        return accountPayableRepository.findAll();
    }
    public Optional<AccountPayable> getPayable(Long id) {
        return accountPayableRepository.findById(id);
    }
    public List<AccountPayable> getPayablesBySupplier(Long supplierId) {
        return accountPayableRepository.findBySupplierId(supplierId);
    }
    public List<AccountPayable> getPayablesByDueDate(LocalDate start, LocalDate end) {
        return accountPayableRepository.findByDueDateBetween(start, end);
    }
    public List<AccountPayable> getPayablesBySettled(boolean settled) {
        return accountPayableRepository.findBySettled(settled);
    }
    @Transactional
    public AccountPayable createPayable(Long supplierId, Double amount, LocalDate dueDate, String description) throws Exception {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new Exception("Proveedor no encontrado"));
        if (amount == null || amount <= 0) throw new Exception("Monto inválido");
        AccountPayable ap = new AccountPayable();
        ap.setSupplier(supplier);
        ap.setAmount(amount);
        ap.setBalance(amount);
        ap.setDueDate(dueDate);
        ap.setDescription(description);
        ap.setSettled(false);
        return accountPayableRepository.save(ap);
    }
    @Transactional
    public Payment registerPayment(Long payableId, Double amount, String method, String reference) throws Exception {
        AccountPayable ap = accountPayableRepository.findById(payableId).orElseThrow(() -> new Exception("Cuenta por pagar no encontrada"));
        if (ap.isSettled()) throw new Exception("La cuenta ya está saldada");
        if (amount == null || amount <= 0) throw new Exception("Monto inválido");
        if (ap.getBalance() < amount) throw new Exception("El pago excede el saldo");
        ap.setBalance(ap.getBalance() - amount);
        if (ap.getBalance() == 0) ap.setSettled(true);
        accountPayableRepository.save(ap);
        Payment payment = new Payment();
        payment.setAccountPayable(ap);
        payment.setAmount(amount);
        payment.setDate(LocalDate.now());
        payment.setMethod(method);
        payment.setReference(reference);
        return paymentRepository.save(payment);
    }
    public List<Payment> getPaymentsByPayable(Long payableId) {
        return paymentRepository.findByAccountPayableId(payableId);
    }
    public List<Payment> getPaymentsByDate(LocalDate start, LocalDate end) {
        return paymentRepository.findByDateBetween(start, end);
    }

    // --- Cuentas por cobrar ---
    public List<AccountReceivable> getAllReceivables() {
        return accountReceivableRepository.findAll();
    }
    public Optional<AccountReceivable> getReceivable(Long id) {
        return accountReceivableRepository.findById(id);
    }
    public List<AccountReceivable> getReceivablesByCustomer(Long customerId) {
        return accountReceivableRepository.findByCustomerId(customerId);
    }
    public List<AccountReceivable> getReceivablesByDueDate(LocalDate start, LocalDate end) {
        return accountReceivableRepository.findByDueDateBetween(start, end);
    }
    public List<AccountReceivable> getReceivablesBySettled(boolean settled) {
        return accountReceivableRepository.findBySettled(settled);
    }
    @Transactional
    public AccountReceivable createReceivable(Long customerId, Double amount, LocalDate dueDate, String description) throws Exception {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new Exception("Cliente no encontrado"));
        if (amount == null || amount <= 0) throw new Exception("Monto inválido");
        AccountReceivable ar = new AccountReceivable();
        ar.setCustomer(customer);
        ar.setAmount(amount);
        ar.setBalance(amount);
        ar.setDueDate(dueDate);
        ar.setDescription(description);
        ar.setSettled(false);
        return accountReceivableRepository.save(ar);
    }
    @Transactional
    public Collection registerCollection(Long receivableId, Double amount, String method, String reference) throws Exception {
        AccountReceivable ar = accountReceivableRepository.findById(receivableId).orElseThrow(() -> new Exception("Cuenta por cobrar no encontrada"));
        if (ar.isSettled()) throw new Exception("La cuenta ya está saldada");
        if (amount == null || amount <= 0) throw new Exception("Monto inválido");
        if (ar.getBalance() < amount) throw new Exception("El cobro excede el saldo");
        ar.setBalance(ar.getBalance() - amount);
        if (ar.getBalance() == 0) ar.setSettled(true);
        accountReceivableRepository.save(ar);
        Collection collection = new Collection();
        collection.setAccountReceivable(ar);
        collection.setAmount(amount);
        collection.setDate(LocalDate.now());
        collection.setMethod(method);
        collection.setReference(reference);
        return collectionRepository.save(collection);
    }
    public List<Collection> getCollectionsByReceivable(Long receivableId) {
        return collectionRepository.findByAccountReceivableId(receivableId);
    }
    public List<Collection> getCollectionsByDate(LocalDate start, LocalDate end) {
        return collectionRepository.findByDateBetween(start, end);
    }

    // --- Reportes ---
    public Double getTotalPayablesPending() {
        return accountPayableRepository.findBySettled(false).stream().mapToDouble(AccountPayable::getBalance).sum();
    }
    public Double getTotalReceivablesPending() {
        return accountReceivableRepository.findBySettled(false).stream().mapToDouble(AccountReceivable::getBalance).sum();
    }
}
