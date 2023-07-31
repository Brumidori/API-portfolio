package br.com.igbr.portfolioapi.repository;

import br.com.igbr.portfolioapi.model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, Long> {
    public List<ProjectModel> findAllByTagsContainingIgnoreCase (String tag);
}
