package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.dto.MessageDTO;
import com.pruu.pombo.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String>, JpaSpecificationExecutor<Message> {
    List<Message> findByIsBlockedFalse();
}
