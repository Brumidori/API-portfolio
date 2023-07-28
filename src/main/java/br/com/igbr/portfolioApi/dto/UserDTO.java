package br.com.igbr.portfolioApi.dto;

import br.com.igbr.portfolioApi.model.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long idUser;
    @NotBlank(message = "Nome obrigatorio")
    private String name;
    @NotBlank
    @Email(message = "Email obrigatorio")
    private String email;
    @NotBlank
    @Size(min = 8, message = "A senha é obrigatória e deve ter no minimo 8 catacteres")
    private String password;
    private String biography;
    private String profilePicture;
    @URL
    private String linkLinkedin;
    @URL
    private String linkGithub;

    public UserDTO(UserModel entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
