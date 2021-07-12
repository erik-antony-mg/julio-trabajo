package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Unspsc;

@Repository
public interface UnspscRepository extends JpaRepository<Unspsc, Long> {
    @Query(value = "SELECT  * from unspsc_sunat us where us.id_clase  = :idClase", nativeQuery = true)
    Page<Unspsc> findByClase(Pageable pageable , Integer idClase);

    @Query(value = "SELECT  * from unspsc_sunat s where s.codigo  = :codigo", nativeQuery = true)
    Unspsc findByCodigo(String codigo);
    
    @Query(value = "SELECT  * from unspsc_sunat s where s.descripcion LIKE %:descripcion%", nativeQuery = true)
    List<Unspsc> findByDescripcion(String descripcion);

    @Query(value = "SELECT  * from unspsc_sunat s where s.codigo  = :codigo and s.id_clase= :id_clase", nativeQuery = true)
    Unspsc findByCodigoClase(String codigo, Integer id_clase);

    @Query(value = "SELECT  * from unspsc_sunat s where s.descripcion LIKE %:descripcion% and s.id_clase= :id_clase ", nativeQuery = true)
    List<Unspsc> findByDescripcionClase(String descripcion, Integer id_clase);
}
