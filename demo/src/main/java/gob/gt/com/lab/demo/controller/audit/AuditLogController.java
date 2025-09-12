package gob.gt.com.lab.demo.controller.audit;

import gob.gt.com.lab.demo.entity.audit.AuditLog;
import gob.gt.com.lab.demo.service.audit.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public List<AuditLog> getAll() {
        return auditLogService.getAll();
    }

    @GetMapping("/user/{username}")
    public List<AuditLog> getByUsername(@PathVariable String username) {
        return auditLogService.getByUsername(username);
    }

    @GetMapping("/action/{action}")
    public List<AuditLog> getByAction(@PathVariable String action) {
        return auditLogService.getByAction(action);
    }

    @GetMapping("/entity/{entity}")
    public List<AuditLog> getByEntity(@PathVariable String entity) {
        return auditLogService.getByEntity(entity);
    }

    @GetMapping("/date-range")
    public List<AuditLog> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return auditLogService.getByDateRange(start, end);
    }
}
