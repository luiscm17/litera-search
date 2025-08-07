package com.luiscm.literasearch.service;

import com.luiscm.literasearch.model.Autor;
import com.luiscm.literasearch.model.DatosAutor;
import com.luiscm.literasearch.model.DatosLibros;
import com.luiscm.literasearch.model.Libro;
import com.luiscm.literasearch.repository.AutorRepository;
import com.luiscm.literasearch.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional
    public void guardarLibro(DatosLibros datosLibro) {
        try {
            Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(datosLibro.title());
            if (libroExistente.isPresent()) {
                System.out.println("\nEl libro '" + datosLibro.title() + "' ya está guardado en el historial.");
                return;
            }

            if (datosLibro.authors() == null || datosLibro.authors().isEmpty()) {
                System.out.println("\nNo se puede guardar el libro porque no tiene autor.");
                return;
            }
            
            DatosAutor datosAutor = datosLibro.authors().get(0);
            if (datosAutor == null || datosAutor.nombre() == null || datosAutor.nombre().trim().isEmpty()) {
                System.out.println("\nNo se puede guardar el libro porque el nombre del autor no es válido.");
                return;
            }
            
            String titulo = datosLibro.title();
            if (titulo.length() > 1000) {
                titulo = titulo.substring(0, 1000);
                System.out.println("\nAdvertencia: El título se ha truncado a 1000 caracteres.");
            }
            
            Optional<Autor> autorExistente = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());
            Autor autor = autorExistente.orElseGet(() -> {
                Autor nuevoAutor = new Autor(
                    datosAutor.nombre(), 
                    datosAutor.anioNacimiento(), 
                    datosAutor.anioFallecimiento()
                );
                return autorRepository.save(nuevoAutor);
            });

            String idioma = (datosLibro.getFirstLanguage() != null && !datosLibro.getFirstLanguage().isEmpty()) 
                ? datosLibro.getFirstLanguage() 
                : "Desconocido";
                
            Libro nuevoLibro = new Libro(
                titulo,
                idioma,
                datosLibro.downloadCount(), 
                autor
            );
            
            libroRepository.save(nuevoLibro);
            System.out.println("\nLibro '" + nuevoLibro.getTitulo() + "' guardado en el historial.");
            
        } catch (Exception e) {
            System.err.println("\nError al guardar el libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }
    
    // Cuenta los libros por código de idioma (ej: "es", "en")
    public Long contarLibrosPorIdioma(String idioma) {
        return libroRepository.countByLanguage(idioma);
    }
}
