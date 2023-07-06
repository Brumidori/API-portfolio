package br.com.igbr.portfolioApi.repository;

import br.com.igbr.portfolioApi.model.GalleryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryModel, Long> {

}
