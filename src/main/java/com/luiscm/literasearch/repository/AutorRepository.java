package com.luiscm.literasearch.repository;

import com.luiscm.literasearch.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);
    
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqualOrAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(
        Integer anio, Integer anio2, Integer anio3);
        
    default List<Autor> findAutoresVivosEnAnio(Integer anio) {
        return findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqualOrAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(
            anio, anio, anio);
    }
}
