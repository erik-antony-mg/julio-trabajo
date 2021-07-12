package pe.com.fabrica.aldesa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Contenedor;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Integer> {

    @Query(value = "SELECT  * from contenedor c where id_tarjeta  =:id", nativeQuery = true)
    Optional<Contenedor> findByIdTarjeta(Long id);

    @Query(value = "SELECT  * from mercancia m where idmercancia  = :id", nativeQuery = true)
    Optional<Contenedor> findById(Long id);

}
