package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.AgenciasAduanas;

@Repository
public interface AgenciaAduanasRepository extends JpaRepository<AgenciasAduanas, Long> {

	@Query(value = "SELECT COUNT(aa.id_agencia_aduanas) from agencia_aduanas aa where aa.cod_aduana  = :codigo", nativeQuery = true)
	boolean existsByCodigoAgencia(Integer codigo);

	List<AgenciasAduanas> findByCodigoAduana(Integer codigoaduana);

	@Query("from AgenciasAduanas")
	Page<AgenciasAduanas> findAllPag(Pageable pageable);

	@Query(value = "SELECT  * from agencia_aduanas aa inner join empresa e ON aa .id_empresa  = e.id_empresa where e.ra_social like %:razonSocial%", nativeQuery = true)
	List<AgenciasAduanas> findByRazonSocial(String razonSocial);

	@Query(value = "SELECT COUNT(t.id_agencia_aduanas) from tarjeta t where id_agencia_aduanas =:id",nativeQuery = true)
	Long findEliminarTarjetaById(Long id);

	@Query(value = "SELECT COUNT(d.id_ag_aduana) from dam d where id_ag_aduana =:id",nativeQuery = true)
	Long findEliminarDamById(Long id);

}
