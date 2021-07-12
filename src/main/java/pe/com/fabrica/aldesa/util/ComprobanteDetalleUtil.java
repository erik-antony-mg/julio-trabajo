package pe.com.fabrica.aldesa.util;

import org.apache.commons.lang3.StringUtils;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.exception.ApiException;

public class ComprobanteDetalleUtil {

	private ComprobanteDetalleUtil() {
		throw new UnsupportedOperationException();
	}

	public static void validateItem(int item) throws ApiException {
		if (item == 0) {
			throw new ApiException(ApiError.ITEM_NOT_FOUND.getCode(), ApiError.ITEM_NOT_FOUND.getMessage());
		}
	}

	public static void validateServicio(int idServicio) throws ApiException {
		if (idServicio == 0) {
			throw new ApiException(ApiError.SERVICIO_DETALLE_NOT_FOUND.getCode(), ApiError.SERVICIO_DETALLE_NOT_FOUND.getMessage());
		}
	}

	public static void validateDescripcionDetalle(String descripcionDetalle) throws ApiException {
		if (StringUtils.isBlank(descripcionDetalle)) {
			throw new ApiException(ApiError.DESCRIPCION_DETALLE_NOT_FOUND.getCode(), ApiError.DESCRIPCION_DETALLE_NOT_FOUND.getMessage());
		}
	}

	public static void validateUnidadMedida(String unidadMedida) throws ApiException {
		if (StringUtils.isBlank(unidadMedida)) {
			throw new ApiException(ApiError.UNIDAD_MEDIDA_NOT_FOUND.getCode(), ApiError.UNIDAD_MEDIDA_NOT_FOUND.getMessage());
		}
	}

}
