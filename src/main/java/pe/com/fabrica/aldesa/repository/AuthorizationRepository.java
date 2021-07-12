package pe.com.fabrica.aldesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Usuario;

/**
 * Esta clase solo se emplea para el proceso de autenticación y autorización de usuarios
 * 
 * @author Anthony Lopez
 *
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<Usuario, Integer> {
	
	/**
	 * Encuentra un usuario a través de su username
	 * 
	 * @param username
	 * @return
	 */
	Usuario findUserByUsername(String username);

}
