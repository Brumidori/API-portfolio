package br.com.igbr.portfolioApi.dto;

import br.com.igbr.portfolioApi.model.GalleryModel;
import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.model.TagModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
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
