package br.com.igbr.portfolioApi.repository;

import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.model.TagModel;
import br.com.igbr.portfolioApi.model.UserModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeAll
    void start(){
        repository.deleteAll();

        UserModel user = new UserModel(0L, "Testerino", "teste@teste.com",
                "teste123", "Perfil do teste", "https://imgur.com/u9RkTCj",
                "https://teste.linkedin.com", "https://teste.github.com");

        userRepository.save(user);
    }
    @Test
    @Disabled // não funciona, pois busca de Lista com o String não dá certo, o correto é fazer um find all no TagRepository
    @DisplayName("Retorna lista(Set) de projetos pelo nome da tag")
    public void findAllProjectContainingCase(){

        UserModel user = userRepository.findByEmail("teste@teste.com").get();

        ProjectModel project1 = new ProjectModel(
                0L,
                "Project A",
                "This is the first project",
                "http://example.com/project_a.jpg",
                "http://example.com/deploy/project_a",
                "http://github.com/user/project_a",
                LocalDate.of(2023, 7, 25),
                user
        );

        ProjectModel project2 = new ProjectModel(
                0L,
                "Project B",
                "This is the second project",
                "http://example.com/project_b.jpg",
                "http://example.com/deploy/project_b",
                "http://github.com/user/project_b",
                LocalDate.of(2023, 7, 26),
                user
        );

        project1 = repository.save(project1);
        project2 = repository.save(project2);

        Set<ProjectModel> projects = new HashSet<>();

        projects.add(project1);

        TagModel tagJava = tagRepository.save(new TagModel(0L, "java", projects));

        projects.add(project2);

        TagModel tagBackEnd = tagRepository.save(new TagModel(0L, "backend", projects));

        List<ProjectModel> responseProject = repository.findAllByTagsContainingIgnoreCase("Java");
        List<ProjectModel> responseProject2 = repository.findAllByTagsContainingIgnoreCase("backend");

        assertEquals(2, responseProject.size());
        assertEquals(1, responseProject2.size());

    }



}
