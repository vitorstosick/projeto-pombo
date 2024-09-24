package model.repository;

import com.pruu.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    


}
