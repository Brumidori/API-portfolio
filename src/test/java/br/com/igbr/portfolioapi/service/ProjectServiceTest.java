package br.com.igbr.portfolioapi.service;

import br.com.igbr.portfolioapi.dto.ProjectDTO;
import br.com.igbr.portfolioapi.model.GalleryModel;
import br.com.igbr.portfolioapi.model.ProjectModel;
import br.com.igbr.portfolioapi.model.TagModel;
import br.com.igbr.portfolioapi.model.UserModel;
import br.com.igbr.portfolioapi.repository.GalleryRepository;
import br.com.igbr.portfolioapi.repository.ProjectRepository;
import br.com.igbr.portfolioapi.repository.TagRepository;
import br.com.igbr.portfolioapi.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectServiceTest {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ProjectService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private GalleryRepository galleryRepository;

    @BeforeAll
    void start(){
        repository.deleteAll();

        UserModel user = new UserModel(0L, "Testerino", "teste@teste.com",
                "teste123", "Perfil do teste", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        userRepository.save(user);

        tagRepository.save(new TagModel(0L, "java", null));
        tagRepository.save(new TagModel(0L, "backend", null));
        tagRepository.save(new TagModel(0L, "springboot", null));

        galleryRepository.save(new GalleryModel(null, "https://imgur.com/hl6TgG2", null));
        galleryRepository.save(new GalleryModel(null, "https://imgur.com/mULO3ga", null));
    }

    @Test
    @DisplayName("Retorna ProjectDTO Cadastrado")
    @Order(1)
    public void createProject(){
        UserModel user = userRepository.findByEmail("teste@teste.com").get();

        ProjectModel project = new ProjectModel(
                0L, // não pode ser null
                "Project A",
                "This is the first project",
                "http://example.com/project_a.jpg",
                "http://example.com/deploy/project_a",
                "http://github.com/user/project_a",
                LocalDate.of(2023, 7, 25),
                user
        );

        Optional<ProjectDTO> newProject = Optional.of(service.save(project));

        assertTrue(newProject.isPresent());
    }

    @Test
    @DisplayName("Retorna um optional de ProjectDTO Cadastrado")
    @Order(2)
    public void findProjectById(){
        UserModel user = userRepository.findByEmail("teste@teste.com").get();
        ProjectModel project2 = new ProjectModel(
                0L,
                "Project B",
                "This is the second project",
                "http://example.com/project_b.jpg",
                "http://example.com/deploy/project_b",
                "http://github.com/user/project_b",
                LocalDate.of(2023, 7, 26),
                user // se nao enviar com usuario o metodo convertToDTO nao funciona e quebra a aplicaco
        );

        ProjectModel savedProject = repository.save(project2);

        Optional<ProjectDTO> response = service.findById(savedProject.getIdProject());

        assertEquals(savedProject.getIdProject(), response.get().getIdProject());
    }

    @Test
    @DisplayName("Retorna uma lista de ProjectDTO Cadastrado")
    @Order(3)
    public void findAllProjects(){

        List<ProjectModel> listProject = repository.findAll();

        System.out.println(listProject.size());
        List<ProjectDTO> response = service.findAll();

        assertEquals(listProject.size(), response.size());
    }

    @Test
    @DisplayName("Retorna um um ProjectDTO atualizado")
    @Order(4)
    public void updateProjects(){

        UserModel user = userRepository.findByEmail("teste@teste.com").get();

        ProjectModel projectToChange = new ProjectModel(
                1L,
                "Project A changed",
                "This is the first project",
                "http://example.com/project_b.jpg",
                "http://example.com/deploy/project_b",
                "http://github.com/user/project_b",
                LocalDate.of(2023, 7, 26),
                user // se nao enviar com usuario o metodo convertToDTO nao funciona e quebra a aplicaco
        );

        ProjectDTO response = service.update(projectToChange);

        assertEquals(projectToChange.getName(), response.getName());
    }

    @Test
    @DisplayName("Deleta um projeto, é esperado a lista de todos os projetos com 1")
    @Order(5)
    public void deleteById(){

        service.deleteById(1L);
        List<ProjectDTO> projects = service.findAll();

        assertEquals(1, projects.size());
    }
}
