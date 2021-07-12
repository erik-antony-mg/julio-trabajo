package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import pe.com.fabrica.aldesa.beans.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

    @Query("select a from Area a where a.nombre  like %:nombre%")
    List<Area> findByNombre(String nombre);

    @Query("select a from Area a where a.nombre  like %:nombre%")
    Page<Area> findByNombrePag(String nombre, Pageable pageable);

    @Query("select a from Area a where a.abreviatura  like %:abreviatura%")
    List<Area> findByAbreviatura(String abreviatura);

    @Query("select a from Area a where a.abreviatura  like %:abreviatura%")
    Page<Area> findByAbreviaturaPag(String abreviatura, Pageable pageable);

    @Query("from Area")
    List<Area> findAllSP();

    @Override
    Page<Area> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(u.id_area) from ubicacion u where u.id_area =:id",nativeQuery = true)
    Integer findEliminarUbicacionById(Integer id);

}
