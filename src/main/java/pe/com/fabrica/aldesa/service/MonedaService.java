package pe.com.fabrica.aldesa.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Moneda;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.MonedaRepository;

@Service
public class MonedaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MonedaRepository monedaRepository;

	@Autowired
	public MonedaService(MonedaRepository monedaRepository) {
		this.monedaRepository = monedaRepository;
	}

	public ApiResponse findAll() {
		List<Moneda> monedas = monedaRepository.findAll();
		int total = monedas.size();
		logger.debug("Total monedas: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), monedas, total);
	}

	public ApiResponse findById(Integer id) {
		Moneda tmpMoneda = monedaRepository.findById(id).orElse(null);
		logger.debug("Moneda: {}", tmpMoneda);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpMoneda);
	}

	public ApiResponse save(String request) throws ApiException {
		Moneda responseMoneda;

		JsonNode root;
		String	nombre = null;
		String	simbolo = null;
		String	abreviatura = null;
		String	codigoDivisa = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			simbolo = root.path("simbolo").asText();
			logger.debug("simbolo: {}", simbolo);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			codigoDivisa = root.path("codigoDivisa").asText();
			logger.debug("codigoDivisa: {}", codigoDivisa);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || StringUtils.isBlank(simbolo) || StringUtils.isBlank(abreviatura) || StringUtils.isBlank(codigoDivisa) || codigoDivisa.length() != 3) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Moneda moneda = new Moneda();
			moneda.setNombre(nombre);
			moneda.setSimbolo(simbolo);
			moneda.setAbreviatura(abreviatura.toUpperCase());
			moneda.setCodigoDivisa(codigoDivisa);

			responseMoneda = monedaRepository.save(moneda);
			logger.debug("Moneda guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseMoneda);
	}

	public ApiResponse update(String request) throws ApiException {
		Moneda responseMoneda;

		JsonNode root;
		Integer	id = null;
		String	nombre = null;
		String	simbolo = null;
		String	abreviatura = null;
		String	codigoDivisa = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			simbolo = root.path("simbolo").asText();
			logger.debug("simbolo: {}", simbolo);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			codigoDivisa = root.path("codigoDivisa").asText();
			logger.debug("codigoDivisa: {}", codigoDivisa);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(simbolo) || StringUtils.isBlank(abreviatura) || codigoDivisa.length() != 3) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsMoneda = monedaRepository.existsById(id);
		logger.debug("Existe Moneda? {}", existsMoneda);
		if (!existsMoneda) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		try {
			Moneda moneda = new Moneda();
			moneda.setIdMoneda(id);
			moneda.setNombre(nombre);
			moneda.setSimbolo(simbolo);
			moneda.setAbreviatura(abreviatura.toUpperCase());
			moneda.setCodigoDivisa(codigoDivisa);

			responseMoneda = monedaRepository.save(moneda);
			logger.debug("Moneda actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseMoneda);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Moneda tmpMoneda = monedaRepository.findById(id).orElse(null);
		logger.debug("Moneda: {}", tmpMoneda);
		if (null != tmpMoneda) {
			monedaRepository.deleteById(id);
			logger.debug("Moneda eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Moneda " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

}
