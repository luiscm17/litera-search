package com.luiscm.literasearch.repository;

import com.luiscm.literasearch.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Busca un libro por su titulo para evitar duplicados
    Optional<Libro> findByTituloIgnoreCase(String titulo);
    
    // Cuenta libros por idioma
    @Query("SELECT COUNT(l) FROM Libro l WHERE LOWER(l.idioma) = LOWER(:idioma)")
    Long countByLanguage(String idioma);
}
