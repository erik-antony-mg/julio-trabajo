package pe.com.fabrica.aldesa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Mercancia;

@Repository
public interface MercanciaRepository extends JpaRepository<Mercancia, Integer> {

    @Query(value = "SELECT  * from mercancia m where id_tarjeta  = :id", nativeQuery = true)
    Optional<Mercancia> findByIdTarjeta(Long id);

    @Query(value = "SELECT  * from mercancia m where idmercancia  = :id", nativeQuery = true)
    Optional<Mercancia> findById(Long id);

}
