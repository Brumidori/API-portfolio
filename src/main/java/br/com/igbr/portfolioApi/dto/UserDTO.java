package br.com.igbr.portfolioApi.dto;

import br.com.igbr.portfolioApi.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long idUser;
    private String name;
    private String email;
    private String biography;
    private String profilePicture;
    private String linkLinkedin;
    private String linkGithub;

    public UserDTO(UserModel entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
