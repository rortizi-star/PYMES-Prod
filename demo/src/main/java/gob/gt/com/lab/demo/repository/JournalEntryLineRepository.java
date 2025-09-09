package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.JournalEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryLineRepository extends JpaRepository<JournalEntryLine, Long> {
}
