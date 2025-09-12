package gob.gt.com.lab.demo.service.audit;

import gob.gt.com.lab.demo.entity.audit.AuditLog;
import gob.gt.com.lab.demo.repository.audit.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public AuditLog log(String username, String action, String entity, String entityId, String details, String ip) {
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        log.setIp(ip);
        return auditLogRepository.save(log);
    }

    public List<AuditLog> getAll() {
        return auditLogRepository.findAll();
    }

    public List<AuditLog> getByUsername(String username) {
        return auditLogRepository.findByUsername(username);
    }

    public List<AuditLog> getByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    public List<AuditLog> getByEntity(String entity) {
        return auditLogRepository.findByEntity(entity);
    }

    public List<AuditLog> getByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByTimestampBetween(start, end);
    }
}
