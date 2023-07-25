package br.com.igbr.portfolioApi.repository;

import br.com.igbr.portfolioApi.model.UserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeAll
    void start(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("Retorna usuario utilizando o email")
    public void returnOneUserByEmail(){

        UserModel user = new UserModel(0L, "Testerino", "teste@teste.com",
                "teste123", "Perfil do teste", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        repository.save(user);

        Optional<UserModel> userModel = repository.findByEmail("teste@teste.com");

        assertEquals(true, userModel.isPresent());

        assertTrue(userModel.isPresent() && userModel.get().getEmail() == "teste@teste.com");

    }

}
