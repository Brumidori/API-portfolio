package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.dto.UserLoginDTO;
import br.com.igbr.portfolioApi.model.TagModel;
import br.com.igbr.portfolioApi.model.UserModel;
import br.com.igbr.portfolioApi.repository.TagRepository;
import br.com.igbr.portfolioApi.repository.UserRepository;
import br.com.igbr.portfolioApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable Long id){
        return repository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PostMapping
    public ResponseEntity<UserModel> post (@RequestBody UserModel user){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));
    }

    @PutMapping
    public ResponseEntity<UserModel> put (@RequestBody UserModel user){
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestBody Optional<UserLoginDTO> userLoginDTO) {
        return userService.authenticUser(userLoginDTO)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserModel> postUsuario(@Valid @RequestBody UserModel user) {
        return userService.signUpUser(user)
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
