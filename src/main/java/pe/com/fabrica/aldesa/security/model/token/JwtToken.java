package pe.com.fabrica.aldesa.security.model.token;

/**
 * Esta interfaz debe ser implementada por aquellas clases que gestionarán la creación de tokens 
 * 
 * @author Anthony Lopez
 *
 */
public interface JwtToken {

	/** 
	 * Retorna el token creado por la aplicación
	 * 
	 * @return
	 */
	String getToken();
	
}
