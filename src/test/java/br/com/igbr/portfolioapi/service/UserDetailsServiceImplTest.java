package br.com.igbr.portfolioapi.service;

import br.com.igbr.portfolioapi.model.UserModel;
import br.com.igbr.portfolioapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDetailsServiceImplTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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

        UserDetails userDetails = userDetailsService.loadUserByUsername("teste@teste.com");

        assertTrue(userDetails.getUsername() == "teste@teste.com");

    }

}
