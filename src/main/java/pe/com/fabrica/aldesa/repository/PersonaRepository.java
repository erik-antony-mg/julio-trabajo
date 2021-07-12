package pe.com.fabrica.aldesa.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.TipoDocumento;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	@Query("select p from Persona p where p.nombres like %:nombres% or p.apellidoPaterno like %:nombres% or p.apellidoMaterno like %:nombres%")
	List<Persona> searchByNombres(String nombres);

	@Query("select p from Persona p where p.nombres like %:nombres% or p.apellidoPaterno like %:nombres% or p.apellidoMaterno like %:nombres%")
	Page<Persona> searchByNombresPag(String nombres, Pageable pageable);
	
	@Query("select p from Persona p where p.numeroDocumento = :numeroDocumento")
	Optional<Persona> findByNumeroDocumento(String numeroDocumento);

	//nuevo

	@Query("select p from Persona p where p.numeroDocumento like %:numero%")
	List<Persona> findByNumeroDocumento1(String numero);

	@Override
	Page<Persona> findAll(Pageable pageable);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Persona u SET u.nombres = :nombres, "
			+ "u.apellidoPaterno = :apellidoPaterno, u.apellidoMaterno = :apellidoMaterno, "
			+ "u.tipoDocumento = :tipoDocumento, u.numeroDocumento = :numeroDocumento, "
			+ "u.sexo = :sexo, u.fechaNacimiento = :fechaNacimiento, u.email = :email, u.telefono = :telefono , "
			+ "u.direccion = :direccion WHERE u.idPersona = :id")
	void updatePersona(Long id, String nombres, String apellidoPaterno, String apellidoMaterno,
			TipoDocumento tipoDocumento, String numeroDocumento, Character sexo, Date fechaNacimiento, String email,
			String telefono, Direccion direccion);

	@Query(value = "SELECT COUNT(c.id_persona) from chofer c where c.id_persona =:id", nativeQuery = true)
	Long finEliiminarChoferById(Long id);

	@Query(value = "SELECT COUNT(u.id_persona) from usuario u where id_persona =:id", nativeQuery = true)
	Long finEliiminarUsuarioById(Long id);

	@Query(value = "SELECT COUNT(v.id_persona) from vendedor v where id_persona =:id", nativeQuery = true)
	Long finEliiminarVendedorById(Long id);

	@Query(value = "SELECT COUNT(c.id_persona) from cliente c where id_persona =:id", nativeQuery = true)
	Long finEliiminarClienteById(Long id);

	@Query(value = "SELECT COUNT(p.id_persona) from proveedor p where id_persona =:id", nativeQuery = true)
	Long finEliiminarProveedorById(Long id);

	
}
