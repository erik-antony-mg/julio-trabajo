package pe.com.fabrica.aldesa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.ContenedorDetalle;

@Repository
public interface ContenedorDetalleRepository extends JpaRepository<ContenedorDetalle, Integer> {

    @Query(value = "SELECT  * from mercancia_detalle md  where idmercancia  = :id", nativeQuery = true)
    List<ContenedorDetalle> findByIdMercaderia(Long id);

    @Query(value = "SELECT  * from mercancia_detalle md  where md.iddetalle_mercancia  = :id", nativeQuery = true)
    Optional<ContenedorDetalle> findById(Long id);

    @Query(value = "SELECT  cd.id_contenedor , cd.id_contenedor_detalle  ,cd.numero_precintos , cd.us_crea  , cd.us_modi  , cd.fe_crea ,cd.fe_modi  FROM contenedor c INNER JOIN contenedor_detalle cd ON c.id_contenedor = cd.id_contenedor where c.id_tarjeta = :id", nativeQuery = true)
    List<ContenedorDetalle> findByTarjeta(Long id);

}
