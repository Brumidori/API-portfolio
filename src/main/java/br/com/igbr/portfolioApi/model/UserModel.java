package br.com.igbr.portfolioApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank
    @Size(min = 2, max = 125, message = "O nome deve conter no mínimo 2 e no máximo 125 caracteres")
    private String name;

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 16, message = "A senha deve ter no minimo 8 catacteres")
    private String password;

    @NotBlank
    @Size(min = 2, max = 1000, message = "A biografia deve ter no maximo 1000 caracteres")
    private String biography;

    @Size(min = 2, max = 255)
    private String profilePicture;

    @URL
    private String linkLinkedin;

    @URL
    private String linkGithub;

    @OneToMany (mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("user")
    private List<ProjectModel> projects;
}
