package br.com.igbr.portfolioApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "gallery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGallery;

    @URL
    private String imageUrl;

    @ManyToOne
    @JsonIgnoreProperties("galleries")
    private ProjectModel project;
}
