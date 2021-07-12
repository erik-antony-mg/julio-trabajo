package pe.com.fabrica.aldesa.security.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import pe.com.fabrica.aldesa.security.auth.web.AuthUserData;

/**
 * Esta clase sirve como puente para transferir datos escenciales del usuario
 * 
 * @author Anthony Lopez
 *
 */
public class UserContext {
	private final String username;
	private final List<GrantedAuthority> authorities;
	private final AuthUserData authUser;

	private UserContext(String username, List<GrantedAuthority> authorities, AuthUserData authUser) {
		this.username = username;
		this.authorities = authorities;
		this.authUser = authUser;
	}

	public static UserContext create(String username, List<GrantedAuthority> authorities, AuthUserData authUser) {
		if (StringUtils.isBlank(username))
			throw new IllegalArgumentException("Username está vacío: " + username);
		return new UserContext(username, authorities, authUser);
	}

	public String getUsername() {
		return username;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public AuthUserData getAuthUser() {
		return authUser;
	}

}
