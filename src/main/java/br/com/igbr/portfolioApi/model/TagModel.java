package br.com.igbr.portfolioApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTag;

    @NotBlank
    @Size(min = 2, max = 125)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("tags")
    private UserModel user;

    @ManyToMany(mappedBy="tags")
    private Set<ProjectModel> project;
}
