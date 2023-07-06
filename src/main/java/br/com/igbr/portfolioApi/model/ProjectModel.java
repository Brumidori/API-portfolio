package br.com.igbr.portfolioApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProject;

    @NotBlank
    @Size(min = 2, max = 125, message = "O nome deve conter no mínimo 2 e no máximo 125 caracteres")
    private String name;

    @NotBlank
    @Size(min = 2, max = 1000, message = "A descricao deve ter no maximo 1000 caracteres")
    private String description;

    @URL
    private String projectPicture;

    @URL
    private String linkDeploy;

    @URL
    private String linkCode;

    @UpdateTimestamp
    private LocalDate data;

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private UserModel user;


    @OneToMany (mappedBy = "project", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("project")
    private List<GaleryModel> galery;

    @ManyToMany
    @JoinTable(name="tags_project",
            joinColumns=
            @JoinColumn(name="project", referencedColumnName="idProject"),
            inverseJoinColumns=
            @JoinColumn(name="tag", referencedColumnName="idTag")
    )
    private Set<TagModel> tags;



}
