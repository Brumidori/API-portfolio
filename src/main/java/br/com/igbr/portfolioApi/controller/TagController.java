package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.model.TagModel;
import br.com.igbr.portfolioApi.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {

    @Autowired
    private TagRepository repository;

    @GetMapping
    public ResponseEntity<List<TagModel>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagModel> getById(@PathVariable Long id){
        return repository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<TagModel> post (@RequestBody TagModel tags){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tags));
    }

    @PutMapping
    public ResponseEntity<TagModel> put (@RequestBody TagModel tags){
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(tags));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
