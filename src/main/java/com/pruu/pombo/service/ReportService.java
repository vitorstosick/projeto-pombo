package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.Report;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.repository.MessageRepository;
import com.pruu.pombo.model.repository.ReportRepository;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.ReportSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    public Report save(Report newReport) {
        return reportRepository.save(newReport);
    }

    public boolean delete(String id) throws PruuException {
        reportRepository.deleteById(id);
        return true;
    }

    public Report findById(String id, String userId) throws PruuException {
        this.isAdmin(userId);
        return reportRepository.findById(id).orElseThrow(() -> new PruuException("Report not found!"));
    }

    public List<Report> findAll(String userId) throws PruuException {
        this.isAdmin(userId);
        return reportRepository.findAll();
    }

    public Report update(Report report, String userId) throws PruuException {
        this.isAdmin(userId);
        return reportRepository.save(report);
    }

    private void isAdmin(String userId) throws PruuException {
        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));

        if (user.getRole() == Role.USER) {
            throw new PruuException("Not an admin.");
        }
    }

    public List<Report> filters(ReportSelector reportSelector) {
        if (reportSelector.pagination()) {
            int numberPages = reportSelector.getPages();
            int size = reportSelector.getLimit();
            PageRequest page = PageRequest.of(numberPages - 1, size);
            return reportRepository.findAll(reportSelector, page).toList();
        }
        return reportRepository.findAll(reportSelector);
    }
}
