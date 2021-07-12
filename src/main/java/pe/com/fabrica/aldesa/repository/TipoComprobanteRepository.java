package pe.com.fabrica.aldesa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.TipoComprobante;

import java.util.List;

@Repository
public interface TipoComprobanteRepository extends JpaRepository<TipoComprobante, Integer>{
    
    @Override
    Page<TipoComprobante> findAll(Pageable pageable);

    //@Query(value = "SELECT  * from tipo_comprobante t where t.nombre LIKE %:nombre", nativeQuery = true)
    List<TipoComprobante> findByNombreContaining(String nombre);

    @Query(value = "SELECT  * from tipo_comprobante t where t.abrev  = :abreviatura", nativeQuery = true)
    TipoComprobante findByAbreviatura(String abreviatura);

    @Query(value = "SELECT  * from tipo_comprobante t where t.cod_sunat  = :codigoSunat", nativeQuery = true)
    TipoComprobante findByCodigoSunat(String codigoSunat);
}
