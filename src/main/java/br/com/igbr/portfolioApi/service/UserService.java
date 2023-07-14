package br.com.igbr.portfolioApi.service;

import br.com.igbr.portfolioApi.dto.UserDTO;
import br.com.igbr.portfolioApi.dto.UserLoginDTO;
import br.com.igbr.portfolioApi.model.UserModel;
import br.com.igbr.portfolioApi.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.weaver.ast.Literal;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> signUpUser(UserModel user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return Optional.empty();

        user.setPassword(cryptographyPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    public Optional<UserModel> updateUser(UserModel user) {

        if(userRepository.findById(user.getIdUser()).isPresent()) {
            Optional<UserModel> findUser = userRepository.findByEmail(user.getEmail());

            if ((findUser.isPresent()) && ( findUser.get().getIdUser() != user.getIdUser()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Email de usuário já existe!", null);

            user.setPassword(cryptographyPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));
        }
        return Optional.empty();
    }

    public Optional<UserLoginDTO> authenticUser(Optional<UserLoginDTO> userLogin) {
        Optional<UserModel> user = userRepository.findByEmail(userLogin.get().getEmail());

        if (user.isPresent()) {
            if (comparePasswords(userLogin.get().getPassword(), user.get().getPassword())) {
                userLogin.get().setIdUser(user.get().getIdUser());
                userLogin.get().setToken(generateBasicToken(userLogin.get().getEmail(), userLogin.get().getPassword()));
                userLogin.get().setPassword(user.get().getPassword());

                return userLogin;
            }
        }
        return Optional.empty();
    }

    private String cryptographyPassword (String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private boolean comparePasswords(String loginPassword, String userPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(loginPassword, userPassword);
    }

    private String generateBasicToken(String email, String password) {
        String token = email + ":" + password;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);
    }

    public Optional<UserDTO> findById(Long id) {
        UserModel model = userRepository.findById(id).get();
        UserDTO userDTO = new UserDTO(model);
        return Optional.of(userDTO);
    }

    public UserDTO save(UserDTO user) {
        Long id = user.getIdUser();
        UserModel model = userRepository.findById(id).get();
        model.setEmail(user.getEmail());
        model.setName(user.getName());
        model.setBiography(user.getBiography());
        model.setLinkGithub(user.getLinkGithub());
        model.setLinkLinkedin(user.getLinkLinkedin());
        model.setProfilePicture(user.getProfilePicture());
        userRepository.save(model);
        return user;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }
}
