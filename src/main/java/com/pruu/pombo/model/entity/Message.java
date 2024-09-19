package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pruu.pombo.model.dto.MessageDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(max = 300, message = "Max 300 characters")
    private String text;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likes;

    private boolean isBlocked;

    @OneToMany(mappedBy = "message")
    @JsonBackReference
    private List<Report> reports;

    public static MessageDTO toDTO(Message message, Integer likes, Integer reports) {
        return new MessageDTO(
                message.getId(),
                message.getText(),
                message.isBlocked(),
                message.getUser().getId(),
                message.getUser().getName(),
                likes,
                reports
        );
    }

}
