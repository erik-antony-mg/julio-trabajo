package pe.com.fabrica.aldesa.constant;

public enum ApiError {

	SUCCESS("0", "Ok"),
	NO_APPLICATION_PROCESSED("9000", "El sistema no pudo procesar su solicitud"),
	RESOURCE_NOT_FOUND("9001", "Recurso no encontrado"),
	EMPTY_OR_NULL_PARAMETER("9002", "Uno o más parámetros están vacíos o nulos"),
	ALREADY_EXISTS("9003", "Los datos ya han sido registrados anteriormente"),
	MULTIPLES_SIMILAR_ELEMENTS("9004", "Hay más de 1 coincidencia (múltiples elementos encontrados)"),
	RUC_LENGTH("9005", "RUC debe tener longitud 11"),
	DNI_LENGTH("9006", "DNI debe tener longitud 8"),
	CE_LENGTH("9007", "CE debe tener longitud máxima de 12"),
	QUOTATION_LINES("9008", "No se ha enviado líneas de cotización"),
	COMPROBANTE_LINES("9009", "No se ha enviado líneas de comprobante"),
	COMPROBANTE_ALREADY_EXISTS("7001", "Comprobante ya ha sido registrado anteriormente"),
	ADUANA_NOT_FOUND("8000", "No se encontró Aduana"),
	AGENCIA_ADUANA_NOT_FOUND("8001", "No se encontró Agencia Aduana"),
	DEPOSITO_TEMPORAL_NOT_FOUND("8002", "No se encontró Depósito temporal"),
	REGIMEN_NOT_FOUND("8003", "No se encontró Régimen"),
	TIPO_BULTO_NOT_FOUND("8004", "No se encontró Tipo Bulto"),
	DAM_NOT_FOUND("8005", "No se encontró DAM"),
	SERIE_NOT_FOUND("8006", "No se encontró Serie Comprobante"),
	TIPO_COMPROBANTE_NOT_FOUND("8007", "No se encontró Tipo Comprobante"),
	VENDEDOR_NOT_FOUND("8008", "No se encontró Vendedor"),
	CLIENTE_NOT_FOUND("8009", "No se encontró Cliente"),
	MONEDA_NOT_FOUND("8010", "No se encontró Moneda"),
	COTIZACION_NOT_FOUND("8011", "No se encontró Cotización"),
	CODIGO_PRODUCTO_SUNAT_NOT_FOUND("8011", "No se encontró Codigo Producto Sunat"),
	SERVICIO_NOT_FOUND("8012", "No se encontró Servicio"),
	EMPRESA_NOT_FOUND("8013", "No se encontró Empresa"),
	CHOFER_NOT_FOUND("8014", "No se encontró Chofer"),
	UBICACION_NOT_FOUND("8015", "No se encontró Ubicación"),
	TIPO_MERCANCIA_NOT_FOUND("8016", "No se encontró Tipo Mercancía"),
	TARJETA_NOT_FOUND("8017", "No se encontró Tarjeta"),
	VEHICULO_NOT_FOUND("8018", "No se encontró Vehículo"),
	TICKET_NOT_FOUND("8019", "No se encontró Ticket"),
	MOVIMIENTO_NOT_FOUND("8020", "No se encontró Movimiento"),
	GRUPO_SERVICIO_NOT_FOUND("8021", "No se encontró Grupo de servicios"),
	SUBSERVICIO_NOT_FOUND("8022", "No se encontró Grupo de Subservicio"),
	FORMA_PAGO_NOT_FOUND("8023", "No se encontró Forma de Pago"),
	NUMERO_COMPROBANTE_NOT_FOUND("8024", "No se encontró Número comprobante"),
	FECHA_EMISION_NOT_FOUND("8025", "No se encontró fecha de emisión"),
	TIPO_CAMBIO_NOT_FOUND("8026", "No se encontró Tipo Cambio"),
	NOMBRE_CLIENTE_NOT_FOUND("8027", "No se encontró Nombre Cliente"),
	EMAIL_CLIENTE_NOT_FOUND("8028", "No se encontró Email Cliente"),
	FG_ANULADO_NOT_FOUND("8029", "No se encontró flag anulado"),
	FG_DETRACCION_NOT_FOUND("8030", "No se encontró flag Detracción"),
	COMPROBANTE_NOT_FOUND("8031", "No se encontró comprobante (serie-número)"),
	INVALID_NUMERO_COMPROBANTE("8032", "Serie-número inválido, no corresponde con los campos serie y número"),
	TIPO_COMPROBANTE_REF_NOT_FOUND("8033", "No se encontró Tipo Comprobante de Referencia"),
	DESCRIPCION_DETALLE_NOT_FOUND("8034", "No se encontró descripción en detalle"),
	TIPO_SERVICIO_NOT_FOUND("8037", "No se encontró Número comprobante"),
	UNIDAD_MEDIDA_NOT_FOUND("8035", "No se encontró unidad de medida"),
	SERVICIO_DETALLE_NOT_FOUND("8036", "No se encontró Servicio en detalle"),
	ITEM_NOT_FOUND("8036", "No se encontró Item");

	private final String code;
	private final String message;

	private ApiError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
