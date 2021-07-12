package pe.com.fabrica.aldesa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.fabrica.aldesa.beans.Usuario;
import pe.com.fabrica.aldesa.repository.AuthorizationRepository;

/**
 * Implementación por defecto de {@link AuthorizationService}
 * 
 * @author Anthony Lopez
 *
 */
@Service
public class DefaultAuthorizationService implements AuthorizationService {

	@Autowired
	private AuthorizationRepository authorizationRepository;

	@Override
	public Optional<Usuario> loadUserByUsername(String username) {

		Usuario user = authorizationRepository.findUserByUsername(username);
		return Optional.ofNullable(user);
	}

}
