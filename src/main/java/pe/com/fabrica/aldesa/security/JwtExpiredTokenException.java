package pe.com.fabrica.aldesa.security;

import org.springframework.security.core.AuthenticationException;

import pe.com.fabrica.aldesa.security.model.token.JwtToken;

/** 
 * Esta excepción es lanzada cuando ha expirado el JWT Token
 * 
 * @author Anthony Lopez
 *
 */
public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
