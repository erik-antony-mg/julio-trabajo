package pe.com.fabrica.aldesa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import pe.com.fabrica.aldesa.dto.CotizacionContext;
import pe.com.fabrica.aldesa.beans.Cliente;
import pe.com.fabrica.aldesa.beans.Cotizacion;
import pe.com.fabrica.aldesa.beans.CotizacionDetalle;
import pe.com.fabrica.aldesa.beans.Moneda;
import pe.com.fabrica.aldesa.beans.Servicio;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ClienteRepository;
import pe.com.fabrica.aldesa.repository.CotizacionRepository;
import pe.com.fabrica.aldesa.repository.MonedaRepository;
import pe.com.fabrica.aldesa.repository.ServicioRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class CotizacionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CotizacionRepository cotizacionRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private MonedaRepository monedaRepository;

	private static final int PAGE_LIMIT = 10;

	/*
	 * public ApiResponse findAll() { List<Cotizacion> cotizaciones =
	 * cotizacionRepository.findAll(); int total = cotizaciones.size();
	 * logger.debug("Total Cotizaciones: {}", total);
	 * 
	 * List<CotizacionContext> allCotizaciones = new ArrayList<>(); for (Cotizacion
	 * cotizacion : cotizaciones) { CotizacionContext cotContent = new
	 * CotizacionContext(cotizacion, cotizacion.getLineas());
	 * allCotizaciones.add(cotContent); } return
	 * ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
	 * allCotizaciones, total); }
	 */

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Cotizacion> allCotizaciones = cotizacionRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, allCotizaciones.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), allCotizaciones.getContent(),
				Math.toIntExact(allCotizaciones.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Cotizacion tmpCotizacion = cotizacionRepository.findById(id).orElse(null);
		logger.debug("Cotizacion: {}", tmpCotizacion);
		CotizacionContext cotContent = null;
		if (null != tmpCotizacion)
			cotContent = new CotizacionContext(tmpCotizacion, tmpCotizacion.getLineas());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), cotContent);
	}

	public ApiResponse findByRangoFechas(String startDate, String endDate) {
		Date fechaInicial = DateUtil.of(startDate);
		Date fechaFinal = DateUtil.of(endDate);
		List<Cotizacion> cotizaciones = cotizacionRepository.searchByRangoFechas(fechaInicial, fechaFinal);
		int size = cotizaciones.size();
		logger.debug("Total cotizaciones: {}", size);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), cotizaciones, size);
	}

	public ApiResponse save(String request) throws ApiException {
		CotizacionContext responseCoti = null;

		JsonNode root;
		Long idCliente = null;
		Integer idMoneda = null;
		String etapa = null;
		String codigo = null;
		String nroCarta = null;
		String fecha = null;
		Double precioTotal = null;
		String referencia = null;
		String observaciones = null;
		ArrayNode lineas = null;
		try {
			root = new ObjectMapper().readTree(request);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idMoneda = root.path("idMoneda").asInt();
			logger.debug("idMoneda: {}", idMoneda);

			etapa = root.path("etapa").asText();
			logger.debug("etapa: {}", etapa);

			fecha = root.path("fecha").asText();
			logger.debug("fecha: {}", fecha);

			precioTotal = root.path("precioTotal").asDouble();
			logger.debug("precioTotal: {}", precioTotal);

			nroCarta = root.path("nroCarta").asText();
			logger.debug("nroCarta: {}", nroCarta);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			referencia = root.path("referencia").asText();
			logger.debug("referencia: {}", referencia);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			lineas = (ArrayNode) root.path("lineas");
			logger.debug("Total líneas: {}", lineas.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));
		logger.debug("Cliente: {}", cliente);

		Moneda moneda = monedaRepository.findById(idMoneda).orElseThrow(
				() -> new ApiException(ApiError.MONEDA_NOT_FOUND.getCode(), ApiError.MONEDA_NOT_FOUND.getMessage()));
		logger.debug("Moneda: {}", moneda);

		try {
			Cotizacion cotizacion = new Cotizacion();
			List<CotizacionDetalle> detalles = new ArrayList<>();
			for (JsonNode node : lineas) {

				int idServicio = node.path("id").asInt();
				logger.debug("id: {}", idServicio);

				double precio = node.path("precio").asDouble();
				logger.debug("precio: {}", precio);

				String tipoCantidad = node.path("TipoCantidad").asText();
				logger.debug("TipoCantidad: {}", tipoCantidad);

				Servicio servicio = servicioRepository.findById(idServicio)
						.orElseThrow(() -> new ApiException(ApiError.SERVICIO_NOT_FOUND.getCode(),
								ApiError.SERVICIO_NOT_FOUND.getMessage()));
				logger.debug("Servicio: {}", servicio);

				CotizacionDetalle detalle = new CotizacionDetalle();
				detalle.setServicio(servicio);
				detalle.setPrecio(precio);
				detalle.setCotizacion(cotizacion);
				detalle.setTipoCantidad(tipoCantidad);
				detalles.add(detalle);
			}

			cotizacion.setCliente(cliente);
			cotizacion.setMoneda(moneda);
			cotizacion.setEtapa(etapa);
			cotizacion.setNroCarta(nroCarta);
			cotizacion.setCodigo(codigo);
			cotizacion.setFecha(DateUtil.of(fecha));
			cotizacion.setObservaciones(observaciones);
			cotizacion.setLineas(detalles);
			cotizacion.setReferencia(referencia);
			cotizacion = cotizacionRepository.save(cotizacion);
			logger.debug("Se guardó Cotizacion");

			responseCoti = new CotizacionContext(cotizacion, cotizacion.getLineas());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseCoti);
	}

	public ApiResponse update(String request) throws ApiException {
		CotizacionContext responseCoti;

		JsonNode root;
		Long idCotizacion = null;
		Long idCliente = null;
		Integer idMoneda = null;
		String etapa = null;
		String codigo = null;
		String nroCarta = null;
		String fecha = null;
		Double precioTotal = null;
		String referencia = null;
		String observaciones = null;
		ArrayNode lineas = null;

		try {
			root = new ObjectMapper().readTree(request);

			idCotizacion = root.path("idCotizacion").asLong();
			logger.debug("idCotizacion: {}", idCotizacion);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idMoneda = root.path("idMoneda").asInt();
			logger.debug("idMoneda: {}", idMoneda);

			etapa = root.path("etapa").asText();
			logger.debug("etapa: {}", etapa);

			fecha = root.path("fecha").asText();
			logger.debug("fecha: {}", fecha);

			precioTotal = root.path("precioTotal").asDouble();
			logger.debug("precioTotal: {}", precioTotal);

			nroCarta = root.path("nroCarta").asText();
			logger.debug("nroCarta: {}", nroCarta);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			referencia = root.path("referencia").asText();
			logger.debug("referencia: {}", referencia);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			lineas = (ArrayNode) root.path("lineas");
			logger.debug("Total líneas: {}", lineas.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		boolean existCotizacion = cotizacionRepository.existsById(idCotizacion);
		logger.debug("Exists cotización {}? {}", idCotizacion, existCotizacion);
		if (!existCotizacion) {
			throw new ApiException(ApiError.COTIZACION_NOT_FOUND.getCode(), ApiError.COTIZACION_NOT_FOUND.getMessage());
		}

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));
		logger.debug("Cliente: {}", cliente);

		Moneda moneda = monedaRepository.findById(idMoneda).orElseThrow(
				() -> new ApiException(ApiError.MONEDA_NOT_FOUND.getCode(), ApiError.MONEDA_NOT_FOUND.getMessage()));
		logger.debug("Moneda: {}", moneda);

		try {
			Cotizacion cotizacion = new Cotizacion();
			cotizacion.setIdCotizacion(idCotizacion);

			List<CotizacionDetalle> detalles = new ArrayList<>();
			for (JsonNode node : lineas) {

				Long uuid = node.path("idDetalle").asLong();
				logger.debug("idDetalle: {}", uuid);

				int idServicio = node.path("id").asInt();
				logger.debug("id: {}", idServicio);
				
				String tipoCantidad = node.path("TipoCantidad").asText();
				logger.debug("TipoCantidad: {}", tipoCantidad);

				double precio = node.path("precio").asDouble();
				logger.debug("precio: {}", precio);

				if (idServicio == 0) {
					throw new ApiException(ApiError.QUOTATION_LINES.getCode(), ApiError.QUOTATION_LINES.getMessage());
				}

				Servicio servicio = servicioRepository.findById(idServicio)
						.orElseThrow(() -> new ApiException(ApiError.SERVICIO_NOT_FOUND.getCode(),
								ApiError.SERVICIO_NOT_FOUND.getMessage()));
				logger.debug("Servicio: {}", servicio);

				CotizacionDetalle detalle = new CotizacionDetalle();
				detalle.setIdDetalle(uuid);
				detalle.setServicio(servicio);
				detalle.setPrecio(precio);
				detalle.setTipoCantidad(tipoCantidad);
				detalle.setCotizacion(cotizacion);
				detalles.add(detalle);
			}

			cotizacion.setCliente(cliente);
			cotizacion.setMoneda(moneda);
			cotizacion.setEtapa(etapa);
			cotizacion.setNroCarta(nroCarta);
			cotizacion.setCodigo(codigo);
			cotizacion.setFecha(DateUtil.of(fecha));
			cotizacion.setObservaciones(observaciones);
			cotizacion.setLineas(detalles);
			cotizacion.setReferencia(referencia);

			cotizacion = cotizacionRepository.save(cotizacion);
			logger.debug("Se actualizó Cotizacion");

			responseCoti = new CotizacionContext(cotizacion, cotizacion.getLineas());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseCoti);
	}

	public ApiResponse searchByFiltro(String filtro) {
		List<Cotizacion> cotizaciones = cotizacionRepository.searchByFiltro(filtro);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), cotizaciones,
				cotizaciones.size());
	}

	public ApiResponse searchByFiltroCliente(String filtro, Integer idCliente) {
		List<Cotizacion> cotizaciones = cotizacionRepository.searchByFiltroCliente(filtro, idCliente);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), cotizaciones,
				cotizaciones.size());
	}

	public ApiResponse searchByCliente(Integer idCliente) {
		List<Cotizacion> cotizaciones = cotizacionRepository.searchByCliente(idCliente);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), cotizaciones,
				cotizaciones.size());
	}

}
