package com.pruu.pombo.model.entity;

import com.pruu.pombo.model.enums.Reason;
import com.pruu.pombo.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Reason reason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

}
