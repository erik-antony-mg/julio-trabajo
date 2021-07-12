package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Subservicio;
import pe.com.fabrica.aldesa.beans.SubservicioId;

@Repository
public interface SubservicioRepository extends JpaRepository<Subservicio, SubservicioId> {

	@Query(value = "select * from subservicio s where s.id_servicio = :idServicio", nativeQuery = true)
	List<Subservicio> searchSubservicioByServicio(Integer idServicio);

}
