package pe.com.fabrica.aldesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.TipoPersona;

@Repository
public interface TipoPersonaRepository extends JpaRepository<TipoPersona, Integer> {

}
