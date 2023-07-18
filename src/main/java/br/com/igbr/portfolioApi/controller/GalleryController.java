package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.dto.GalleryDTO;
import br.com.igbr.portfolioApi.dto.TagDTO;
import br.com.igbr.portfolioApi.model.GalleryModel;
import br.com.igbr.portfolioApi.repository.GalleryRepository;
import br.com.igbr.portfolioApi.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gallery")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GalleryController {
    @Autowired
    private GalleryService service;

    @GetMapping
    public ResponseEntity<List<GalleryDTO>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryDTO> getById(@PathVariable Long id){
        return service.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<GalleryDTO> post (@RequestBody GalleryModel data){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(data));
    }

    @PutMapping
    public ResponseEntity<GalleryDTO> put (@RequestBody GalleryModel data){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<GalleryDTO> dto = service.findById(id);
        if(dto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
