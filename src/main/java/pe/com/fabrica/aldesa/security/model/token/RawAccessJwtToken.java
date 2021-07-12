package pe.com.fabrica.aldesa.security.model.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import pe.com.fabrica.aldesa.security.JwtExpiredTokenException;

/**
 * Esta clase es la encargada de verificar y validar la firma del token
 * 
 * @author Anthony Lopez
 *
 */
public class RawAccessJwtToken implements JwtToken {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String token;
    
    public RawAccessJwtToken(String token) {
        this.token = token;
    }
    
    /**
     * Analiza y valida la firma del token
     * 
     * @param signingKey
     * @return
     */
    public Jws<Claims> parseClaims(String signingKey) {
    	try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("JWT Token inválido", ex);
            throw new BadCredentialsException("JWT token inválido: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token ha expirado", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expiró", expiredEx);
        }
    }

	@Override
	public String getToken() {
		return token;
	}

}
