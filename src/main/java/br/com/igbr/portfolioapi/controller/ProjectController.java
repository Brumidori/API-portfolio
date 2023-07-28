package br.com.igbr.portfolioapi.controller;

import br.com.igbr.portfolioapi.dto.ProjectDTO;
import br.com.igbr.portfolioapi.model.ProjectModel;
import br.com.igbr.portfolioapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id){
        return service.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<ProjectDTO> post (@RequestBody ProjectModel project){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(project));
    }

    @PutMapping
    public ResponseEntity<ProjectDTO> put (@RequestBody ProjectModel project){
        return ResponseEntity.status(HttpStatus.OK).body(service.save(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<ProjectDTO> dto = service.findById(id);
        if(dto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
