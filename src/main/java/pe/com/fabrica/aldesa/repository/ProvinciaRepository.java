package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    @Query(value = "SELECT  * from provincia p where id_departamento  = :id_departamento", nativeQuery = true)
    List<Provincia> findByDepartamento(Integer id_departamento);
}
