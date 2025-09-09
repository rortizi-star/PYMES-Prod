package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.JournalEntry;
import gob.gt.com.lab.demo.entity.JournalEntryLine;
import gob.gt.com.lab.demo.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(Long id) {
        return journalEntryRepository.findById(id);
    }

    public JournalEntry saveJournalEntry(JournalEntry entry) {
        // Validar doble partida: suma debe = suma haber
        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;
        if (entry.getLines() != null) {
            for (JournalEntryLine line : entry.getLines()) {
                totalDebit = totalDebit.add(line.getDebit());
                totalCredit = totalCredit.add(line.getCredit());
                line.setJournalEntry(entry); // asegurar relación bidireccional
            }
        }
        if (totalDebit.compareTo(totalCredit) != 0) {
            throw new IllegalArgumentException("Debe = Haber. Suma de débitos y créditos no cuadra.");
        }
        return journalEntryRepository.save(entry);
    }

    public void deleteJournalEntry(Long id) {
        journalEntryRepository.deleteById(id);
    }
}
