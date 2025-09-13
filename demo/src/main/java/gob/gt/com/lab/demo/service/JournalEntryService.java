package gob.gt.com.lab.demo.service;

import gob.gt.com.lab.demo.entity.JournalEntry;
import gob.gt.com.lab.demo.entity.JournalEntryLine;
import gob.gt.com.lab.demo.repository.JournalEntryRepository;
import gob.gt.com.lab.demo.entity.period.AccountingPeriod;
import gob.gt.com.lab.demo.repository.period.AccountingPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private AccountingPeriodRepository periodRepository;

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(Long id) {
        return journalEntryRepository.findById(id);
    }

    public JournalEntry saveJournalEntry(JournalEntry entry) {
        // Validar que la fecha del asiento esté en un período ABIERTO
        java.util.Date entryDate = entry.getDate();
        List<AccountingPeriod> openPeriods = periodRepository.findByStatus("ABIERTO");
        boolean inOpenPeriod = false;
        for (AccountingPeriod period : openPeriods) {
            java.time.LocalDate start = period.getStartDate();
            java.time.LocalDate end = period.getEndDate();
            java.time.LocalDate entryLocalDate = entryDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            if ((entryLocalDate.isEqual(start) || entryLocalDate.isAfter(start)) && (entryLocalDate.isEqual(end) || entryLocalDate.isBefore(end))) {
                inOpenPeriod = true;
                break;
            }
        }
        if (!inOpenPeriod) {
            throw new IllegalArgumentException("No se puede registrar el asiento: la fecha no está en un período contable ABIERTO.");
        }
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
