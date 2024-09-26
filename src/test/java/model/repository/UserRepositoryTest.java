package model.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

//Anotação usada para subir uma base local (ex.: MySQL)
@SpringBootTest

//Caso queira usar a base H2 utilize
//@DataJpaTest

//Aponta para o DataSource configurado em application-test.properties
//ou para a base com a propriedade test no pom
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setName("User Test 1");
        user1.setCpf("72769427083");
        user1.setEmail("user1@mail.com");
        users.add(user1);

        User user2 = new User();
        user2.setName("User Test 2");
        user2.setCpf("89715688012");
        user2.setEmail("user2@mail.com");
        users.add(user2);

        User user3 = new User();
        user3.setName("User Test 3");
        user3.setCpf("40235278076");
        user3.setEmail("user3@mail.com");
        users.add(user3);

        userRepository.saveAll(users);
    }

    @Test
    public void testInsertAllArgsFilled() {
        User user = new User();

        user.setName("User Test");
        user.setCpf("20283028017");
        user.setEmail("test@mail.com");

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("User Test");
        assertThat(savedUser.getCpf()).isEqualTo("20283028017");
        assertThat(savedUser.getEmail()).isEqualTo("test@mail.com");
    }




}
