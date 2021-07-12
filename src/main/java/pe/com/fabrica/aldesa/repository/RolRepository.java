package pe.com.fabrica.aldesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Page<Rol> findByNombreContaining(String nombre, Pageable pageable);

    Page<Rol> findAll(Pageable pageable);

}
