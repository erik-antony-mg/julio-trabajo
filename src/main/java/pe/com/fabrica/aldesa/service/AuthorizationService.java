package pe.com.fabrica.aldesa.service;

import java.util.Optional;

import pe.com.fabrica.aldesa.beans.Usuario;

public interface AuthorizationService {
	
	/**
	 * Carga datos del usuario
	 * 
	 * @param username
	 * @return
	 */
	Optional<Usuario> loadUserByUsername(String username);
	
}
