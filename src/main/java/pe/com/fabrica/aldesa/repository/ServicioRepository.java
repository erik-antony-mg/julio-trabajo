package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.GrupoServicio;
import pe.com.fabrica.aldesa.beans.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    List<Servicio> findByGrupoServicio(GrupoServicio gruposervicio);

    @Query(value = "SELECT  * from servicio s2 where s2.nombre LIKE %:nombre%", nativeQuery = true)
    List<Servicio> findByNombre(String nombre);

    @Query(value = "SELECT  * from servicio s2 where s2.codigo = :codigo", nativeQuery = true)
    Servicio findByCodigo(String codigo);

    @Query(value = "SELECT  * from servicio s inner join unspsc_sunat us on s.id_unspsc_sunat = us.id_unspsc_sunat where us.codigo = :codigo", nativeQuery = true)
    List<Servicio> findByCodigoProducto(Integer codigo);

    @Query(value = "SELECT  * from servicio s where s.id_grservicio  = :idGrupo", nativeQuery = true)
    List<Servicio> findByGrupo(Integer idGrupo);

    @Override
    Page<Servicio> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(s.id_servicio) from subservicio s where id_servicio =:id", nativeQuery = true)
    Integer findEliminarSubservicioById(Integer id);

    @Query(value = "SELECT COUNT(cd.id_servicio) from cotizacion_detalle cd where id_servicio =:id", nativeQuery = true)
    Integer findEliminarCotizacionDetalleById(Integer id);
}
