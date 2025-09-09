package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
