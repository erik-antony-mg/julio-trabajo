package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.TipoBulto;

@Repository
public interface TipoBultoRepository extends JpaRepository<TipoBulto, Integer>{

    @Query(value = "SELECT  * from tipo_bulto tb  where tb.nombre  LIKE %:nombre%", nativeQuery = true)
    List<TipoBulto> findByNombre(String nombre);

    @Query(value = "SELECT  * from tipo_bulto tb  where tb.abrev  LIKE %:abreviatura%", nativeQuery = true)
    List<TipoBulto> findByAbreviatura(String abreviatura);

    @Query(value = "SELECT COUNT(d.id_tibulto) from dam d where id_tibulto =:id",nativeQuery = true)
    Integer findEliminarDamById(Integer id);

    @Query(value = "SELECT COUNT(m.id_tibulto) from mercancia m where id_tibulto =:id",nativeQuery = true)
    Integer findEliminarMercanciaById(Integer id);
}
