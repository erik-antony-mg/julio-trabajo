package pe.com.fabrica.aldesa.dto;

/**
 * Esta clase contiene la estructura de una respuesta del API
 *
 * @author Anthony Lopez
 *
 */
public class ApiResponse {

	private String code;
	private String message;
	private Object object;
	private Integer count;

	private ApiResponse(String code, String message) {
		this(code, message, null);
	}

	private ApiResponse(String code, String message, Object object) {
		this(code, message, object, null);
	}

	private ApiResponse(String code, String message, Object object, Integer count) {
		this.code = code;
		this.message = message;
		this.object = object;
		this.count = count;
	}

	public static ApiResponse of(String code, String message) {
		return new ApiResponse(code, message);
	}

	public static ApiResponse of(String code, String message, Object object) {
		return new ApiResponse(code, message, object);
	}

	public static ApiResponse of(String code, String message, Object object, Integer count) {
		return new ApiResponse(code, message, object, count);
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
