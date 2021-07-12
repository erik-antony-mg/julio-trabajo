package pe.com.fabrica.aldesa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.dto.ComprobanteContext;
import pe.com.fabrica.aldesa.beans.Cliente;
import pe.com.fabrica.aldesa.beans.Comprobante;
import pe.com.fabrica.aldesa.beans.ComprobanteDetalle;
import pe.com.fabrica.aldesa.beans.FormaPago;
import pe.com.fabrica.aldesa.beans.Moneda;
import pe.com.fabrica.aldesa.beans.SerieComprobante;
import pe.com.fabrica.aldesa.beans.Servicio;
import pe.com.fabrica.aldesa.beans.Tarjeta;
import pe.com.fabrica.aldesa.beans.TipoComprobante;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ClienteRepository;
import pe.com.fabrica.aldesa.repository.ComprobanteRepository;
import pe.com.fabrica.aldesa.repository.FormaPagoRepository;
import pe.com.fabrica.aldesa.repository.MonedaRepository;
import pe.com.fabrica.aldesa.repository.SerieComprobanteRepository;
import pe.com.fabrica.aldesa.repository.ServicioRepository;
import pe.com.fabrica.aldesa.repository.TarjetaRepository;
import pe.com.fabrica.aldesa.repository.TipoComprobanteRepository;
import pe.com.fabrica.aldesa.util.ComprobanteDetalleUtil;
import pe.com.fabrica.aldesa.util.ComprobanteUtil;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class ComprobanteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComprobanteRepository comprobanteRepository;

	@Autowired
	private SerieComprobanteRepository serieComprobanteRepository;

	@Autowired
	private TipoComprobanteRepository tipoComprobanteRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TarjetaRepository tarjetaRepository;

	@Autowired
	private MonedaRepository monedaRepository;

	@Autowired
	private FormaPagoRepository formaPagoRepository;

	@Autowired
	private ServicioRepository servicioRepository;
	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Comprobante> comprobantesPage = comprobanteRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, comprobantesPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantesPage.getContent(),
				Math.toIntExact(comprobantesPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Comprobante comprobante = comprobanteRepository.findById(id).orElse(null);
		logger.debug("Clase: {}", comprobante);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobante);
	}

	public ApiResponse findByTipoComprobanteRangoFechas(Integer idTipoComprobante, String startDate, String endDate,
			Integer pageNumber) throws ApiException {
		Date fechaInicial = DateUtil.of(startDate);
		Date fechaFinal = DateUtil.of(endDate);
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		Page<Comprobante> comprobantePage = comprobanteRepository.searchByTipoComprobanteRangoFechas(tipoComprobante,
				fechaInicial, fechaFinal, pageable);
		logger.debug("Página {} de: {}", pageNumber, comprobantePage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantePage.getContent(),
				Math.toIntExact(comprobantePage.getTotalElements()));
	}

	public ApiResponse findByTipoComprobanteComprobante(Integer idTipoComprobante, String comprobante)
			throws ApiException {
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		List<Comprobante> comprobantes = comprobanteRepository.searchByTipoComprobanteComprobante(tipoComprobante,
				comprobante);
		int size = comprobantes.size();
		logger.debug("Comprobante: {}", size);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantes, size);
	}

	public ApiResponse findByTipoComprobanteClienteRangoFechas(Integer idTipoComprobante, Long idCliente,
			String startDate, String endDate) throws ApiException {
		Date fechaInicial = DateUtil.of(startDate);
		Date fechaFinal = DateUtil.of(endDate);
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		List<Comprobante> comprobantes = comprobanteRepository
				.searchByTipoComprobanteClienteRangoFechas(tipoComprobante, cliente, fechaInicial, fechaFinal);
		int size = comprobantes.size();
		logger.debug("Comprobante: {}", size);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantes, size);
	}

	public ApiResponse findByCliente(Long idCliente) throws ApiException {
		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));
		List<Comprobante> comprobantes = comprobanteRepository.searchByCliente(cliente);
		int size = comprobantes.size();
		logger.debug("Comprobante: {}", size);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantes, size);
	}

	public ApiResponse findByTipoComprobanteTarjetaRangoFechas(Integer idTipoComprobante, Long idTarjeta,
			String startDate, String endDate) throws ApiException {
		Date fechaInicial = DateUtil.of(startDate);
		Date fechaFinal = DateUtil.of(endDate);
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		Tarjeta tarjeta = tarjetaRepository.findById(idTarjeta).orElseThrow(
				() -> new ApiException(ApiError.TARJETA_NOT_FOUND.getCode(), ApiError.TARJETA_NOT_FOUND.getMessage()));

		List<Comprobante> comprobantes = comprobanteRepository
				.searchByTipoComprobanteTarjetaRangoFechas(tipoComprobante, tarjeta, fechaInicial, fechaFinal);
		int size = comprobantes.size();
		logger.debug("Comprobante: {}", size);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantes, size);
	}

	public ApiResponse findByTipoComprobanteClienteTarjetaRangoFechas(Integer idTipoComprobante, Long idCliente,
			Long idTarjeta, String startDate, String endDate) throws ApiException {
		Date fechaInicial = DateUtil.of(startDate);
		Date fechaFinal = DateUtil.of(endDate);
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Tarjeta tarjeta = tarjetaRepository.findById(idTarjeta).orElseThrow(
				() -> new ApiException(ApiError.TARJETA_NOT_FOUND.getCode(), ApiError.TARJETA_NOT_FOUND.getMessage()));

		List<Comprobante> comprobantes = comprobanteRepository.searchByTipoComprobanteClienteTarjetaRangoFechas(
				tipoComprobante, cliente, tarjeta, fechaInicial, fechaFinal);
		int size = comprobantes.size();
		logger.debug("Comprobante: {}", size);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobantes, size);
	}

	public ApiResponse save(String request) throws ApiException {
		ComprobanteContext responseComp = null;

		JsonNode root;
		Integer idSerie = null;
		Long numeroComprobante = null;
		Double tipoCambio = null;
		String nombreCliente = null;
		String nuevaSerie = null;
		String emailCliente = null;
		String numeroDocumentoCliente = null;
		String direccionCliente = null;
		Integer idTipoComprobante = null;
		Integer idTipoComprobanteRef = null;
		Long idCliente = null;
		Long idTarjeta = null;
		Integer idMoneda = null;
		Integer idFormaPago = null;
		Integer tipoServicio = null;
		String comprobanteFull = null;
		String placa = null;
		String fechaEmision = null;
		String fechaVencimiento = null;
		String referencia = null;
		String observaciones = null;
		Double opGravada = null;
		Double pctIGV = null;
		Double totalIGV = null;
		Double totalMonto = null;
		String fgAnulado = null;
		String fgDetraccion = null;
		String pedido = null;
		Long registro = null;
		String fechaInicialPeriodo = null;
		String fechaFinalPeriodo = null;
		Integer diasPeriodo = null;
		String descripcion = null;
		Double cantidadComp = null;
		String bultos = null;
		Double valor = null;
		Double tarifa = null;
		String fechaHoraAnulacion = null;
		Double pctDetraccion = null;
		Double totalDetracion = null;
		Integer idTipoDocRef = null;
		String numeroDocRef = null;
		String fechaDocRef = null;
		String sustento = null;
		String usuarioAnula = null;
		ArrayNode lineas = null;
		try {
			root = new ObjectMapper().readTree(request);

			idSerie = root.path("idSerie").asInt();
			logger.debug("idSerie: {}", idSerie);

			numeroComprobante = root.path("numeroComprobante").asLong();
			logger.debug("numeroComprobante: {}", numeroComprobante);

			nuevaSerie = root.path("nuevaSerie").asText();
			logger.debug("nuevaSerie: {}", nuevaSerie);

			tipoCambio = root.path("tipoCambio").asDouble();
			logger.debug("tipoCambio: {}", tipoCambio);

			nombreCliente = root.path("nombreCliente").asText();
			logger.debug("nombreCliente: {}", nombreCliente);

			emailCliente = root.path("emailCliente").asText();
			logger.debug("emailCliente: {}", emailCliente);

			numeroDocumentoCliente = root.path("numeroDocumentoCliente").asText();
			logger.debug("numeroDocumentoCliente: {}", numeroDocumentoCliente);

			direccionCliente = root.path("direccionCliente").asText();
			logger.debug("direccionCliente: {}", direccionCliente);

			idTipoComprobante = root.path("idTipoComprobante").asInt();
			logger.debug("idTipoComprobante: {}", idTipoComprobante);

			idTipoComprobanteRef = root.path("idTipoComprobanteRef").asInt();
			logger.debug("idTipoComprobanteRef: {}", idTipoComprobanteRef);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idTarjeta = root.path("idTarjeta").asLong();
			logger.debug("idTarjeta: {}", idTarjeta);

			idMoneda = root.path("idMoneda").asInt();
			logger.debug("idMoneda: {}", idMoneda);
			idFormaPago = root.path("idFormaPago").asInt();
			logger.debug("idFormaPago: {}", idFormaPago);

			tipoServicio = root.path("tipoServicio").asInt();
			logger.debug("tipoServicio: {}", tipoServicio);

			fechaEmision = root.path("fechaEmision").asText();
			logger.debug("fechaEmision: {}", fechaEmision);

			fechaVencimiento = root.path("fechaVencimiento").asText();
			logger.debug("fechaVencimiento: {}", fechaVencimiento);

			comprobanteFull = root.path("comprobanteFull").asText();
			logger.debug("comprobanteFull: {}", comprobanteFull);

			placa = root.path("placa").asText();
			logger.debug("placa: {}", placa);

			referencia = root.path("referencia").asText();
			logger.debug("referencia: {}", referencia);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			opGravada = root.path("opGravada").asDouble();
			logger.debug("opGravada: {}", opGravada);

			pctIGV = root.path("pctIGV").asDouble();
			logger.debug("pctIGV: {}", pctIGV);
			totalIGV = root.path("totalIGV").asDouble();
			logger.debug("totalIGV: {}", totalIGV);

			totalMonto = root.path("totalMonto").asDouble();
			logger.debug("totalMonto: {}", totalMonto);

			fgAnulado = root.path("fgAnulado").asText();
			logger.debug("fgAnulado: {}", fgAnulado);

			fgDetraccion = root.path("fgDetraccion").asText();
			logger.debug("fgDetraccion: {}", fgDetraccion);

			pedido = root.path("pedido").asText();
			logger.debug("pedido: {}", pedido);

			registro = root.path("registro").asLong();
			logger.debug("registro: {}", registro);

			fechaInicialPeriodo = root.path("fechaInicialPeriodo").asText();
			logger.debug("fechaInicialPeriodo: {}", fechaInicialPeriodo);
			fechaFinalPeriodo = root.path("fechaFinalPeriodo").asText();
			logger.debug("fechaFinalPeriodo: {}", fechaFinalPeriodo);

			diasPeriodo = root.path("diasPeriodo").asInt();
			logger.debug("diasPeriodo: {}", diasPeriodo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			cantidadComp = root.path("cantidadComp").asDouble();
			logger.debug("cantidadComp: {}", cantidadComp);

			bultos = root.path("bultos").asText();
			logger.debug("bultos: {}", bultos);

			valor = root.path("valor").asDouble();
			logger.debug("valor: {}", valor);

			tarifa = root.path("tarifa").asDouble();
			logger.debug("tarifa: {}", tarifa);
			fechaHoraAnulacion = root.path("fechaHoraAnulacion").asText();
			logger.debug("fechaHoraAnulacion: {}", fechaHoraAnulacion);

			pctDetraccion = root.path("pctDetraccion").asDouble();
			logger.debug("pctDetraccion: {}", pctDetraccion);

			totalDetracion = root.path("totalDetracion").asDouble();
			logger.debug("totalDetracion: {}", totalDetracion);

			idTipoDocRef = root.path("idTipoDocRef").asInt();
			logger.debug("idTipoDocRef: {}", idTipoDocRef);

			numeroDocRef = root.path("numeroDocRef").asText();
			logger.debug("numeroDocRef: {}", numeroDocRef);

			fechaDocRef = root.path("fechaDocRef").asText();
			logger.debug("fechaDocRef: {}", fechaDocRef);

			sustento = root.path("sustento").asText();
			logger.debug("sustento: {}", sustento);
			usuarioAnula = root.path("usuarioAnula").asText();
			logger.debug("usuarioAnula: {}", usuarioAnula);

			lineas = (ArrayNode) root.path("lineas");
			logger.debug("Total líneas: {}", lineas.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		ComprobanteUtil.validateSerie(idSerie);
		// ComprobanteUtil.validaComprobanteFull(comprobanteFull);
		// ComprobanteUtil.validateNumeroComprobante(numeroComprobante);
		ComprobanteUtil.validateTipoCambio(tipoCambio);
		ComprobanteUtil.validateNombreCliente(nombreCliente);
		ComprobanteUtil.validateEmailCliente(emailCliente);
		ComprobanteUtil.validateTipoComprobante(idTipoComprobante);
		ComprobanteUtil.validateCliente(idCliente);
		ComprobanteUtil.validateMoneda(idMoneda);
		ComprobanteUtil.validateFormaPago(idFormaPago);
		ComprobanteUtil.validateTipoServicio(tipoServicio);
		ComprobanteUtil.validateFechaEmision(fechaEmision);
		ComprobanteUtil.validateFgAnulado(fgAnulado);
		ComprobanteUtil.validateFgDetraccion(fgDetraccion);

		SerieComprobante serie = serieComprobanteRepository.findById(idSerie).orElseThrow(
				() -> new ApiException(ApiError.SERIE_NOT_FOUND.getCode(), ApiError.SERIE_NOT_FOUND.getMessage()));

		// ComprobanteUtil.validateFormatComprobante(comprobanteFull);

		/*
		 * TipoComprobante tipoComprobante =
		 * tipoComprobanteRepository.findById(idTipoComprobante) .orElseThrow(() -> new
		 * ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
		 * ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));
		 */

		// Optional<Comprobante> comprobanteExistent =
		// comprobanteRepository.searchComprobante(tipoComprobante ,comprobanteFull);
		// if (comprobanteExistent.isPresent())
		// throw new ApiException(ApiError.COMPROBANTE_ALREADY_EXISTS.getCode(),
		// ApiError.COMPROBANTE_ALREADY_EXISTS.getMessage());

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Tarjeta tarjeta = null;
		if (idTarjeta != 0) {
			tarjeta = tarjetaRepository.findById(idTarjeta)
					.orElseThrow(() -> new ApiException(ApiError.TARJETA_NOT_FOUND.getCode(),
							ApiError.TARJETA_NOT_FOUND.getMessage()));
		}

		Moneda moneda = monedaRepository.findById(idMoneda).orElseThrow(
				() -> new ApiException(ApiError.MONEDA_NOT_FOUND.getCode(), ApiError.MONEDA_NOT_FOUND.getMessage()));

		FormaPago formaPago = formaPagoRepository.findById(idMoneda)
				.orElseThrow(() -> new ApiException(ApiError.FORMA_PAGO_NOT_FOUND.getCode(),
						ApiError.FORMA_PAGO_NOT_FOUND.getMessage()));

		TipoComprobante tipoComprobanteRef = null;
		if (idTipoComprobanteRef != 0) {
			tipoComprobanteRef = tipoComprobanteRepository.findById(idTipoComprobanteRef)
					.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_REF_NOT_FOUND.getCode(),
							ApiError.TIPO_COMPROBANTE_REF_NOT_FOUND.getMessage()));
		}

		try {
			Comprobante comprobante = new Comprobante();
			// comprobante.setIdComprobante(1);

			List<ComprobanteDetalle> detalles = new ArrayList<>();
			for (JsonNode node : lineas) {

				int idServicio = node.path("idServicio").asInt();
				logger.debug("idServicio: {}", idServicio);

				double precio = node.path("precioUnitario").asDouble();
				logger.debug("precio: {}", precio);

				double cantidad = node.path("cantidad").asDouble();
				logger.debug("cantidad: {}", cantidad);

				double valorUnitario = node.path("valorUnitario").asDouble();
				logger.debug("valorUnitario: {}", valorUnitario);

				double valorTotal = node.path("valorTotal").asDouble();
				logger.debug("valorTotal: {}", valorTotal);

				String descripcionDetalle = node.path("descripcionDetalle").asText();
				logger.debug("descripcionDetalle: {}", descripcionDetalle);

				String unidadMedida = node.path("unidadMedida").asText();
				logger.debug("unidadMedida: {}", unidadMedida);

				String pedidoDetalle = node.path("pedidoDetalle").asText();
				logger.debug("pedidoDetalle: {}", pedidoDetalle);

				Long registroDetalle = node.path("registroDetalle").asLong();
				logger.debug("registroDetalle: {}", registroDetalle);

				String fechaIniPerDetalle = node.path("fechaIniPerDetalle").asText();
				logger.debug("fechaIniPerDetalle: {}", fechaIniPerDetalle);

				String fechaFinPerDetalle = node.path("fechaFinPerDetalle").asText();
				logger.debug("fechaFinPerDetalle: {}", fechaFinPerDetalle);

				Integer diasPeriodoDetalle = node.path("diasPeriodoDetalle").asInt();
				logger.debug("diasPeriodoDetalle: {}", diasPeriodoDetalle);

				String descripcionAdicional = node.path("descripcionAdicional").asText();
				logger.debug("descripcionAdicional: {}", descripcionAdicional);

				Double cantidadAdicional = node.path("cantidadAdicional").asDouble();
				logger.debug("cantidadAdicional: {}", cantidadAdicional);

				String bultosDetalle = node.path("bultosDetalle").asText();
				logger.debug("bultosDetalle: {}", bultosDetalle);

				Double valorDetalle = node.path("valorDetalle").asDouble();
				logger.debug("valorDetalle: {}", valorDetalle);

				Double tarifaDetalle = node.path("tarifaDetalle").asDouble();
				logger.debug("tarifaDetalle: {}", tarifaDetalle);

				ComprobanteDetalleUtil.validateServicio(idServicio);
				ComprobanteDetalleUtil.validateDescripcionDetalle(descripcionDetalle);
				ComprobanteDetalleUtil.validateUnidadMedida(unidadMedida);

				Servicio servicio = servicioRepository.findById(idServicio)
						.orElseThrow(() -> new ApiException(ApiError.SERVICIO_NOT_FOUND.getCode(),
								ApiError.SERVICIO_NOT_FOUND.getMessage()));

				ComprobanteDetalle detalle = new ComprobanteDetalle();
				detalle.setServicio(servicio);
				detalle.setPrecioUnitario(precio);
				detalle.setCantidad(cantidad);
				detalle.setValorUnitario(valorUnitario);
				detalle.setValorTotal(valorTotal);
				detalle.setDescripcion(descripcionDetalle);
				detalle.setUnidadMedida(unidadMedida);
				detalle.setPedido(pedidoDetalle);
				detalle.setRegistro(registroDetalle);
				if (StringUtils.isNotBlank(fechaIniPerDetalle))
					detalle.setFechaInicialPeriodo(DateUtil.of(fechaIniPerDetalle));
				if (StringUtils.isNotBlank(fechaFinPerDetalle))
					detalle.setFechaInicialPeriodo(DateUtil.of(fechaFinPerDetalle));
				detalle.setDiasPeriodo(diasPeriodoDetalle);
				detalle.setDescripcionAdicional(descripcionAdicional);
				detalle.setCantidadAdicional(cantidadAdicional);
				detalle.setBultos(bultosDetalle);
				detalle.setValor(valorDetalle);
				detalle.setTarifa(tarifaDetalle);
				detalle.setComprobante(comprobante);
				detalles.add(detalle);
			}
			comprobante.setSerie(serie);
			comprobante.setComprobanteFull(comprobanteFull);
			comprobante.setCliente(cliente);
			comprobante.setNombreCliente(nombreCliente);
			comprobante.setEmailCliente(emailCliente);
			comprobante.setNumeroDocumentoCliente(numeroDocumentoCliente);
			comprobante.setDireccionCliente(direccionCliente);
			comprobante.setTipoCambio(tipoCambio);
			comprobante.setTarjeta(tarjeta);
			comprobante.setMoneda(moneda);
			comprobante.setFormaPago(formaPago);
			comprobante.setTipoServicio(tipoServicio);
			comprobante.setFechaEmision(DateUtil.of(fechaEmision));
			comprobante.setHoraEmision(DateUtil.getCurrentHour());
			comprobante.setFechaVencimiento(DateUtil.of(fechaVencimiento));
			comprobante.setReferencia(referencia);
			comprobante.setObservaciones(observaciones);
			comprobante.setFgAnulado(fgAnulado);
			comprobante.setFgDetraccion(fgDetraccion);
			comprobante.setOpGravadas(opGravada);
			comprobante.setPctIGV(pctIGV);
			comprobante.setTotalIGV(totalIGV);
			comprobante.setTotalMonto(totalMonto);
			if (StringUtils.isNotBlank(fechaInicialPeriodo))
				comprobante.setFechaInicioPeriodo(DateUtil.of(fechaInicialPeriodo));
			if (StringUtils.isNotBlank(fechaFinalPeriodo))
				comprobante.setFechaFinalPeriodo(DateUtil.of(fechaFinalPeriodo));
			comprobante.setDiasPeriodo(diasPeriodo);
			if (StringUtils.isNotBlank(fechaHoraAnulacion))
				comprobante.setFechaHoraAnulacion(DateUtil.of(fechaHoraAnulacion));
			comprobante.setPctDetraccion(pctDetraccion);
			comprobante.setTotalDetraccion(totalDetracion);
			if (tipoComprobanteRef != null) {
				comprobante.setTipoComprobanteRef(tipoComprobanteRef);
			}
			comprobante.setSustento(sustento);
			comprobante.setUsuarioAnula(usuarioAnula);

			comprobante.setLineas(detalles);

			comprobante = comprobanteRepository.save(comprobante);
			serieComprobanteRepository.updateNumero(idSerie, nuevaSerie);
			logger.debug("Se guardó Comprobante");

			responseComp = new ComprobanteContext(comprobante, comprobante.getLineas());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseComp);
	}

	public ApiResponse anular(Integer id) throws ApiException {
		comprobanteRepository.updateEstado(id, "S");
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Serie " + id + " Anulado");
	}

	public ApiResponse update(String request) throws ApiException {
		ComprobanteContext responseComp = null;

		JsonNode root;
		Integer idComprobante = null;
		Integer idSerie = null;
		Long numeroComprobante = null;
		Double tipoCambio = null;
		String nombreCliente = null;
		String nuevaSerie = null;
		String emailCliente = null;
		String numeroDocumentoCliente = null;
		String direccionCliente = null;
		Integer idTipoComprobante = null;
		Integer idTipoComprobanteRef = null;
		Long idCliente = null;
		Long idTarjeta = null;
		Integer idMoneda = null;
		Integer idFormaPago = null;
		Integer tipoServicio = null;
		String comprobanteFull = null;
		String placa = null;
		String fechaEmision = null;
		String fechaVencimiento = null;
		String referencia = null;
		String observaciones = null;
		Double opGravada = null;
		Double pctIGV = null;
		Double totalIGV = null;
		Double totalMonto = null;
		String fgAnulado = null;
		String fgDetraccion = null;
		String pedido = null;
		Long registro = null;
		String fechaInicialPeriodo = null;
		String fechaFinalPeriodo = null;
		Integer diasPeriodo = null;
		String descripcion = null;
		Double cantidadComp = null;
		String bultos = null;
		Double valor = null;
		Double tarifa = null;
		String fechaHoraAnulacion = null;
		Double pctDetraccion = null;
		Double totalDetracion = null;
		Integer idTipoDocRef = null;
		String numeroDocRef = null;
		String fechaDocRef = null;
		String sustento = null;
		String usuarioAnula = null;
		ArrayNode lineas = null;
		try {
			root = new ObjectMapper().readTree(request);

			idComprobante = root.path("idComprobante").asInt();
			logger.debug("idComprobante: {}", idComprobante);

			idSerie = root.path("idSerie").asInt();
			logger.debug("idSerie: {}", idSerie);

			numeroComprobante = root.path("numeroComprobante").asLong();
			logger.debug("numeroComprobante: {}", numeroComprobante);

			nuevaSerie = root.path("nuevaSerie").asText();
			logger.debug("nuevaSerie: {}", nuevaSerie);

			tipoCambio = root.path("tipoCambio").asDouble();
			logger.debug("tipoCambio: {}", tipoCambio);

			nombreCliente = root.path("nombreCliente").asText();
			logger.debug("nombreCliente: {}", nombreCliente);

			emailCliente = root.path("emailCliente").asText();
			logger.debug("emailCliente: {}", emailCliente);

			numeroDocumentoCliente = root.path("numeroDocumentoCliente").asText();
			logger.debug("numeroDocumentoCliente: {}", numeroDocumentoCliente);

			direccionCliente = root.path("direccionCliente").asText();
			logger.debug("direccionCliente: {}", direccionCliente);

			idTipoComprobante = root.path("idTipoComprobante").asInt();
			logger.debug("idTipoComprobante: {}", idTipoComprobante);

			idTipoComprobanteRef = root.path("idTipoComprobanteRef").asInt();
			logger.debug("idTipoComprobanteRef: {}", idTipoComprobanteRef);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idTarjeta = root.path("idTarjeta").asLong();
			logger.debug("idTarjeta: {}", idTarjeta);

			idMoneda = root.path("idMoneda").asInt();
			logger.debug("idMoneda: {}", idMoneda);

			idFormaPago = root.path("idFormaPago").asInt();
			logger.debug("idFormaPago: {}", idFormaPago);

			tipoServicio = root.path("tipoServicio").asInt();
			logger.debug("tipoServicio: {}", tipoServicio);

			fechaEmision = root.path("fechaEmision").asText();
			logger.debug("fechaEmision: {}", fechaEmision);

			fechaVencimiento = root.path("fechaVencimiento").asText();
			logger.debug("fechaVencimiento: {}", fechaVencimiento);

			comprobanteFull = root.path("comprobanteFull").asText();
			logger.debug("comprobanteFull: {}", comprobanteFull);

			placa = root.path("placa").asText();
			logger.debug("placa: {}", placa);

			referencia = root.path("referencia").asText();
			logger.debug("referencia: {}", referencia);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			opGravada = root.path("opGravada").asDouble();
			logger.debug("opGravada: {}", opGravada);

			pctIGV = root.path("pctIGV").asDouble();
			logger.debug("pctIGV: {}", pctIGV);

			totalIGV = root.path("totalIGV").asDouble();
			logger.debug("totalIGV: {}", totalIGV);

			totalMonto = root.path("totalMonto").asDouble();
			logger.debug("totalMonto: {}", totalMonto);

			fgAnulado = root.path("fgAnulado").asText();
			logger.debug("fgAnulado: {}", fgAnulado);

			fgDetraccion = root.path("fgDetraccion").asText();
			logger.debug("fgDetraccion: {}", fgDetraccion);

			pedido = root.path("pedido").asText();
			logger.debug("pedido: {}", pedido);

			registro = root.path("registro").asLong();
			logger.debug("registro: {}", registro);

			fechaInicialPeriodo = root.path("fechaInicialPeriodo").asText();
			logger.debug("fechaInicialPeriodo: {}", fechaInicialPeriodo);

			fechaFinalPeriodo = root.path("fechaFinalPeriodo").asText();
			logger.debug("fechaFinalPeriodo: {}", fechaFinalPeriodo);

			diasPeriodo = root.path("diasPeriodo").asInt();
			logger.debug("diasPeriodo: {}", diasPeriodo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			cantidadComp = root.path("cantidadComp").asDouble();
			logger.debug("cantidadComp: {}", cantidadComp);

			bultos = root.path("bultos").asText();
			logger.debug("bultos: {}", bultos);

			valor = root.path("valor").asDouble();
			logger.debug("valor: {}", valor);

			tarifa = root.path("tarifa").asDouble();
			logger.debug("tarifa: {}", tarifa);

			fechaHoraAnulacion = root.path("fechaHoraAnulacion").asText();
			logger.debug("fechaHoraAnulacion: {}", fechaHoraAnulacion);

			pctDetraccion = root.path("pctDetraccion").asDouble();
			logger.debug("pctDetraccion: {}", pctDetraccion);

			totalDetracion = root.path("totalDetracion").asDouble();
			logger.debug("totalDetracion: {}", totalDetracion);

			idTipoDocRef = root.path("idTipoDocRef").asInt();
			logger.debug("idTipoDocRef: {}", idTipoDocRef);

			numeroDocRef = root.path("numeroDocRef").asText();
			logger.debug("numeroDocRef: {}", numeroDocRef);

			fechaDocRef = root.path("fechaDocRef").asText();
			logger.debug("fechaDocRef: {}", fechaDocRef);

			sustento = root.path("sustento").asText();
			logger.debug("sustento: {}", sustento);

			usuarioAnula = root.path("usuarioAnula").asText();
			logger.debug("usuarioAnula: {}", usuarioAnula);

			lineas = (ArrayNode) root.path("lineas");
			logger.debug("Total líneas: {}", lineas.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		ComprobanteUtil.validateSerie(idSerie);
		// ComprobanteUtil.validaComprobanteFull(comprobanteFull);
		// ComprobanteUtil.validateNumeroComprobante(numeroComprobante);
		ComprobanteUtil.validateTipoCambio(tipoCambio);
		ComprobanteUtil.validateNombreCliente(nombreCliente);
		ComprobanteUtil.validateEmailCliente(emailCliente);
		ComprobanteUtil.validateTipoComprobante(idTipoComprobante);
		ComprobanteUtil.validateCliente(idCliente);
		ComprobanteUtil.validateMoneda(idMoneda);
		ComprobanteUtil.validateFormaPago(idFormaPago);
		ComprobanteUtil.validateFechaEmision(fechaEmision);
		ComprobanteUtil.validateFgAnulado(fgAnulado);
		ComprobanteUtil.validateFgDetraccion(fgDetraccion);

		SerieComprobante serie = serieComprobanteRepository.findById(idSerie).orElseThrow(
				() -> new ApiException(ApiError.SERIE_NOT_FOUND.getCode(), ApiError.SERIE_NOT_FOUND.getMessage()));

		// ComprobanteUtil.validateFormatComprobante(comprobanteFull);

		/*
		 * TipoComprobante tipoComprobante =
		 * tipoComprobanteRepository.findById(idTipoComprobante) .orElseThrow(() -> new
		 * ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
		 * ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));
		 */

		// Optional<Comprobante> comprobanteExistent =
		// comprobanteRepository.searchComprobante(tipoComprobante ,comprobanteFull);
		// if (comprobanteExistent.isPresent())
		// throw new ApiException(ApiError.COMPROBANTE_ALREADY_EXISTS.getCode(),
		// ApiError.COMPROBANTE_ALREADY_EXISTS.getMessage());

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Tarjeta tarjeta = null;
		if (idTarjeta != 0) {
			tarjeta = tarjetaRepository.findById(idTarjeta)
					.orElseThrow(() -> new ApiException(ApiError.TARJETA_NOT_FOUND.getCode(),
							ApiError.TARJETA_NOT_FOUND.getMessage()));
		}

		Moneda moneda = monedaRepository.findById(idMoneda).orElseThrow(
				() -> new ApiException(ApiError.MONEDA_NOT_FOUND.getCode(), ApiError.MONEDA_NOT_FOUND.getMessage()));

		FormaPago formaPago = formaPagoRepository.findById(idMoneda)
				.orElseThrow(() -> new ApiException(ApiError.FORMA_PAGO_NOT_FOUND.getCode(),
						ApiError.FORMA_PAGO_NOT_FOUND.getMessage()));

		TipoComprobante tipoComprobanteRef = null;
		if (idTipoComprobanteRef != 0) {
			tipoComprobanteRef = tipoComprobanteRepository.findById(idTipoComprobanteRef)
					.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_REF_NOT_FOUND.getCode(),
							ApiError.TIPO_COMPROBANTE_REF_NOT_FOUND.getMessage()));
		}

		try {
			Comprobante comprobante = new Comprobante();
			// comprobante.setIdComprobante(1);

			List<ComprobanteDetalle> detalles = new ArrayList<>();
			for (JsonNode node : lineas) {

				int idServicio = node.path("idServicio").asInt();
				logger.debug("idServicio: {}", idServicio);

				double precio = node.path("precioUnitario").asDouble();
				logger.debug("precio: {}", precio);

				double cantidad = node.path("cantidad").asDouble();
				logger.debug("cantidad: {}", cantidad);

				double valorUnitario = node.path("valorUnitario").asDouble();
				logger.debug("valorUnitario: {}", valorUnitario);

				double valorTotal = node.path("valorTotal").asDouble();
				logger.debug("valorTotal: {}", valorTotal);

				String descripcionDetalle = node.path("descripcionDetalle").asText();
				logger.debug("descripcionDetalle: {}", descripcionDetalle);

				String unidadMedida = node.path("unidadMedida").asText();
				logger.debug("unidadMedida: {}", unidadMedida);

				String pedidoDetalle = node.path("pedidoDetalle").asText();
				logger.debug("pedidoDetalle: {}", pedidoDetalle);

				Long registroDetalle = node.path("registroDetalle").asLong();
				logger.debug("registroDetalle: {}", registroDetalle);

				String fechaIniPerDetalle = node.path("fechaIniPerDetalle").asText();
				logger.debug("fechaIniPerDetalle: {}", fechaIniPerDetalle);

				String fechaFinPerDetalle = node.path("fechaFinPerDetalle").asText();
				logger.debug("fechaFinPerDetalle: {}", fechaFinPerDetalle);

				Integer diasPeriodoDetalle = node.path("diasPeriodoDetalle").asInt();
				logger.debug("diasPeriodoDetalle: {}", diasPeriodoDetalle);

				String descripcionAdicional = node.path("descripcionAdicional").asText();
				logger.debug("descripcionAdicional: {}", descripcionAdicional);

				Double cantidadAdicional = node.path("cantidadAdicional").asDouble();
				logger.debug("cantidadAdicional: {}", cantidadAdicional);

				String bultosDetalle = node.path("bultosDetalle").asText();
				logger.debug("bultosDetalle: {}", bultosDetalle);

				Double valorDetalle = node.path("valorDetalle").asDouble();
				logger.debug("valorDetalle: {}", valorDetalle);

				Double tarifaDetalle = node.path("tarifaDetalle").asDouble();
				logger.debug("tarifaDetalle: {}", tarifaDetalle);

				ComprobanteDetalleUtil.validateServicio(idServicio);
				ComprobanteDetalleUtil.validateDescripcionDetalle(descripcionDetalle);
				ComprobanteDetalleUtil.validateUnidadMedida(unidadMedida);

				Servicio servicio = servicioRepository.findById(idServicio)
						.orElseThrow(() -> new ApiException(ApiError.SERVICIO_NOT_FOUND.getCode(),
								ApiError.SERVICIO_NOT_FOUND.getMessage()));

				ComprobanteDetalle detalle = new ComprobanteDetalle();
				detalle.setServicio(servicio);
				detalle.setPrecioUnitario(precio);
				detalle.setCantidad(cantidad);
				detalle.setValorUnitario(valorUnitario);
				detalle.setValorTotal(valorTotal);
				detalle.setDescripcion(descripcionDetalle);
				detalle.setUnidadMedida(unidadMedida);
				detalle.setPedido(pedidoDetalle);
				detalle.setRegistro(registroDetalle);
				if (StringUtils.isNotBlank(fechaIniPerDetalle))
					detalle.setFechaInicialPeriodo(DateUtil.of(fechaIniPerDetalle));
				if (StringUtils.isNotBlank(fechaFinPerDetalle))
					detalle.setFechaInicialPeriodo(DateUtil.of(fechaFinPerDetalle));
				detalle.setDiasPeriodo(diasPeriodoDetalle);
				detalle.setDescripcionAdicional(descripcionAdicional);
				detalle.setCantidadAdicional(cantidadAdicional);
				detalle.setBultos(bultosDetalle);
				detalle.setValor(valorDetalle);
				detalle.setTarifa(tarifaDetalle);
				detalle.setComprobante(comprobante);
				detalles.add(detalle);
			}

			comprobante.setIdComprobante(idComprobante);
			comprobante.setSerie(serie);
			comprobante.setComprobanteFull(comprobanteFull);
			comprobante.setCliente(cliente);
			comprobante.setNombreCliente(nombreCliente);
			comprobante.setEmailCliente(emailCliente);
			comprobante.setNumeroDocumentoCliente(numeroDocumentoCliente);
			comprobante.setDireccionCliente(direccionCliente);
			comprobante.setTipoCambio(tipoCambio);
			comprobante.setTarjeta(tarjeta);
			comprobante.setMoneda(moneda);
			comprobante.setFormaPago(formaPago);
			comprobante.setTipoServicio(tipoServicio);
			comprobante.setFechaEmision(DateUtil.of(fechaEmision));
			comprobante.setHoraEmision(DateUtil.getCurrentHour());
			comprobante.setFechaVencimiento(DateUtil.of(fechaVencimiento));
			comprobante.setReferencia(referencia);
			comprobante.setObservaciones(observaciones);
			comprobante.setFgAnulado(fgAnulado);
			comprobante.setFgDetraccion(fgDetraccion);
			comprobante.setOpGravadas(opGravada);
			comprobante.setPctIGV(pctIGV);
			comprobante.setTotalIGV(totalIGV);
			comprobante.setTotalMonto(totalMonto);
			if (StringUtils.isNotBlank(fechaInicialPeriodo))
				comprobante.setFechaInicioPeriodo(DateUtil.of(fechaInicialPeriodo));
			if (StringUtils.isNotBlank(fechaFinalPeriodo))
				comprobante.setFechaFinalPeriodo(DateUtil.of(fechaFinalPeriodo));
			comprobante.setDiasPeriodo(diasPeriodo);
			if (StringUtils.isNotBlank(fechaHoraAnulacion))
				comprobante.setFechaHoraAnulacion(DateUtil.of(fechaHoraAnulacion));
			comprobante.setPctDetraccion(pctDetraccion);
			comprobante.setTotalDetraccion(totalDetracion);
			if (tipoComprobanteRef != null) {
				comprobante.setTipoComprobanteRef(tipoComprobanteRef);
			}
			comprobante.setSustento(sustento);
			comprobante.setUsuarioAnula(usuarioAnula);

			comprobante.setLineas(detalles);

			comprobante = comprobanteRepository.save(comprobante);
			// serieComprobanteRepository.updateNumero(idSerie, nuevaSerie);
			logger.debug("Se guardó Comprobante");

			responseComp = new ComprobanteContext(comprobante, comprobante.getLineas());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseComp);
	}

}
