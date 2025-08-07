package com.luiscm.literasearch.repository;

import com.luiscm.literasearch.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Busca un libro por su titulo para evitar duplicados
    Optional<Libro> findByTituloIgnoreCase(String titulo);
}
