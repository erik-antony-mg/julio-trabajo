package pe.com.fabrica.aldesa.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.TipoDocumento;
import pe.com.fabrica.aldesa.beans.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Vendedor u SET u.nombres = :nombres, "
			+ "u.apellidoPaterno = :apellidoPaterno, u.apellidoMaterno = :apellidoMaterno, "
			+ "u.tipoDocumento = :tipoDocumento, u.numeroDocumento = :numeroDocumento, "
			+ "u.sexo = :sexo, u.fechaNacimiento = :fechaNacimiento, u.email = :email, "
			+ "u.direccion = :direccion WHERE u.idPersona = :id")
	void updateVendedor(Long id, String nombres, String apellidoPaterno, String apellidoMaterno,
			TipoDocumento tipoDocumento, String numeroDocumento, Character sexo, Date fechaNacimiento, String email,
			Direccion direccion);

}
