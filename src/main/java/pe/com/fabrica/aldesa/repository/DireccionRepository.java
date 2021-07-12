package pe.com.fabrica.aldesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

}
