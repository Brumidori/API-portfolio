package br.com.igbr.portfolioapi.repository;

import br.com.igbr.portfolioapi.model.GalleryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryModel, Long> {
}
