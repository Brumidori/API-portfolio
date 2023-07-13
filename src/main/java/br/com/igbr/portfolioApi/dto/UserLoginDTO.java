package br.com.igbr.portfolioApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private Long idUser;
    private String name;
    private String email;
    private String password;
    private String token;
    private String biography;
    private String profilePicture;
    private String linkLinkedin;
    private String linkGithub;
}
