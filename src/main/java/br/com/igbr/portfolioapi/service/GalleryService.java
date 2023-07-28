package br.com.igbr.portfolioapi.service;

import br.com.igbr.portfolioapi.dto.GalleryDTO;
import br.com.igbr.portfolioapi.model.GalleryModel;
import br.com.igbr.portfolioapi.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    public List<GalleryDTO> findAll() {
        List<GalleryModel> result =  galleryRepository.findAll();
        List<GalleryDTO> dtoList = result.stream().map(GalleryDTO:: new).toList();
        return dtoList;
    }

    public Optional<GalleryDTO> findById(Long id) {
        Optional<GalleryModel> optModel = galleryRepository.findById(id);
        if(optModel.isPresent()){
            GalleryDTO dto = new GalleryDTO(optModel.get());
            return Optional.of(dto);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Galeria não encontrada!", null);

    }

    public GalleryDTO save(GalleryModel gallery) {

        Optional<GalleryModel> optModel = galleryRepository.findById(gallery.getIdGallery());
        if(optModel.isEmpty()){
            GalleryModel model = galleryRepository.save(gallery);
            return new GalleryDTO(model);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Galeria já existe!", null);

    }

    public GalleryDTO update(GalleryModel gallery) {

        Optional<GalleryModel> optModel = galleryRepository.findById(gallery.getIdGallery());
        if(optModel.isPresent()){
            GalleryModel model = galleryRepository.save(gallery);
            return new GalleryDTO(model);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Galeria não existe!", null);

    }

    public void deleteById(Long id) {
        galleryRepository.deleteById(id);
    }

}
