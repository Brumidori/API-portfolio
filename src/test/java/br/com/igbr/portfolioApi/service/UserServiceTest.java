package br.com.igbr.portfolioApi.service;

import br.com.igbr.portfolioApi.dto.UserDTO;
import br.com.igbr.portfolioApi.dto.UserLoginDTO;
import br.com.igbr.portfolioApi.model.UserModel;
import br.com.igbr.portfolioApi.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @BeforeAll
    void start(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("Retorna UserModel Cadastro")
    @Order(1)
    public void signUpUser(){
        UserDTO user = new UserDTO(0L, "Testerino", "teste@teste.com",
                "teste123", "Perfil do teste", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        Optional<UserDTO> newUser = service.signUpUser(user);

        assertTrue(newUser.isPresent());

    }

    @Test
    @DisplayName("Retorna UserLoginDTO com token")
    @Order(2)
    public void signUser(){

        UserLoginDTO userLoginDTO = new UserLoginDTO(null, "teste@teste.com",
                "teste123", null);

        Optional<UserLoginDTO> loginUser = service.authenticUser(Optional.of(userLoginDTO));

        assertTrue(loginUser.isPresent());
    }

    @Test
    @DisplayName("Retorna UserModel Editado")
    @Order(3)
    public void updateUser(){
        UserDTO user = new UserDTO(1L, "Testerino Editado", "teste2@teste.com",
                "teste1234", "Perfil do teste 2", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        Optional<UserDTO> updatedUser = service.updateUser(user);

        assertEquals("Testerino Editado", updatedUser.get().getName());
        assertEquals("Perfil do teste 2", updatedUser.get().getBiography());
        assertEquals("teste2@teste.com", updatedUser.get().getEmail());
    }

    @Test
    @DisplayName("Retorna UserDTO por id")
    @Order(4)
    public void findUserById(){

        Optional<UserDTO> userDTO = service.findById(1L);

        assertTrue(userDTO.isPresent());
        assertTrue(userDTO.get().getName().equals("Testerino Editado"));
    }

    @Test
    @DisplayName("Retorna UserDTO criado/editado com UserDTO, precisando somente do ID")
    @Order(5)
    public void saveWithUserDtoId(){
        // não é  possível trocar senha
        Optional<UserDTO> userDTO = service.findById(1L);

        userDTO.get().setEmail("teste3@teste.com");

        UserDTO updatedUser = service.save(userDTO.get());

        assertTrue(userDTO.isPresent());
        assertTrue(userDTO.get().getEmail().equals("teste3@teste.com"));
    }

    @Test
    @DisplayName("Retorna uma lista com um userModel")
    @Order(6)
    public void findAll(){

        List<UserModel> users = service.findAll();

        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("delata um user e é esperado uma lista vazia")
    @Order(7)
    public void deleteById(){

        service.deleteById(1L);
        List<UserModel> users = service.findAll();

        assertEquals(0, users.size());
    }
}
