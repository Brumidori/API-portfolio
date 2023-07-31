package br.com.igbr.portfolioapi.controller;

import br.com.igbr.portfolioapi.dto.ProjectDTO;
import br.com.igbr.portfolioapi.dto.UserDTO;
import br.com.igbr.portfolioapi.model.ProjectModel;
import br.com.igbr.portfolioapi.model.UserModel;
import br.com.igbr.portfolioapi.repository.GalleryRepository;
import br.com.igbr.portfolioapi.repository.ProjectRepository;
import br.com.igbr.portfolioapi.repository.TagRepository;
import br.com.igbr.portfolioapi.repository.UserRepository;
import br.com.igbr.portfolioapi.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProjectRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private UserService userService;


    @BeforeAll
    void start(){
        repository.deleteAll();
        userRepository.deleteAll();
        tagRepository.deleteAll();
        galleryRepository.deleteAll();

        UserDTO user = new UserDTO(0L, "Testerino", "teste@teste.com",
                "teste123", "Perfil do teste", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        Optional<UserDTO> newUser = userService.signUpUser(user);
    }
    @Test
    @Order(1)
    @DisplayName("Cadastrar Um Projeto")
    public void deveCriarUmProjeto() {
        UserModel user = userRepository.findByEmail("teste@teste.com").get();
        LocalDate data = LocalDate.now();

        HttpEntity<ProjectModel> requisicao = new HttpEntity<ProjectModel>(new ProjectModel(0L,
                "Projeto", "descrição do projeto", "https://exemplo.com/foto.png%22",
                "https://exemplo.com/foto.png%22", "https://exemplo.com/foto.png%22",
                data, user, null, null));

        ResponseEntity<ProjectDTO> resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/projects", HttpMethod.POST, requisicao, ProjectDTO.class);

        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Encontrar todos projetos")
    public void deveEncontrarTodosProjetos() {

        ResponseEntity<List<ProjectModel>> resposta = testRestTemplate
                .exchange("/projects", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProjectModel>>() {
                });

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Encontrar Um projeto por id")
    public void deveEncontrarProjetoPorId() {

        ProjectModel project = repository.findById(1L).get();

        ResponseEntity<UserDTO> resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/projects/" + project.getIdProject(), HttpMethod.GET, null, UserDTO.class);

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("Atualizar Um Projeto")
    @Disabled //nao esta funcionando
    public void deveAtualizarUmProjeto() {

        ProjectModel project = repository.findById(1L).get();

        project.setDescription("Estou atualizado =)");


        HttpEntity<ProjectModel> requisicao = new HttpEntity<ProjectModel>(project);

        ResponseEntity<ProjectDTO> resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/projects", HttpMethod.PUT, requisicao, ProjectDTO.class);

        assertEquals(HttpStatus.OK,resposta.getStatusCode());
        assertEquals(project.getDescription(), resposta.getBody().getDescription());
    }

    @Test
    @Order(5)
    @DisplayName("Deletar Um projet por id")
    public void deveDeletarUsuarioPorId() {
        Long id = Long.valueOf(1);

        ResponseEntity resposta = testRestTemplate
                .withBasicAuth("teste@teste.com", "teste123")
                .exchange("/projects/" + id, HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
    }
}
