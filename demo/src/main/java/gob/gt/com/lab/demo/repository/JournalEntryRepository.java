package gob.gt.com.lab.demo.repository;

import gob.gt.com.lab.demo.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
}
