package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.model.GalleryModel;
import br.com.igbr.portfolioApi.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gallery")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GalleryController {
    @Autowired
    private GalleryRepository repository;

    @GetMapping
    public ResponseEntity<List<GalleryModel>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryModel> getById(@PathVariable Long id){
        return repository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<GalleryModel> post (@RequestBody GalleryModel data){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(data));
    }

    @PutMapping
    public ResponseEntity<GalleryModel> put (@RequestBody GalleryModel data){
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(data));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
