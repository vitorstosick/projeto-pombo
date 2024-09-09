package com.pruu.pombo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;

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
    @Size(max = 300, message = "It must be max 300 characters")
    private String text;

    @CreationTimestamp
    private LocalDate date;

//    @ManyToMany(mappedBy = "messageList")
//    private Set<User> likes;

    //private Boolean isBlocked;
}
