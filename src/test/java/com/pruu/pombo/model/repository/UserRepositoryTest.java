package com.pruu.pombo.model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.pruu.pombo.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setName("Usuario teste 1");
        user1.setCpf("02507924004");
        user1.setEmail("user1@mail.com");
        users.add(user1);

        User user2 = new User();
        user2.setName("Usuario teste 2");
        user2.setCpf("64712044004");
        user2.setEmail("user2@mail.com");
        users.add(user2);

        User user3 = new User();
        user3.setName("Usuario teste 3");
        user3.setCpf("96709769011");
        user3.setEmail("user3@mail.com");
        users.add(user3);

        userRepository.saveAll(users);
    }

    @Test
    public void testInsertAllArgumentsFilled() {
        // Arrange
        User user = new User();
        user.setName("Usuario teste");
        user.setCpf("66843867006");
        user.setEmail("usertest@mail.com");

        // Act
        User savedUser = userRepository.save(user);
        long totalUsers = userRepository.count();

        // Assert
        assertEquals(8, totalUsers);
        assertNotNull(savedUser);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Usuario teste");
        assertThat(savedUser.getCpf()).isEqualTo("66843867006");
        assertThat(savedUser.getEmail()).isEqualTo("usertest@mail.com");

    }

    @Test
    public void testDuplicatedCpf() {
        
    }

}
