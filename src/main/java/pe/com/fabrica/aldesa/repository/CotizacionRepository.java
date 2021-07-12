package pe.com.fabrica.aldesa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Cotizacion;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

	@Query("select c from Cotizacion c where c.fecha between :fechaInicial and :fechaFinal")
	List<Cotizacion> searchByRangoFechas(@Param("fechaInicial") Date fechaInicial,
			@Param("fechaFinal") Date fechaFinal);

	@Query(value = "SELECT  * from cotizacion c where (c.codigo LIKE %:filtro% or c.nro_carta_al LIKE %:filtro%)", nativeQuery = true)
	List<Cotizacion> searchByFiltro(String filtro);

	@Query(value = "SELECT  * from cotizacion c where (c.codigo LIKE  %:filtro% or c.nro_carta_al LIKE  %:filtro%) and c.id_cliente  = :idCliente", nativeQuery = true)
	List<Cotizacion> searchByFiltroCliente(String filtro, Integer idCliente);

	@Query(value = "SELECT  * from cotizacion c where  c.id_cliente = :idCliente", nativeQuery = true)
	List<Cotizacion> searchByCliente(Integer idCliente);
}
