package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setName("User test 1");
        user1.setCpf("02507924004");
        user1.setEmail("user1@mail.com");
        users.add(user1);

        User user2 = new User();
        user2.setName("User test 2");
        user2.setCpf("64712044004");
        user2.setEmail("user2@mail.com");
        users.add(user2);

        User user3 = new User();
        user3.setName("User test 3");
        user3.setCpf("96709769011");
        user3.setEmail("user3@mail.com");
        users.add(user3);

        userRepository.saveAll(users);
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void testInsertAllArgumentsFilled() {
        // Arrange
        User user = new User();
        user.setName("User test");
        user.setCpf("66843867006");
        user.setEmail("usertest@mail.com");

        // Act
        User savedUser = userRepository.save(user);
        long totalUsers = userRepository.count();

        // Assert
        assertEquals(4, totalUsers);
        assertNotNull(savedUser);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("User test");
        assertThat(savedUser.getCpf()).isEqualTo("66843867006");
        assertThat(savedUser.getEmail()).isEqualTo("usertest@mail.com");

    }

   /* @Test
    @DisplayName("Deve lançar exceção ao inserir usuário com CPF duplicado")
    public void testInserirCpfDuplicado() {
        User user = new User();
        user.setName("Usuario teste 4");
        user.setCpf("02507924004"); // CPF já existente
        user.setEmail("user4@mail.com");
        user.setRole(Role.USER);

        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(PersistenceException.class); // Ajuste conforme o seu ambiente

    }

    @Test
    @DisplayName("Deve lançar exceção ao inserir usuário com email duplicado")
    public void testInserirEmailDuplicado() {
        User user = new User();
        user.setName("Usuario teste 5");
        user.setCpf("12345678901"); // CPF fictício
        user.setEmail("user1@mail.com"); // Email já existente
        user.setRole(Role.USER);

        assertThatThrownBy(() -> userRepository.save(user))
                .isInstanceOf(PersistenceException.class) // Ajuste conforme o seu ambiente
                .hasMessageContaining("duplicate key value violates unique constraint");
    }*/

//    @Test
//    @DisplayName("Deve lançar exceção ao inserir usuário com CPF inválido")
//    public void testInserirCpfInvalido() {
//        User user = new User();
//        user.setName("Usuario com CPF inválido");
//        user.setCpf("12345678900"); // CPF inválido
//        user.setEmail("valid@mail.com");
//        user.setRole(Role.USER);
//
//        assertThatThrownBy(() -> userRepository.save(user))
//                .isInstanceOf(ConstraintViolationException.class);
//    }

    @Test
    public void testInsertInvalidEmail() {
        User userInvalidEmail = new User();
        userInvalidEmail.setName("User test invalid email");
        userInvalidEmail.setCpf("81884698077");
        userInvalidEmail.setEmail("invalidemail.com");

        assertThatThrownBy(() -> userRepository.save(userInvalidEmail))
                .isInstanceOf(TransactionSystemException.class);
    }

    @Test
    public void testInsertInvalidCpf() {
        User userInvalidEmail = new User();
        userInvalidEmail.setName("User test invalid CPF");
        userInvalidEmail.setCpf("11122233344");
        userInvalidEmail.setEmail("validmail@mail.com");

        assertThatThrownBy(() -> userRepository.save(userInvalidEmail))
                .isInstanceOf(TransactionSystemException.class);
    }

    @Test
    public void testFindByCpf() {
        User user = userRepository.findByCpf("02507924004");

        assertNotNull(user);
        assertThat(user.getName()).isEqualTo("User test 1");
        assertThat(user.getEmail()).isEqualTo("user1@mail.com");
    }

    @Test
    public void testNonExistCpf() {
        User user = userRepository.findByCpf(null);

        assertThat(user).isNull();
    }
}
