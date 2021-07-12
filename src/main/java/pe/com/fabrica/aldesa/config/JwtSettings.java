package pe.com.fabrica.aldesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Esta clase contiene los valores necesarios para gestión del token.
 * Toma los valores del archivo de propiedades <tt>application.yml</tt> o del archivo que se designe en forma externa.
 * 
 * @author Anthony Lopez
 *
 */
@Configuration
@ConfigurationProperties(prefix = "web.security.jwt")
public class JwtSettings {

	private Integer tokenExpirationTime;
	private String tokenIssuer;
	private String tokenSigningKey;
	private Integer refreshTokenExpTime;

	public Integer getRefreshTokenExpTime() {
		return refreshTokenExpTime;
	}

	public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
		this.refreshTokenExpTime = refreshTokenExpTime;
	}

	public Integer getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Integer tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

	public String getTokenIssuer() {
		return tokenIssuer;
	}
	public void setTokenIssuer(String tokenIssuer) {
		this.tokenIssuer = tokenIssuer;
	}

	public String getTokenSigningKey() {
		return tokenSigningKey;
	}

	public void setTokenSigningKey(String tokenSigningKey) {
		this.tokenSigningKey = tokenSigningKey;
	}
}
