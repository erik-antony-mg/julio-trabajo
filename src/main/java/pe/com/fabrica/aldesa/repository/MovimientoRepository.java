package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

	@Override
	Page<Movimiento> findAll(Pageable pageable);
	
	@Query(value = "SELECT * from movimiento m where m.tipo  = :tipoMovimiento and m.fe_movimiento >= :fechaInicio AND m.fe_movimiento < DATE_ADD(:fechaFin,INTERVAL 1 DAY)", nativeQuery = true)
	Page<Movimiento> findByTipoFecha(String tipoMovimiento, String fechaInicio, String fechaFin, Pageable pageable);

	@Query(value = "SELECT * from movimiento m where m.fe_movimiento >= :fechaInicio AND m.fe_movimiento < DATE_ADD(:fechaFin,INTERVAL 1 DAY)", nativeQuery = true)
	Page<Movimiento> findByFecha(String fechaInicio, String fechaFin, Pageable pageable);

	@Query(value = "SELECT * from movimiento m inner join mercancia_detalle md on m.iddetalle_mercancia  = md.iddetalle_mercancia where md.idmercancia  = :idmercancia and m.tipo = :tipoMovimiento", nativeQuery = true)
	List<Movimiento> findByTipoAndMercancia(String tipoMovimiento, Integer idmercancia);

	@Query(value = "SELECT * FROM movimiento m\n" +
			"INNER JOIN mercancia_detalle md ON m.iddetalle_mercancia = md.iddetalle_mercancia\n" +
			"INNER JOIN mercancia me ON md.idmercancia = me.idmercancia\n" +
			"INNER JOIN  tarjeta t ON me.id_tarjeta = t.id_tarjeta\n" +
			"WHERE t.nro_tarjeta=:nroTarjeta",nativeQuery = true)
	List<Movimiento> findMovimientoByTarjetaByNumero(String nroTarjeta);

	@Query(value = "SELECT * FROM movimiento m\n" +
			"INNER JOIN mercancia_detalle md ON m.iddetalle_mercancia = md.iddetalle_mercancia\n" +
			"INNER JOIN mercancia me ON md.idmercancia = me.idmercancia\n" +
			"INNER JOIN  tarjeta t ON me.id_tarjeta = t.id_tarjeta\n" +
			"INNER JOIN cliente c ON t.id_cliente = c.id_cliente\n" +
			"INNER JOIN empresa e ON c.id_empresa = e.id_empresa\n" +
			"WHERE e.nu_ruc =:ruc",nativeQuery = true)
	List<Movimiento> findMovimientoByTarjetaByClienteByRuc(String ruc);

	@Query(value = "SELECT * FROM movimiento m\n" +
			"INNER JOIN mercancia_detalle md ON m.iddetalle_mercancia = md.iddetalle_mercancia\n" +
			"INNER JOIN mercancia me ON md.idmercancia = me.idmercancia\n" +
			"INNER JOIN  tarjeta t ON me.id_tarjeta = t.id_tarjeta\n" +
			"INNER JOIN cliente c ON t.id_cliente = c.id_cliente\n" +
			"INNER JOIN empresa e ON c.id_empresa = e.id_empresa\n" +
			"WHERE e.ra_social like lower(concat('%', :razonSocial, '%')) or e.no_comercial like lower(concat('%',:razonSocial, '%'));", nativeQuery = true)
	List<Movimiento> findMovimientoByTarjetaByClienteByRazonSocial(String razonSocial);



}
