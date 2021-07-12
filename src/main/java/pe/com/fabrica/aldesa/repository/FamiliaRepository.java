package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import pe.com.fabrica.aldesa.beans.Familia;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Integer> {

    @Override
    Page<Familia> findAll(Pageable pageable);

    @Query(value = "SELECT  * from familia f where id_segmento  = :idSegmento", nativeQuery = true)
    Page<Familia> findAllSegmento(Pageable pageable , Integer idSegmento);
    
    @Query(value = "SELECT  * from familia s where s.codigo  = :codigo", nativeQuery = true)
    Familia findByCodigo(String codigo);
    
    @Query(value = "SELECT  * from familia s where s.descripcion LIKE %:descripcion%", nativeQuery = true)
    List<Familia> findByDescripcion(String descripcion);
    
    @Query(value = "SELECT  * from familia s where s.codigo  = :codigo and s.id_segmento= :idSegmento", nativeQuery = true)
    Familia findByCodigoSegmento(String codigo ,  Integer idSegmento);
    
    @Query(value = "SELECT  * from familia s where s.descripcion LIKE %:descripcion% and s.id_segmento= :idSegmento ", nativeQuery = true)
    List<Familia> findByDescripcionSegmento(String descripcion, Integer idSegmento);
}
