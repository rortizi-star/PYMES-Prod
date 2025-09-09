    public List<Invoice> getInvoicesByDateRange(java.time.LocalDate start, java.time.LocalDate end) {
    return invoiceRepository.findAll().stream()
        .filter(inv -> inv.getDate() != null &&
            !inv.getDate().isBefore(start) && !inv.getDate().isAfter(end))
        .toList();
    }

    public Double getTotalSalesByCustomer(Long customerId) {
    return invoiceRepository.findAll().stream()
        .filter(inv -> inv.getCustomer() != null && inv.getCustomer().getId().equals(customerId))
        .mapToDouble(inv -> inv.getTotal() != null ? inv.getTotal() : 0.0)
        .sum();
    }
package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.Invoice;
import gob.gt.com.lab.demo.entity.InvoiceLine;
import gob.gt.com.lab.demo.repository.InvoiceRepository;
import gob.gt.com.lab.demo.repository.InvoiceLineRepository;
import gob.gt.com.lab.demo.repository.CustomerRepository;
import gob.gt.com.lab.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceLineRepository invoiceLineRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        // Validar cliente
        if (invoice.getCustomer() == null || invoice.getCustomer().getId() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (!customerRepository.existsById(invoice.getCustomer().getId())) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        // Validar líneas
        if (invoice.getLines() == null || invoice.getLines().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos una línea de producto");
        }
        double total = 0.0;
        for (InvoiceLine line : invoice.getLines()) {
            if (line.getProduct() == null || line.getProduct().getId() == null) {
                throw new IllegalArgumentException("Cada línea debe tener un producto válido");
            }
            if (!productRepository.existsById(line.getProduct().getId())) {
                throw new IllegalArgumentException("El producto con ID " + line.getProduct().getId() + " no existe");
            }
            if (line.getQuantity() == null || line.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
            }
            if (line.getPrice() == null || line.getPrice() <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor a cero");
            }
            line.setInvoice(invoice);
            line.setSubtotal(line.getPrice() * line.getQuantity());
            total += line.getSubtotal();
        }
        invoice.setTotal(total);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        for (InvoiceLine line : invoice.getLines()) {
            invoiceLineRepository.save(line);
        }
        return savedInvoice;
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setCustomer(invoiceDetails.getCustomer());
            invoice.setDate(invoiceDetails.getDate());
            // Actualizar líneas y total
            invoice.getLines().clear();
            double total = 0.0;
            if (invoiceDetails.getLines() != null) {
                for (InvoiceLine line : invoiceDetails.getLines()) {
                    line.setInvoice(invoice);
                    line.setSubtotal(line.getPrice() * line.getQuantity());
                    total += line.getSubtotal();
                    invoice.getLines().add(line);
                }
            }
            invoice.setTotal(total);
            return invoiceRepository.save(invoice);
        }).orElse(null);
    }

    public boolean deleteInvoice(Long id) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoiceRepository.delete(invoice);
            return true;
        }).orElse(false);
    }
}
