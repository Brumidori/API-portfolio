package br.com.igbr.portfolioApi.controller;

import br.com.igbr.portfolioApi.dto.UserDTO;
import br.com.igbr.portfolioApi.dto.UserLoginDTO;
import br.com.igbr.portfolioApi.model.UserModel;
import br.com.igbr.portfolioApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id){
        return userService.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<UserDTO> put (@RequestBody UserDTO user){
        return userService.findById(user.getIdUser())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                                .body(userService.save(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<UserDTO> dto = userService.findById(id);
        if(dto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
