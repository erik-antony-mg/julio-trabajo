package pe.com.fabrica.aldesa.security.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * Esta clase extrae el token de autorización del encabezado.
 * 
 * @author Anthony Lopez
 *
 */
@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
	public static final String HEADER_PREFIX = "Bearer ";

	@Override
	public String extract(String header) {
		if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("El encabezado de autorización no puede estar en blanco!");
        }
        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Tamaño de encabezado de autorización no válido.");
        }
        return header.substring(HEADER_PREFIX.length(), header.length());
	}

}
