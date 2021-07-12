package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.TipoMercancia;

@Repository
public interface TipoMercanciaRepository extends JpaRepository<TipoMercancia, Integer>{


    @Query(value = "SELECT  * from tipo_mercancia tm where tm.nombre LIKE  %:nombre%", nativeQuery = true)
    List<TipoMercancia> findByNombre(String nombre);

    @Query(value = "SELECT COUNT(m.id_timercancia) from mercancia m where id_timercancia =:id",nativeQuery = true)
    Integer findEliminarMercanciaById(Integer id);




}
