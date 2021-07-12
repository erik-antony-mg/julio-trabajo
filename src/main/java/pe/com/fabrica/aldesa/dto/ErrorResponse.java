package pe.com.fabrica.aldesa.dto;

import java.util.Date;

/**
 * Esta clase contiene la estructura de una respuesta errónea del API
 * 
 * @author Anthony Lopez
 *
 */
public class ErrorResponse {

	private String code;
	private String message;
	private String detailMessage;
	private Date timestamp;

	private ErrorResponse(String code, String message) {
		this(code, message, null);
	}

	private ErrorResponse(String code, String message, String detailMessage) {
		this.code = code;
		this.message = message;
		this.detailMessage = detailMessage;
		this.timestamp = new Date();
	}

	public static ErrorResponse of(String code, String message, String detailMessage) {
		return new ErrorResponse(code, message, detailMessage);
	}

	public static ErrorResponse of(String code, String message) {
		return new ErrorResponse(code, message);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
