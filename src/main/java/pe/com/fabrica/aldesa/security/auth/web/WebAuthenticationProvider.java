package pe.com.fabrica.aldesa.security.auth.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import pe.com.fabrica.aldesa.beans.Usuario;
import pe.com.fabrica.aldesa.security.model.UserContext;
import pe.com.fabrica.aldesa.service.AuthorizationService;
import pe.com.fabrica.aldesa.util.WebUtil;

/**
 * Esta clase tiene las siguientes responsabilidades:
 * <ol>
 * <li>Verifica las credenciales de usuario contra la base de datos</li>
 * <li>Si el usuario y/o contraseña no hacen match con los registros de la base de datos lanza una excepción</li>
 * <li>Crea un {@link UserContext} con los datos necesarios (usuario y privilegios)</li>
 * <li>Luego de la autenticación delega la creación del JWT token en {@link WebAwareAuthenticationSuccessHandler}</li>
 * </ol>
 *
 * @author Anthony Lopez
 *
 */
@Component
public class WebAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebAuthenticationProvider.class);

	private final BCryptPasswordEncoder encoder;
	private final AuthorizationService userService;

	@Autowired
	public WebAuthenticationProvider(final AuthorizationService userService, final BCryptPasswordEncoder encoder) {
		this.userService = userService;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		Assert.notNull(authentication, "No se proporcionaron datos de autenticación");

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		LOGGER.info("Username: {}", username);

		Usuario usuario = userService.loadUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		if (usuario.getActivo().equalsIgnoreCase("N")) {
			throw new LockedException("Usuario está bloqueado");
		}

		if (!encoder.matches(password, usuario.getPassword())) {
			throw new BadCredentialsException("Autenticación falló. Username o Password no válido.");
		}

		AuthUserData authUser = WebUtil.getAuthUser(usuario);

		if (authUser.getRol() == null)
			throw new InsufficientAuthenticationException("User no tiene rol asignados");

		List<GrantedAuthority> authorities = Arrays.asList(authUser.getRol()).stream()
				.map(authority -> new SimpleGrantedAuthority(authUser.getRol()))
				.collect(Collectors.toList());

		UserContext userContext = UserContext.create(usuario.getUsername(), authorities, authUser);

		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
