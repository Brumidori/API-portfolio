package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {

    @Autowired
    private ProjectRepository repository;

    @GetMapping
    public ResponseEntity<List<ProjectModel>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectModel> getById(@PathVariable Long id){
        return repository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<ProjectModel> post (@RequestBody ProjectModel project){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(project));
    }

    @PutMapping
    public ResponseEntity<ProjectModel> put (@RequestBody ProjectModel project){
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(project));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
