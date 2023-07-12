package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.dto.TagDTO;
import br.com.igbr.portfolioApi.model.TagModel;
import br.com.igbr.portfolioApi.repository.TagRepository;
import br.com.igbr.portfolioApi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getById(@PathVariable Long id){
        return service.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<TagDTO> post (@RequestBody TagModel tag){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(tag));
    }

    @PutMapping
    public ResponseEntity<TagDTO> put (@RequestBody TagModel tag){
        return service.findById(tag.getIdTag())
                        .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                        .body(service.save(tag)))
                        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<TagDTO> dto = service.findById(id);
        if(dto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        service.deleteById(id);
    }
}
