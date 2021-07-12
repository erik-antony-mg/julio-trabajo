package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Regimen;

@Repository
public interface RegimenRepository extends JpaRepository<Regimen, Integer> {

	List<Regimen> findByCodigoAduana(Integer codigoaduana);

	@Query(value = "SELECT  * from regimen r where r.nombre like %:nombre%", nativeQuery = true)
	List<Regimen> findByNombre(String nombre);

	@Query("from Regimen")
    List<Regimen> findAllSP();

	@Override
	Page<Regimen> findAll(Pageable pageable);

	@Query(value = "SELECT COUNT(t.id_regimen) from tarjeta t where id_regimen =:id", nativeQuery = true)
	Integer findEliminarTarjetaById(Integer id);

	@Query(value = "SELECT COUNT(d.id_regimen) from dam d where id_regimen =:id", nativeQuery = true)
	Integer findEliminarDamById(Integer id);
}
