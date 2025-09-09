package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.Supplier;
import gob.gt.com.lab.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        return supplierRepository.findById(id).map(supplier -> {
            supplier.setName(supplierDetails.getName());
            supplier.setEmail(supplierDetails.getEmail());
            supplier.setPhone(supplierDetails.getPhone());
            supplier.setAddress(supplierDetails.getAddress());
            return supplierRepository.save(supplier);
        }).orElse(null);
    }

    public boolean deleteSupplier(Long id) {
        return supplierRepository.findById(id).map(supplier -> {
            supplierRepository.delete(supplier);
            return true;
        }).orElse(false);
    }
}
