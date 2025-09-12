package gob.gt.com.lab.demo.repository.audit;

import gob.gt.com.lab.demo.entity.audit.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUsername(String username);
    List<AuditLog> findByAction(String action);
    List<AuditLog> findByEntity(String entity);
    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
