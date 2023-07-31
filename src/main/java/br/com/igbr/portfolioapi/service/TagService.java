package br.com.igbr.portfolioapi.service;

import br.com.igbr.portfolioapi.dto.TagDTO;
import br.com.igbr.portfolioapi.model.TagModel;
import br.com.igbr.portfolioapi.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<TagDTO> findAll() {
        List<TagModel> result =  tagRepository.findAll();
        List<TagDTO> dtoList = result.stream().map(TagDTO:: new).toList();
        return dtoList;
    }

    public Optional<TagDTO> findById(Long id) {
        TagModel model = tagRepository.findById(id).get();
        TagDTO dto = new TagDTO(model);
        return Optional.of(dto);
    }

    public TagDTO save(TagModel tag) {
        TagModel model = tagRepository.save(tag);
        return new TagDTO(model);
    }

    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
