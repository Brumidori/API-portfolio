package br.com.igbr.portfolioapi.dto;

import br.com.igbr.portfolioapi.model.GalleryModel;
import br.com.igbr.portfolioapi.model.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GalleryDTO {

    private Long idGallery;

    private String imageUrl;

    private ProjectModel project;

    public GalleryDTO(GalleryModel entity) {
        BeanUtils.copyProperties(entity, this);
    }

}
