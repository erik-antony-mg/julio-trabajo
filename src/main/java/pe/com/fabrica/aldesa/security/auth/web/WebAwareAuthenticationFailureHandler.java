package pe.com.fabrica.aldesa.security.auth.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.fabrica.aldesa.security.JwtExpiredTokenException;
import pe.com.fabrica.aldesa.security.common.ErrorCode;
import pe.com.fabrica.aldesa.security.common.ErrorResponse;

/**
 * Es invocado por {@link WebLoginProcessingFilter} en caso falle la autenticación.
 * 
 * @author Anthony Lopez
 *
 */
@Component
public class WebAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private final ObjectMapper mapper;
    
    @Autowired
    public WebAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }   
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        if (exception instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), ErrorResponse.of("Username o password inválido", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		} else if (exception instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), ErrorResponse.of("Token ha expirado", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
		} else if (exception instanceof AuthenticationServiceException) {
		    mapper.writeValue(response.getWriter(), ErrorResponse.of(exception.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		}

		mapper.writeValue(response.getWriter(), ErrorResponse.of("Auntenticación falló", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
	}

}
