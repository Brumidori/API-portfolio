package br.com.igbr.portfolioapi.repository;

import br.com.igbr.portfolioapi.model.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagModel, Long> {
    public List<TagModel> findAllByNameContainingIgnoreCase (String name);
}
