package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.dto.UserDTO;
import br.com.igbr.portfolioApi.dto.UserLoginDTO;
import br.com.igbr.portfolioApi.model.UserModel;
import br.com.igbr.portfolioApi.repository.UserRepository;
import br.com.igbr.portfolioApi.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @BeforeAll
    void start(){
        repository.deleteAll();

        UserDTO user = new UserDTO(0L, "Testerino", "teste@teste.com",
                "teste123", "Perfil do teste", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        Optional<UserDTO> newUser = service.signUpUser(user);
    }

    @Test
    @Order(1)
    @DisplayName("Cadastrar Um Usuario")
    public void deveCriarUmUser() {

        HttpEntity<UserDTO> requisicao = new HttpEntity<UserDTO>(new UserDTO(0L, "Testerino",
                "teste1@teste.com", "teste1234", "Perfil do teste",
                "https://imgur.com/u9RkTCj","https://teste.linkedin.com",
                "https://teste.github.com"));

        ResponseEntity<UserDTO> resposta = testRestTemplate
                .exchange("/user/signUp", HttpMethod.POST, requisicao, UserDTO.class);

        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Erro ao cadastrar Usuario")
    public void deveApontarErroUsuarioSemSenha() {

        HttpEntity<UserDTO> requisicao = new HttpEntity<>(new UserDTO(0L, "aaaaaa",
                "teste1@email.com", "", "Perfil do teste",
                "https://imgur.com/u9RkTCj", "https://teste.linkedin.com",
                "https://teste.github.com"));

        ResponseEntity<String> resposta = testRestTemplate
                //a classe esperada eh erro e nao UserDto
                .exchange("/user/signUp", HttpMethod.POST, requisicao, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertTrue(resposta.getBody().contains("password"));
    }

    @Test
    @Order(8)
    @DisplayName("Erro ao cadastrar Usuario")
    public void deveApontarErroCriarUmUserURLErrada() {

        HttpEntity<UserDTO> requisicao = new HttpEntity<UserDTO>(new UserDTO(0L, "Testerino",
                "teste1@teste.com", "teste1234", "Perfil do teste",
                "https://imgur.com/u9RkTCj","este.linkedin.com",
                "https://teste.github.com"));

        ResponseEntity<String> resposta = testRestTemplate
                //a classe esperada eh erro e nao UserDto
                .exchange("/user/signUp", HttpMethod.POST, requisicao, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Logar Um Usuario")
    public void deveLogarUmUser() {

        HttpEntity<UserLoginDTO> requisicao = new HttpEntity<UserLoginDTO>(new UserLoginDTO(null, "teste@teste.com",
                "teste123", null));

        ResponseEntity<UserLoginDTO> resposta = testRestTemplate
                .exchange("/user/login", HttpMethod.POST, requisicao, UserLoginDTO.class);

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Encontrar todos Usuarios")
    public void deveEncontrarTodosUsuarios() {

        ResponseEntity<List<UserModel>> resposta = testRestTemplate
                .exchange("/user", HttpMethod.GET, null,  new ParameterizedTypeReference<List<UserModel>>() {});

        assertEquals(HttpStatus.OK,resposta.getStatusCode());

        List<UserModel> usuarios = resposta.getBody();

        if (usuarios != null) {
            for (UserModel usuario : usuarios) {
                System.out.println(usuario.getIdUser());
                System.out.println(usuario.getName());
            }
        } else {
            System.out.println("A lista de usuários está vazia ou a resposta é inválida.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Encontrar Um Usuario por id")
    public void deveEncontrarUsuarioPorId() {
        Long id = Long.valueOf(1);

        ResponseEntity<UserDTO> resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/user/"+id, HttpMethod.GET, null, UserDTO.class);

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
    }

    @Test
    @Order(5)
    @DisplayName("Atualizar Um Usuario")
    public void deveAtualizarUmUser() {

        HttpEntity<UserDTO> requisicao = new HttpEntity<UserDTO>(new UserDTO(1L, "Testerino",
                "teste@teste.com", "", "Perfil do teste",
                "https://imgur.com/u9RkTCj","https://teste.linkedin.com",
                "https://teste123.github.com"));

        ResponseEntity<UserDTO> resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/user", HttpMethod.PUT, requisicao, UserDTO.class);

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        assertEquals(resposta.getBody().getLinkGithub(), "https://teste123.github.com");
    }

    @Test
    @Order(6)
    @DisplayName("Deletar Um Usuario por id")
    public void deveDeletarUsuarioPorId() {
        Long id = Long.valueOf(1);

        ResponseEntity resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/user/"+id, HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
    }

}
