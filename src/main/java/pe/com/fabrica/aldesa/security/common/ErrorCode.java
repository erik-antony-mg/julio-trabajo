package pe.com.fabrica.aldesa.security.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Códigos de error al solicitar acceso a recursos vía Token
 * 
 * @author Juan Pablo Cánepa Alvarez
 *
 */
public enum ErrorCode {

	GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);
    
    private int code;

    private ErrorCode(int code) {
        this.code = code;
    }

    @JsonValue
    public int getErrorCode() {
        return code;
    }
}
