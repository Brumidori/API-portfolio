package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.model.GaleryModel;
import br.com.igbr.portfolioApi.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GalleryController {
    @Autowired
    private GalleryRepository repository;

    @GetMapping
    public ResponseEntity<List<GaleryModel>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GaleryModel> getById(@PathVariable Long id){
        return repository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<GaleryModel> post (@RequestBody GaleryModel tags){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tags));
    }

    @PutMapping
    public ResponseEntity<GaleryModel> put (@RequestBody GaleryModel tags){
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(tags));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
