package pe.com.fabrica.aldesa.service;

import java.util.ArrayList;
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
import pe.com.fabrica.aldesa.dto.MercaderiaContex;
import pe.com.fabrica.aldesa.dto.MovimientoMercanciaContext;
import pe.com.fabrica.aldesa.beans.Mercancia;
import pe.com.fabrica.aldesa.beans.MercanciaDetalle;
import pe.com.fabrica.aldesa.beans.Movimiento;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.MercanciaDetalleRepository;
import pe.com.fabrica.aldesa.repository.MercanciaRepository;
import pe.com.fabrica.aldesa.repository.MovimientoRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class MovimientoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final int PAGE_LIMIT = 10;

	@Autowired
	private MovimientoRepository movimientoRepository;

	@Autowired
	private MercanciaDetalleRepository mercanciaDetalleRepository;

	@Autowired
	private MercanciaRepository mercanciaRepository;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Movimiento> movimientosPage = movimientoRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, movimientosPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), movimientosPage.getContent(),
				Math.toIntExact(movimientosPage.getTotalElements()));
	}

	public ApiResponse findByTipoFecha(String tipoMovimiento, String fechaInicio, String fechaFin, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Movimiento> movimientos = movimientoRepository.findByTipoFecha(tipoMovimiento, fechaInicio, fechaFin,
				pageable);
		logger.debug("Página {} de: {}", pageNumber, movimientos.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), movimientos.getContent(),
				Math.toIntExact(movimientos.getTotalElements()));
	}

	public ApiResponse findByFecha(String fechaInicio, String fechaFin, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Movimiento> movimientos = movimientoRepository.findByFecha(fechaInicio, fechaFin, pageable);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), movimientos.getContent(),
				Math.toIntExact(movimientos.getTotalElements()));
	}

	public ApiResponse findByTipoAndMercancia(String tipoMovimiento, Integer idmercancia) {
		List<Movimiento> movimientos = movimientoRepository.findByTipoAndMercancia(tipoMovimiento, idmercancia);
		int total = movimientos.size();
		logger.debug("Total Movimientos: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), movimientos, total);
	}

	public ApiResponse findById(Long id) {
		Movimiento tmpMovimiento = movimientoRepository.findById(id).orElse(null);
		logger.debug("Movimiento: {}", tmpMovimiento);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpMovimiento);
	}

	public ApiResponse findMovimientoByTarjetaByNumero(String nroTarjeta){
		List<Movimiento> movimientos = movimientoRepository.findMovimientoByTarjetaByNumero(nroTarjeta);
		logger.debug("movimientos: {}", movimientos);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),movimientos, movimientos.size());
	}

	public ApiResponse findMovimientoByTarjetaByClienteByRuc(String ruc){
		List<Movimiento> movimientos = movimientoRepository.findMovimientoByTarjetaByClienteByRuc(ruc);
		logger.debug("movimientos: {}", movimientos);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), movimientos, movimientos.size());
	}

	public ApiResponse findMovimientoByTarjetaByClienteByRazonSocial(String razonSocial){
		List<Movimiento> movimientos = movimientoRepository.findMovimientoByTarjetaByClienteByRazonSocial(razonSocial);
		logger.debug("movimientos: {}", movimientos);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), movimientos, movimientos.size());
	}

	public ApiResponse save(String request) throws ApiException {
		MercaderiaContex responseMovimiento;

		JsonNode root;
		Long idMercancia = null;
		String tipo = null;
		String fechaMovimiento = null;
		ArrayNode mercanciaDetalle = null;

		try {
			root = new ObjectMapper().readTree(request);

			idMercancia = root.path("idMercancia").asLong();
			logger.debug("idMercancia: {}", idMercancia);

			tipo = root.path("tipo").asText();
			logger.debug("tipo: {}", tipo);

			fechaMovimiento = root.path("fechaMovimiento").asText();
			logger.debug("fechaMovimiento: {}", fechaMovimiento);
			logger.info("-----");
			logger.info(fechaMovimiento);


			mercanciaDetalle = (ArrayNode) root.path("mercanciaDetalle");
			logger.debug("Total mercanciaDetalle: {}", mercanciaDetalle.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (idMercancia == 0 || StringUtils.isBlank(tipo) || StringUtils.isBlank(fechaMovimiento)
				|| "null".equals(fechaMovimiento)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Mercancia mercancia = mercanciaRepository.findById(idMercancia).orElseThrow(
				() -> new ApiException(ApiError.TARJETA_NOT_FOUND.getCode(), ApiError.TARJETA_NOT_FOUND.getMessage()));

		logger.debug("mercancia: {}", mercancia);

		try {
			List<MercanciaDetalle> listMercancia = new ArrayList<>();
			List<Movimiento> listMovimiento = new ArrayList<>();
			MercanciaDetalle detalles = new MercanciaDetalle();

			for (JsonNode node : mercanciaDetalle) {

				String descripcion = node.path("descripcion").asText();
				logger.debug("descripcion: {}", descripcion);

				Long idDetalleMercancia = node.path("idDetalleMercancia").asLong();
				logger.debug("idDetalleMercancia: {}", idDetalleMercancia);

				double valorUS$ = node.path("valorUS$").asDouble();
				logger.debug("valorUS$: {}", valorUS$);

				int bueno = node.path("bueno").asInt();
				logger.debug("bueno: {}", bueno);

				int cantidad = node.path("cantidad").asInt();
				logger.debug("cantidad: {}", cantidad);

				int cantidadNueva = node.path("cantidadNueva").asInt();
				logger.debug("cantidadNueva: {}", cantidadNueva);

				if (node.path("idDetalleMercancia").asInt() == 0) {
					MercanciaDetalle detalle = new MercanciaDetalle();
					Movimiento movimiento = new Movimiento();
					
					movimiento.setFechaMovimiento(DateUtil.of(fechaMovimiento));
					movimiento.setHoraMovimiento(DateUtil.getCurrentHour());
					logger.info("*******");
					logger.info(fechaMovimiento);
					logger.info("*******");
					movimiento.setTipo(tipo);
					movimiento.setMercaderia_detalle(detalle);
					movimiento.setCantidad(cantidad);
					movimiento.setAnulado("SA");
					listMovimiento.add(movimiento);

					detalle.setBueno(bueno);
					detalle.setCantidad(cantidad);
					detalle.setDescripcion(descripcion);
					detalle.setValorUS$(valorUS$);
					detalle.setMercancia(mercancia);
					detalle.setListmovimiento(listMovimiento);
					detalle = mercanciaDetalleRepository.save(detalle);
					listMovimiento = new ArrayList<>();
				} else {
					if (cantidadNueva != 0) {

						MercanciaDetalle detalle = new MercanciaDetalle();
						Movimiento movimiento = new Movimiento();
						movimiento.setFechaMovimiento(DateUtil.of(fechaMovimiento));
						movimiento.setHoraMovimiento(DateUtil.getCurrentHour());
						movimiento.setTipo(tipo);
						movimiento.setMercaderia_detalle(detalle);
						movimiento.setCantidad(cantidadNueva);
						movimiento.setAnulado("SA");
						listMovimiento.add(movimiento);

						detalle.setIdDetalleMercancia(idDetalleMercancia);
						detalle.setBueno(bueno);
						detalle.setCantidad(cantidad);
						detalle.setDescripcion(descripcion);
						detalle.setValorUS$(valorUS$);
						detalle.setMercancia(mercancia);
						detalle.setListmovimiento(listMovimiento);

						detalle = mercanciaDetalleRepository.save(detalle);

						listMovimiento = new ArrayList<>();

					}

				}
			}
			logger.debug("Se guardó Tarjeta");
			responseMovimiento = new MercaderiaContex(listMercancia, detalles.getListmovimiento());
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseMovimiento);
	}

	public ApiResponse anulacion(String request) throws ApiException {
		MovimientoMercanciaContext responseMovimiento;
		JsonNode root;
		Long idMovimiento = null;
		Long idDetalleMercancia = null;
		String fechaMovimiento = null;
		Integer cantidad = null;
		String tipo = null;

		try {
			root = new ObjectMapper().readTree(request);

			idMovimiento = root.path("idMovimiento").asLong();
			logger.debug("idMovimiento: {}", idMovimiento);

			idDetalleMercancia = root.path("idDetalleMercancia").asLong();
			logger.debug("idDetalleMercancia: {}", idDetalleMercancia);

			fechaMovimiento = root.path("fechaMovimiento").asText();
			logger.debug("fechaMovimiento: {}", fechaMovimiento);

			cantidad = root.path("cantidad").asInt();
			logger.debug("cantidad: {}", cantidad);

			tipo = root.path("tipo").asText();
			logger.debug("tipo: {}", tipo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (idMovimiento == 0 || idDetalleMercancia == 0 || StringUtils.isBlank(fechaMovimiento)
				|| "null".equals(fechaMovimiento)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		MercanciaDetalle mercanciaDetalleObtenida = mercanciaDetalleRepository.findById(idDetalleMercancia).orElseThrow(
				() -> new ApiException(ApiError.TICKET_NOT_FOUND.getCode(), ApiError.TICKET_NOT_FOUND.getMessage()));

		try {
			MercanciaDetalle mercaderiaDetalle = new MercanciaDetalle();
			mercaderiaDetalle.setIdDetalleMercancia(mercanciaDetalleObtenida.getIdDetalleMercancia());
			mercaderiaDetalle.setDescripcion(mercanciaDetalleObtenida.getDescripcion());
			if (tipo.equals("I")) {
				mercaderiaDetalle.setCantidad(mercanciaDetalleObtenida.getCantidad() - cantidad);
				mercaderiaDetalle.setBueno(mercanciaDetalleObtenida.getBueno() - cantidad);
			} else {
				mercaderiaDetalle.setCantidad(mercanciaDetalleObtenida.getCantidad() + cantidad);
				mercaderiaDetalle.setBueno(mercanciaDetalleObtenida.getBueno() + cantidad);
			}
			mercaderiaDetalle.setValorUS$(mercanciaDetalleObtenida.getValorUS$());
			mercaderiaDetalle.setMercancia(mercanciaDetalleObtenida.getMercancia());

			mercaderiaDetalle = mercanciaDetalleRepository.save(mercaderiaDetalle);

			Movimiento movimiento = new Movimiento();
			movimiento.setIdMovimiento(idMovimiento);
			movimiento.setMercaderia_detalle(mercaderiaDetalle);
			movimiento.setTipo(tipo);
			movimiento.setCantidad(cantidad);
			movimiento.setFechaMovimiento(DateUtil.of(fechaMovimiento));
			movimiento.setAnulado("A");

			movimiento = movimientoRepository.save(movimiento);

			responseMovimiento = new MovimientoMercanciaContext(mercaderiaDetalle, movimiento);
			logger.debug("Movimiento Anulado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseMovimiento);
	}

}
