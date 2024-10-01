package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.entity.Report;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String>, JpaSpecificationExecutor<Report> {
    boolean existsByMessageAndUser(Message message, User user);

    Integer countByMessage(Message message);

    Integer countByMessageAndStatus(Message message, Status status);

    List<Message> findByMessageId(String messageId);
}
