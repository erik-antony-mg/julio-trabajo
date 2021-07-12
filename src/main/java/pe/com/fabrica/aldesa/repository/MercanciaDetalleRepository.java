package pe.com.fabrica.aldesa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.MercanciaDetalle;

@Repository
public interface MercanciaDetalleRepository extends JpaRepository<MercanciaDetalle, Integer> {

    @Query(value = "SELECT  * from mercancia_detalle md  where idmercancia  = :id", nativeQuery = true)
    List<MercanciaDetalle> findByIdMercaderia(Long id);

    @Query(value = "SELECT  * from mercancia_detalle md  where md.iddetalle_mercancia  = :id", nativeQuery = true)
    Optional<MercanciaDetalle> findById(Long id);

    @Query(value = "SELECT  md.iddetalle_mercancia , md.descripcion  , md.cantidad  , md.`valorUS$` , md.bueno ,md.diferencia ,md.motivo, md.idmercancia from mercancia m INNER join mercancia_detalle md on m.idmercancia = md.idmercancia where m.id_tarjeta = :id", nativeQuery = true)
    List<MercanciaDetalle> findByTarjeta(Long id);

}
