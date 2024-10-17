package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.dto.ReportDTO;
import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.entity.Report;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.enums.Status;
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

    @Autowired
    private UserService userService;

    public Report save(Report newReport) {
        return reportRepository.save(newReport);
    }

    public void delete(String id) throws PruuException {
        reportRepository.deleteById(id);
    }

    public Report findById(String id, String userId) throws PruuException {
        userService.isAdmin(userId);
        return reportRepository.findById(id).orElseThrow(() -> new PruuException("Report not found!"));
    }

    public List<Report> findAll(String userId) throws PruuException {
        userService.isAdmin(userId);
        return reportRepository.findAll();
    }

    public Report update(Report report, String userId) throws PruuException {
        userService.isAdmin(userId);
        return reportRepository.save(report);
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

    public Report reportMessage(String messageId, String userId, Report newReport) throws PruuException {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new PruuException("Message not found."));
        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));

        boolean alreadyReported = reportRepository.existsByMessageAndUser(message, user);
        if (alreadyReported) {
            throw new PruuException("User has already reported this message.");
        }

        newReport.setMessage(message);
        newReport.setUser(user);

        return reportRepository.save(newReport);
    }

    public ReportDTO findReportByMessage(String messageId) throws PruuException {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new PruuException("Message not found."));

        int totalReports = reportRepository.countByMessage(message);

        int pendingReports = reportRepository.countByMessageAndStatus(message, Status.PENDING);

        int analyzedReports = reportRepository.countByMessageAndStatus(message, Status.ANALYZED);

        return new ReportDTO(message.getId(), totalReports, pendingReports, analyzedReports);
    }

    public boolean changeStatusReport(String userId, String reportId) throws PruuException {
        userService.isAdmin(userId);

        Report report = reportRepository.findById(reportId).orElseThrow(() -> new PruuException("Report not found."));

        if (report.getStatus() == Status.PENDING) {
            report.setStatus(Status.ANALYZED);
            reportRepository.save(report);
            return true;
        } else {
            throw new PruuException("Report has already been analyzed.");
        }
    }
}
