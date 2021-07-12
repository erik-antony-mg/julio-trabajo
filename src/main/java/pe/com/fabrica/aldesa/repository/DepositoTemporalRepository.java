package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.DepositoTemporal;

@Repository
public interface DepositoTemporalRepository extends JpaRepository<DepositoTemporal, Integer> {

    List<DepositoTemporal> findByCodigoAduana(String codigoAduana);

	@Query(value = "SELECT * FROM deposito_temporal dt  INNER JOIN empresa e ON dt.id_empresa  = e.id_empresa where e.ra_social like %:razonSocial%", nativeQuery = true)
    List<DepositoTemporal> findByRazonSocial(String razonSocial);
    
    @Query("from DepositoTemporal")
    List<DepositoTemporal> findAllSP();

    @Override
    Page<DepositoTemporal> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(t.id_deposito) from tarjeta t where t.id_deposito =:id",nativeQuery = true)
    Integer findEliminarTarjetaById(Integer id);
    
}
