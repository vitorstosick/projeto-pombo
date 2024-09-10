package com.pruu.pombo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email should be valid.")
    @Column(unique = true)
    private String email;

    @NotBlank
    @CPF
    @Column(unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Message> message;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
