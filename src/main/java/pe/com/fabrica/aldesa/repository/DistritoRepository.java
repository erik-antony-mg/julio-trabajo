package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Distrito;

@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Integer>{
    @Query(value = "SELECT  * from distrito  where id_provincia = :id_provincia", nativeQuery = true)
    List<Distrito> findByProvincia(Integer id_provincia);
}
