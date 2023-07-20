package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.dto.ProjectDTO;
import br.com.igbr.portfolioApi.model.GalleryModel;
import br.com.igbr.portfolioApi.model.ProjectModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    @Order(1)
    @DisplayName("Cadastrar Um Projeto")
    public void deveCriarUmProjeto() {

        LocalDate data = LocalDate.now();
        HttpEntity<ProjectDTO> requisicao = new HttpEntity<ProjectDTO>(new ProjectDTO(0L,
                "Projeto", "descrição do projeto", "https://exemplo.com/foto.png%22",
                "https://exemplo.com/foto.png%22", "https://exemplo.com/foto.png%22",
                data, null, null, null));

        ResponseEntity<ProjectDTO> resposta = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/projects", HttpMethod.POST, requisicao, ProjectDTO.class);

        assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

    }
}
