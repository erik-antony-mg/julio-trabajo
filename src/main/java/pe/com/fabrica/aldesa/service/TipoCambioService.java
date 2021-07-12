package pe.com.fabrica.aldesa.service;

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
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.TipoCambio;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.TipoCambioRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class TipoCambioService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoCambioRepository tipoCambioRepository;
	private static final int PAGE_LIMIT = 10;

	@Autowired
	public TipoCambioService(TipoCambioRepository tipoCambioRepository) {
		this.tipoCambioRepository = tipoCambioRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<TipoCambio> ClasePage = tipoCambioRepository.findAllFechaCambio(pageable);
		logger.debug("Página {} de: {}", pageNumber, ClasePage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), ClasePage.getContent(),
				Math.toIntExact(ClasePage.getTotalElements()));
	}

	public ApiResponse findByRangoFechas(String startDate, String endDate,Integer pageNumber)
			throws ApiException {
		Date fechaInicial = DateUtil.of(startDate);
		Date fechaFinal = DateUtil.of(endDate);
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);

		Page<TipoCambio> tipoCambioPage = tipoCambioRepository.searchByRangoFechas(fechaInicial, fechaFinal, pageable);
		logger.debug("Página {} de: {}", pageNumber, tipoCambioPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tipoCambioPage.getContent(),
				Math.toIntExact(tipoCambioPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		TipoCambio tmpTipoCambio = tipoCambioRepository.findById(id).orElse(null);
		logger.debug("Tipo Cambio: {}", tmpTipoCambio);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTipoCambio);
	}

	public ApiResponse save(String request) throws ApiException {
		TipoCambio responseTipoCambio;

		JsonNode root;
		String fechaTipoCambio = null;
		Double cambioCompra = null;
		Double cambioVenta = null;
		try {
			root = new ObjectMapper().readTree(request);

			cambioCompra = root.path("cambioCompra").asDouble();
			logger.debug("cambioCompra: {}", cambioCompra);

			cambioVenta = root.path("cambioVenta").asDouble();
			logger.debug("cambioVenta: {}", cambioVenta);

			fechaTipoCambio = root.path("fechaTipoCambio").asText();
			logger.debug("fechaTipoCambio: {}", fechaTipoCambio);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		try {
			TipoCambio tipoCambio = new TipoCambio();
			tipoCambio.setCambioCompra(cambioCompra);
			tipoCambio.setCambioVenta(cambioVenta);
			if (StringUtils.isNotBlank(fechaTipoCambio))
				tipoCambio.setFechaTipoCambio(DateUtil.of(fechaTipoCambio));
			responseTipoCambio = tipoCambioRepository.save(tipoCambio);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoCambio);
	}

	public ApiResponse update(String request) throws ApiException {
		TipoCambio responseTipoCambio;

		JsonNode root;
		Integer id = null;
		String fechaTipoCambio = null;
		Double cambioCompra = null;
		Double cambioVenta = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			cambioCompra = root.path("cambioCompra").asDouble();
			logger.debug("cambioCompra: {}", cambioCompra);

			cambioVenta = root.path("cambioVenta").asDouble();
			logger.debug("cambioVenta: {}", cambioVenta);

			fechaTipoCambio = root.path("fechaTipoCambio").asText();
			logger.debug("fechaTipoCambio: {}", fechaTipoCambio);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		try {
			TipoCambio tipoCambio = new TipoCambio();
			tipoCambio.setCambioCompra(cambioCompra);
			tipoCambio.setCambioVenta(cambioVenta);
			if (StringUtils.isNotBlank(fechaTipoCambio))
				tipoCambio.setFechaTipoCambio(DateUtil.of(fechaTipoCambio));
			tipoCambio.setIdTipoCambio(id);

			responseTipoCambio = tipoCambioRepository.save(tipoCambio);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoCambio);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		TipoCambio tmpTipoCambio = tipoCambioRepository.findById(id).orElse(null);
		logger.debug("Tipo cambio: {}", tmpTipoCambio);
		if (null != tmpTipoCambio) {
			tipoCambioRepository.deleteById(id);
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Tipo cambio " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByFecha(String fechaTipoCambio) {
		List<TipoCambio> Clase = tipoCambioRepository.findbyFecha(DateUtil.of(fechaTipoCambio));
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Clase, Clase.size());
	}

	public ApiResponse ultimoRegistro() {
		TipoCambio Clase = tipoCambioRepository.ultimoRegistro();
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Clase);
	}

	public ApiResponse obteneerRangoFecha(String fechaTipoCambio1, String fechaTipoCambio2) {
		List<TipoCambio> Clase = tipoCambioRepository.obteneerRangoFecha(DateUtil.of(fechaTipoCambio1),
				DateUtil.of(fechaTipoCambio2));
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Clase, Clase.size());
	}

	public ApiResponse obtenerPromedio(Integer año) {
		List<TipoCambio> Clase = tipoCambioRepository.obtenerPromedio(año);
		logger.debug("-------<>>" + Clase.size()) ;
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Clase, Clase.size());
	}

}
