package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Aduana;

@Repository
public interface AduanaRepository extends JpaRepository<Aduana, Integer> {

    @Query(value = "SELECT  * from aduana a2 where a2.nombre like %:nombre%", nativeQuery = true)
    List<Aduana> findByNombre(String nombre);

    @Query("from Aduana")
    List<Aduana> findAllSP();

    @Override
    Page<Aduana> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(d.id_aduana) from dam d where id_aduana =:id", nativeQuery = true)
    Integer findEliminarDamById(Integer id);

}
