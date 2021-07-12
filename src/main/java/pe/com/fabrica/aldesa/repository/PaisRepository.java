package pe.com.fabrica.aldesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

}
