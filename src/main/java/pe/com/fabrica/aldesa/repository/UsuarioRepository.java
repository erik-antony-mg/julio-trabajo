package pe.com.fabrica.aldesa.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Rol;
import pe.com.fabrica.aldesa.beans.TipoDocumento;
import pe.com.fabrica.aldesa.beans.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Usuario u SET u.username = :username, u.rol = :rol, u.nombres = :nombres, "
			+ "u.apellidoPaterno = :apellidoPaterno, u.apellidoMaterno = :apellidoMaterno, "
			+ "u.tipoDocumento = :tipoDocumento, u.numeroDocumento = :numeroDocumento, "
			+ "u.sexo = :sexo, u.fechaNacimiento = :fechaNacimiento, u.email = :email, "
			+ "u.imagen = :imagen, u.direccion = :direccion, u.activo = :activo WHERE u.idPersona = :id")
	void updateUsuario(Long id, String username, Rol rol, String nombres, String apellidoPaterno,
			String apellidoMaterno, TipoDocumento tipoDocumento, String numeroDocumento, Character sexo,
			Date fechaNacimiento, String email, String imagen, Direccion direccion, String activo);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Usuario u SET u.password = :password WHERE u.idPersona = :id")
	void updatePassword(Long id, String password);


	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value ="INSERT INTO usuario VALUES (usuario.id_persona=:idpersona,usuario.id_rol=:idrol,usuario.username=:username,usuario.passwd=:password,fg_acti=:fgActi,usuario.imagen=:img,usuario.us_crea=:creador,usuario.us_modi=:modificador,usuario.fe_crea=:fechaCreacion,usuario.fe_modi=:fechaModificacion)", nativeQuery = true)
	Integer createUser(@Param("idpersona")Long idpersona,@Param("idrol")Integer idrol,@Param("username") String username,@Param("password")String password,@Param("fgActi")String fgActi,@Param("img")String img,@Param("creador")String creador,@Param("modificador")String modificador,@Param("fechaCreacion")String fechaCreacion,@Param("fechaModificacion")String fechaModificacion);


	

}
