package br.com.igbr.portfolioApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "galery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GaleryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    private String imageUrl;

    @ManyToOne
    @JsonIgnoreProperties("galery")
    private ProjectModel project;

}
