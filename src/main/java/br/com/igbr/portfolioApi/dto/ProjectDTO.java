package br.com.igbr.portfolioApi.dto;

import br.com.igbr.portfolioApi.model.GalleryModel;
import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.model.TagModel;
import br.com.igbr.portfolioApi.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long idProject;
    private String name;
    private String description;
    private String projectPicture;
    private String linkDeploy;
    private String linkCode;
    private LocalDate referenceDate;
    private UserModel user;
    private List<GalleryModel> galleries;
    private List<TagModel> tags;

    public ProjectDTO(ProjectModel entity){
        BeanUtils.copyProperties(entity, this);
    }
}
