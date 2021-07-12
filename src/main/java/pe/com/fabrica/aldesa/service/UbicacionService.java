package pe.com.fabrica.aldesa.service;

import java.util.List;
import java.util.Optional;

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
import pe.com.fabrica.aldesa.beans.Area;
import pe.com.fabrica.aldesa.beans.Ubicacion;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.AreaRepository;
import pe.com.fabrica.aldesa.repository.UbicacionRepository;

@Service
public class UbicacionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	private AreaRepository areaRepository;

	public ApiResponse findAll() {
		List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
		int total = ubicaciones.size();
		logger.debug("Total Ubicaciones: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), ubicaciones, total);
	}

	public ApiResponse findById(Long id) {
		Ubicacion tmpUbicacion = ubicacionRepository.findById(id).orElse(null);
		logger.debug("Ubicacion: {}", tmpUbicacion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpUbicacion);
	}

	public ApiResponse findByArea(Integer idArea) throws ApiException {
		Optional<Area> optArea = areaRepository.findById(idArea);
		if (!optArea.isPresent()) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		List<Ubicacion> ubicaciones = ubicacionRepository.findByArea(optArea.get());
		logger.debug("Ubicacion by id Area: {}", ubicaciones);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), ubicaciones);
	}

	public ApiResponse save(String request) throws ApiException {
		Ubicacion responseUbicacion;

		JsonNode root;
		Integer idArea = null;
		String nombre = null;
		String abreviatura = null;
		Integer numeroRack = null;
		String activo = null;
		try {
			root = new ObjectMapper().readTree(request);

			idArea = root.path("idArea").asInt();
			logger.debug("idArea: {}", idArea);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			numeroRack = root.path("numeroRack").asInt();
			logger.debug("numeroRack: {}", numeroRack);

			activo = root.path("activo").asText();
			logger.debug("activo: {}", activo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idArea || idArea == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(activo)
				|| StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Optional<Area> opArea = areaRepository.findById(idArea);
		logger.debug("Area: {}", opArea);
		if (!opArea.isPresent()) throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());

		try {
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setArea(opArea.get());
			ubicacion.setAbreviatura(abreviatura);
			ubicacion.setNombre(nombre);
			ubicacion.setNumeroRack(numeroRack);
			ubicacion.setActivo(activo);

			responseUbicacion = ubicacionRepository.save(ubicacion);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUbicacion);
	}

	public ApiResponse update(String request) throws ApiException {
		Ubicacion responseUbicacion;

		JsonNode root;
		Long id = null;
		Integer idArea = null;
		String nombre = null;
		String abreviatura = null;
		Integer numeroRack = null;
		String activo = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			idArea = root.path("idArea").asInt();
			logger.debug("idArea: {}", idArea);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			numeroRack = root.path("numeroRack").asInt();
			logger.debug("numeroRack: {}", numeroRack);

			activo = root.path("activo").asText();
			logger.debug("activo: {}", activo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idArea || idArea == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(activo)
				|| StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Optional<Area> opArea = areaRepository.findById(idArea);
		logger.debug("Area: {}", opArea);
		if (!opArea.isPresent()) throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());

		try {
			Ubicacion ubicacion = new Ubicacion();
			ubicacion.setIdUbicacion(id);
			ubicacion.setArea(opArea.get());
			ubicacion.setAbreviatura(abreviatura);
			ubicacion.setNombre(nombre);
			ubicacion.setNumeroRack(numeroRack);
			ubicacion.setActivo(activo);

			responseUbicacion = ubicacionRepository.save(ubicacion);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUbicacion);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Ubicacion tmpUbicacion = ubicacionRepository.findById(id).orElse(null);
		logger.debug("Ubicacion: {}", tmpUbicacion);
		if (null != tmpUbicacion) {
			ubicacionRepository.deleteById(id);
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Ubicacion " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

}
