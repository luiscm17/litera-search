package com.luiscm.literasearch.repository;

import com.luiscm.literasearch.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Busca un autor por su nombre para evitar duplicados
    Optional<Autor> findByNombreIgnoreCase(String nombre);
}
