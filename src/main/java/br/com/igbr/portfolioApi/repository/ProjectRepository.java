package br.com.igbr.portfolioApi.repository;

import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.model.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {
    public List<ProjectModel> findAllByTagsContainingIgnoreCase (String tag);
}
