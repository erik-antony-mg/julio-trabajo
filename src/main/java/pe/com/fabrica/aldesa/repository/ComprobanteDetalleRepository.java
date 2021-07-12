package pe.com.fabrica.aldesa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.ComprobanteDetalle;

@Repository
public interface ComprobanteDetalleRepository extends JpaRepository<ComprobanteDetalle, Integer> {

	@Query(value = "SELECT  * from comprobante_detalle cd where cd.id_comprobante  = :id", nativeQuery = true)
	List<ComprobanteDetalle> findByComprobante(Integer id);

}
