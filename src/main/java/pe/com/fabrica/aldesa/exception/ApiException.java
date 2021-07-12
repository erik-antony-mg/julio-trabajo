package pe.com.fabrica.aldesa.exception;

/**
 * Esta excepción es lanzada hacia el controlador cuando se produce una incidencia en el API
 *
 * @author Anthony Lopez
 *
 */
public class ApiException extends Exception {

	private static final long serialVersionUID = -7821958228183810886L;

	private final String code;
	private final String message;
	private final String detailMessage;

	public ApiException(String message) {
		this(message, null);
	}

	public ApiException(String code, String message) {
		this(code, message, null);
	}

	public ApiException(String code, String message, String detailMessage) {
		this.code = code;
		this.message = message;
		this.detailMessage = detailMessage;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

}
