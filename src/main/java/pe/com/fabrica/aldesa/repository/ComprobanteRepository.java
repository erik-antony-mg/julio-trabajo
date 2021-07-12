package pe.com.fabrica.aldesa.repository;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.fabrica.aldesa.beans.Comprobante;
import pe.com.fabrica.aldesa.beans.TipoComprobante;
import pe.com.fabrica.aldesa.beans.Cliente;
import pe.com.fabrica.aldesa.beans.Tarjeta;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

	@Override
	Page<Comprobante> findAll(Pageable pageable);

	@Query("select c from Comprobante c where c.tipoComprobanteRef = :tipoComprobanteRef and c.fechaEmision between :fechaInicial and :fechaFinal")
	Page<Comprobante> searchByTipoComprobanteRangoFechas(TipoComprobante tipoComprobanteRef,
			@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal, Pageable pageable);

	@Query("select c from Comprobante c where c.tipoComprobanteRef = :tipoComprobanteRef and c.comprobanteFull = :comprobanteFull")
	List<Comprobante> searchByTipoComprobanteComprobante(TipoComprobante tipoComprobanteRef, String comprobanteFull);

	// @Query("select c from Comprobante c where c.fechaEmision between
	// :fechaInicial and :fechaFinal")
	// List<Comprobante> searchByRangoFechas(@Param("fechaInicial") Date
	// fechaInicial, @Param("fechaFinal") Date fechaFinal);

	// @Query("select c from Comprobante c where c.comprobanteFull =
	// :numeroDocumento")
	// Optional<Comprobante> searchByNumeroDocumento(String numeroDocumento);

	@Query("select c from Comprobante c where c.tipoComprobanteRef = :tipoComprobanteRef and c.cliente = :cliente and c.fechaEmision between :fechaInicial and :fechaFinal")
	List<Comprobante> searchByTipoComprobanteClienteRangoFechas(TipoComprobante tipoComprobanteRef, Cliente cliente,
			@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

	@Query("select c from Comprobante c where  c.cliente = :cliente ")
	List<Comprobante> searchByCliente(Cliente cliente);

	@Query("select c from Comprobante c where c.tipoComprobanteRef = :tipoComprobanteRef and c.tarjeta = :tarjeta and c.fechaEmision between :fechaInicial and :fechaFinal")
	List<Comprobante> searchByTipoComprobanteTarjetaRangoFechas(TipoComprobante tipoComprobanteRef, Tarjeta tarjeta,
			@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

	@Query("select c from Comprobante c where c.tipoComprobanteRef = :tipoComprobanteRef and c.cliente = :cliente and c.tarjeta = :tarjeta and c.fechaEmision between :fechaInicial and :fechaFinal")
	List<Comprobante> searchByTipoComprobanteClienteTarjetaRangoFechas(TipoComprobante tipoComprobanteRef,
			Cliente cliente, Tarjeta tarjeta, @Param("fechaInicial") Date fechaInicial,
			@Param("fechaFinal") Date fechaFinal);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE comprobante SET fg_anulado= :anulado ,  fe_hr_anulacion = CURTIME() WHERE id_comprobante = :id", nativeQuery = true)
	void updateEstado(Integer id, String anulado);

}
