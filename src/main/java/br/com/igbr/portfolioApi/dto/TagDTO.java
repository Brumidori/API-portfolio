package br.com.igbr.portfolioApi.dto;

import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.model.TagModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private Long idTag;
    private String name;
    private Set<ProjectModel> project;

    public TagDTO(TagModel entity) {
        BeanUtils.copyProperties(entity, this);
    }

}
