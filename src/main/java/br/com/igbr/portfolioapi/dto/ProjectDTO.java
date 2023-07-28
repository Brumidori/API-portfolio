package br.com.igbr.portfolioapi.dto;

import br.com.igbr.portfolioapi.model.GalleryModel;
import br.com.igbr.portfolioapi.model.ProjectModel;
import br.com.igbr.portfolioapi.model.TagModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

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
    private UserDTO userDto;
    private List<GalleryModel> galleries;
    private List<TagModel> tags;

    public ProjectDTO(ProjectModel entity){
        BeanUtils.copyProperties(entity, this);
    }

}
