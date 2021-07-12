package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import pe.com.fabrica.aldesa.beans.Segmento;

@Repository
public interface SegmentoRepository extends JpaRepository<Segmento, Integer> {

    @Override
    Page<Segmento> findAll(Pageable pageable);
    
    @Query(value = "SELECT  * from segmento s where s.codigo  = :codigo", nativeQuery = true)
    Segmento findByCodigo(String codigo);
    
    @Query(value = "SELECT  * from segmento s where s.descripcion LIKE %:descripcion%", nativeQuery = true)
    List<Segmento> findByDescripcion(String descripcion);
    
}
