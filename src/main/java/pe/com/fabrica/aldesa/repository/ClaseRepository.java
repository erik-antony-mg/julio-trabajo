package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import pe.com.fabrica.aldesa.beans.Clase;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    @Override
    Page<Clase> findAll(Pageable pageable);

    @Query(value = "SELECT  * from clase c where id_familia  =  :idFamilia", nativeQuery = true)
    Page<Clase> findAllFamilia(Pageable pageable , Integer idFamilia);

    @Query(value = "SELECT  * from clase s where s.codigo  = :codigo", nativeQuery = true)
    Clase findByCodigo(String codigo);

    @Query(value = "SELECT  * from clase s where s.descripcion LIKE %:descripcion%", nativeQuery = true)
    List<Clase> findByDescripcion(String descripcion);

    @Query(value = "SELECT  * from clase s where s.codigo  = :codigo and s.id_familia= :idFamilia", nativeQuery = true)
    Clase findByCodigoFamilia(String codigo, Integer idFamilia);

    @Query(value = "SELECT  * from clase s where s.descripcion LIKE %:descripcion% and s.id_familia= :idFamilia ", nativeQuery = true)
    List<Clase> findByDescripcionFamilia(String descripcion, Integer idFamilia);

}
