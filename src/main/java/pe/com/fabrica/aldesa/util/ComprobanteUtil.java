package pe.com.fabrica.aldesa.util;

import org.apache.commons.lang3.StringUtils;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.exception.ApiException;

public class ComprobanteUtil {

	private ComprobanteUtil() {
		throw new UnsupportedOperationException();
	}

	public static void validateMoneda(Integer idMoneda) throws ApiException {
		if (idMoneda == 0) throw new ApiException(ApiError.MONEDA_NOT_FOUND.getCode(), ApiError.MONEDA_NOT_FOUND.getMessage());
	}

	public static void validateCliente(Long idCliente) throws ApiException {
		if (idCliente == 0) throw new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage());
	}

	public static void validateTipoComprobante(Integer idTipoComprobante) throws ApiException {
		if (idTipoComprobante == 0) throw new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(), ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage());
	}

	public static void validateEmailCliente(String emailCliente) throws ApiException {
		if (StringUtils.isBlank(emailCliente)) throw new ApiException(ApiError.EMAIL_CLIENTE_NOT_FOUND.getCode(), ApiError.EMAIL_CLIENTE_NOT_FOUND.getMessage());
	}

	public static void validateNombreCliente(String nombreCliente) throws ApiException {
		if (StringUtils.isBlank(nombreCliente)) throw new ApiException(ApiError.NOMBRE_CLIENTE_NOT_FOUND.getCode(), ApiError.NOMBRE_CLIENTE_NOT_FOUND.getMessage());
	}

	public static void validateTipoCambio(Double tipoCambio) throws ApiException {
		if (tipoCambio == 0) throw new ApiException(ApiError.TIPO_CAMBIO_NOT_FOUND.getCode(), ApiError.TIPO_CAMBIO_NOT_FOUND.getMessage());
	}

	public static void validateNumeroComprobante(Long numeroComprobante) throws ApiException {
		if (numeroComprobante == 0) throw new ApiException(ApiError.NUMERO_COMPROBANTE_NOT_FOUND.getCode(), ApiError.NUMERO_COMPROBANTE_NOT_FOUND.getMessage());
	}

	public static void validateSerie(Integer idSerie) throws ApiException {
		if (idSerie == 0) throw new ApiException(ApiError.SERIE_NOT_FOUND.getCode(), ApiError.SERIE_NOT_FOUND.getMessage());
	}

	public static void validateFormaPago(Integer idformapago) throws ApiException {
		if (idformapago == 0) throw new ApiException(ApiError.FORMA_PAGO_NOT_FOUND.getCode(), ApiError.FORMA_PAGO_NOT_FOUND.getMessage());
	}

	public static void validateTipoServicio(Integer tipoServicio) throws ApiException {
		if (tipoServicio == 0) throw new ApiException(ApiError.TIPO_SERVICIO_NOT_FOUND.getCode(), ApiError.TIPO_SERVICIO_NOT_FOUND.getMessage());
	}

	public static void validateFechaEmision(String fechaEmision) throws ApiException {
		if (StringUtils.isBlank(fechaEmision) || "null".equals(fechaEmision))
			throw new ApiException(ApiError.FECHA_EMISION_NOT_FOUND.getCode(), ApiError.FECHA_EMISION_NOT_FOUND.getMessage());

	}

	public static void validateFgAnulado(String fgAnulado) throws ApiException {
		if (StringUtils.isBlank(fgAnulado) || "null".equals(fgAnulado))
			throw new ApiException(ApiError.FG_ANULADO_NOT_FOUND.getCode(), ApiError.FG_ANULADO_NOT_FOUND.getMessage());
	}

	public static void validateFgDetraccion(String fgDetracion) throws ApiException {
		if (StringUtils.isBlank(fgDetracion) || "null".equals(fgDetracion))
			throw new ApiException(ApiError.FG_DETRACCION_NOT_FOUND.getCode(), ApiError.FG_DETRACCION_NOT_FOUND.getMessage());

	}

	public static void validaComprobanteFull(String comprobanteFull) throws ApiException {
		if (StringUtils.isBlank(comprobanteFull) || "null".equals(comprobanteFull))
			throw new ApiException(ApiError.COMPROBANTE_NOT_FOUND.getCode(), ApiError.COMPROBANTE_NOT_FOUND.getMessage());
	}

	/*public static void validateFormatComprobante(String comprobanteFull) throws ApiException {
		//if (!(serie.getSerie() + "-" + numeroComprobante).equals(formatComprobante(comprobanteFull)))
			throw new ApiException(ApiError.INVALID_NUMERO_COMPROBANTE.getCode(), ApiError.INVALID_NUMERO_COMPROBANTE.getMessage(), "esperaba " + comprobanteFull + " pero llegó " + serie.getSerie() + "-" + numeroComprobante);

	}*/

	/*private static Object formatComprobante(String comprobanteFull) {
		return comprobanteFull.split("-")[0] + "-" +  removeLeadingZeroes(comprobanteFull.split("-")[1]);
	}*/

	public static String removeLeadingZeroes(String str) {
		String strPattern = "^0+(?!$)";
		str = str.replaceAll(strPattern, "");
		return str;
	}

}
