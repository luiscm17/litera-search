package com.luiscm.literasearch.service;

import com.luiscm.literasearch.model.Autor;
import com.luiscm.literasearch.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> buscarAutoresVivosEnAnio(Integer anio) {
        if (anio == null) {
            throw new IllegalArgumentException("El año no puede ser nulo");
        }
        return autorRepository.findAutoresVivosEnAnio(anio);
    }
}
