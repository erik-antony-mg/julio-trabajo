package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.CotizacionDetalle;

@Repository
public interface CotizacionDetalleRepository extends JpaRepository<CotizacionDetalle, String> {

	@Query(value = "SELECT  * from cotizacion_detalle cd where id_cotizacion  = :idcotizacion", nativeQuery = true)
	List<CotizacionDetalle> findByCotizacion(Integer idcotizacion);

	@Query(value = "SELECT * from cotizacion_detalle cd  where id_servicio != 1 and id_servicio != 2 and id_cotizacion  = :idcotizacion", nativeQuery = true)
	List<CotizacionDetalle> findByCotizacionExtras(Integer idcotizacion);

	@Query(value = "SELECT  * from cotizacion_detalle cd where id_servicio = :id_servicio and id_cotizacion  = :idcotizacion ", nativeQuery = true)
	List<CotizacionDetalle> findByServicioCotizacion(Integer idcotizacion, Integer id_servicio);
}
