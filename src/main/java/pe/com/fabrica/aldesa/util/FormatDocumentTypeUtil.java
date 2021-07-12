package pe.com.fabrica.aldesa.util;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.constant.DocumentType;
import pe.com.fabrica.aldesa.exception.ApiException;

public class FormatDocumentTypeUtil {

	private FormatDocumentTypeUtil() {
		throw new UnsupportedOperationException();
	}

	public static void validateDocumentType(String numeroDocumento, Integer idTipoDocumento) throws ApiException {
		switch (idTipoDocumento) {
		case DocumentType.DNI:
			validateLengthDni(numeroDocumento);
			break;
		case DocumentType.CE:
			validateLengthCe(numeroDocumento);
			break;
		case DocumentType.RUC:
			validateLengthRuc(numeroDocumento);
			break;
		default:
			throw new ApiException("Documento no definido");
		}
	}

	private static void validateLengthDni(String numeroDocumento) throws ApiException {
		if (numeroDocumento.length() != 8) {
			throw new ApiException(ApiError.DNI_LENGTH.getCode(), ApiError.DNI_LENGTH.getMessage());
		}
	}

	private static void validateLengthCe(String numeroDocumento) throws ApiException {
		if (numeroDocumento.length() > 12) {
			throw new ApiException(ApiError.DNI_LENGTH.getCode(), ApiError.DNI_LENGTH.getMessage());
		}
	}

	private static void validateLengthRuc(String numeroDocumento) throws ApiException {
		if (numeroDocumento.length() != 11) {
			throw new ApiException(ApiError.RUC_LENGTH.getCode(), ApiError.RUC_LENGTH.getMessage());
		}
	}

}
