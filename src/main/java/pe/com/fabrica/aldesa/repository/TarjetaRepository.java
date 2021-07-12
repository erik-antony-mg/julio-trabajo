package pe.com.fabrica.aldesa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.fabrica.aldesa.beans.Tarjeta;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

        @Override
        Page<Tarjeta> findAll(Pageable pageable);
        
        @Query(value = "SELECT  * from tarjeta t where id_tarjeta =:id", nativeQuery = true)
        Optional<Tarjeta> findByIdTarjeta(Long id);

        @Query(value = "SELECT * FROM tarjeta t \n" + "INNER JOIN cliente c ON t.id_cliente = c.id_cliente\n"
                        + "INNER JOIN empresa e ON c.id_empresa = e.id_empresa\n"
                        + "WHERE e.nu_ruc =:ruc", nativeQuery = true)
        List<Tarjeta> findTarjetaClienteByRuc(String ruc);

        @Query(value = "SELECT  * from tarjeta t where id_cliente  = :id", nativeQuery = true)
        List<Tarjeta> findTarjetaClien(Integer id);

        @Query(value = "SELECT * FROM tarjeta t\n" + "INNER JOIN cliente c ON t.id_cliente = c.id_cliente\n"
                        + "INNER JOIN empresa e ON c.id_empresa = e.id_empresa\n"
                        + "WHERE e.ra_social like lower(concat('%', :razonSocial, '%')) or e.no_comercial like lower(concat('%',:razonSocial, '%'));", nativeQuery = true)
        List<Tarjeta> findTarjetaClienteByRazonSocial(String razonSocial);

        // List<Aduana> findByNombre(String nombre);
}
