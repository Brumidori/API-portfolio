package br.com.igbr.portfolioApi.service;

import br.com.igbr.portfolioApi.dto.ProjectDTO;
import br.com.igbr.portfolioApi.dto.UserDTO;
import br.com.igbr.portfolioApi.model.GalleryModel;
import br.com.igbr.portfolioApi.model.ProjectModel;
import br.com.igbr.portfolioApi.model.TagModel;
import br.com.igbr.portfolioApi.model.UserModel;
import br.com.igbr.portfolioApi.repository.ProjectRepository;
import br.com.igbr.portfolioApi.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<ProjectDTO> findAll() {
        List<ProjectModel> result =  projectRepository.findAll();
        List<ProjectDTO> dtoList = new ArrayList<ProjectDTO>();

        result.forEach(project -> {
            ProjectDTO projectDTO = convertToDTO(project);
            dtoList.add(projectDTO);
        });

        return dtoList;
    }

    public Optional<ProjectDTO> findById(Long id) {
        Optional<ProjectModel> optModel = projectRepository.findById(id);
        if(optModel.isPresent()){
            ProjectDTO dto = new ProjectDTO(optModel.get());
            return Optional.of(dto);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Projeto não encontrada!", null);

    }

    public ProjectDTO save(ProjectModel project) {

        Optional<ProjectModel> optModel = projectRepository.findById(project.getIdProject());
        if(optModel.isEmpty()){

            ProjectModel model = projectRepository.save(project);
            return new ProjectDTO(model);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Projeto já existe!", null);

    }

    public ProjectDTO update(ProjectModel project) {

        Optional<ProjectModel> optModel = projectRepository.findById(project.getIdProject());
        if(optModel.isPresent()){
            ProjectModel model = projectRepository.save(project);
            return new ProjectDTO(model);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Projeto não existe!", null);

    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public ProjectDTO convertToDTO(ProjectModel projectModel) {
        ProjectDTO projectDTO = new ProjectDTO(projectModel);
        UserDTO userDTO = new UserDTO(projectModel.getUser());

        projectDTO.setUserDto(userDTO);

        return projectDTO;
    }

}
