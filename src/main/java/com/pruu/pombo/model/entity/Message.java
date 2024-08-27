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
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @NotBlank(message = "Tamanho do texto inválido!")
    @Size(min = 1, max = 300)
    private String text;

    @CreationTimestamp
    private LocalDate date;

    @OneToMany(mappedBy = "message")
    private Set<User> likes;

    private Boolean isBlocked;

}
