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
import pe.com.fabrica.aldesa.beans.GrupoServicio;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.GrupoServicioRepository;

@Service
public class GrupoServicioService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private GrupoServicioRepository grupoServicioRepository;

	@Autowired
	public GrupoServicioService(GrupoServicioRepository grupoServicioRepository) {
		this.grupoServicioRepository = grupoServicioRepository;
	}

	public ApiResponse findAll() {
		List<GrupoServicio> gruposServicio = grupoServicioRepository.findAll();
		int total = gruposServicio.size();
		logger.debug("Total GrupoServicio: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), gruposServicio, total);
	}

	public ApiResponse findById(Integer id) {
		GrupoServicio tmpGrupoServicio = grupoServicioRepository.findById(id).orElse(null);
		logger.debug("GrupoServicio: {}", tmpGrupoServicio);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpGrupoServicio);
	}

	public ApiResponse save(String request) throws ApiException {
		GrupoServicio responseGrupoServicio;

		JsonNode root;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			GrupoServicio grupoServicio = new GrupoServicio();
			grupoServicio.setNombre(nombre);

			responseGrupoServicio = grupoServicioRepository.save(grupoServicio);
			logger.debug("GrupoServicio guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseGrupoServicio);
	}

	public ApiResponse update(String request) throws ApiException {
		GrupoServicio responseGrupoServicio;

		JsonNode root;
		String	nombre = null;
		Integer id = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || null == id || id == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			GrupoServicio grupoServicio = new GrupoServicio();
			grupoServicio.setIdGrupoServicio(id);
			grupoServicio.setNombre(nombre);

			responseGrupoServicio = grupoServicioRepository.save(grupoServicio);
			logger.debug("GrupoServicio actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseGrupoServicio);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		GrupoServicio tmpGrupoServicio = grupoServicioRepository.findById(id).orElse(null);
		logger.debug("GrupoServicio: {}", tmpGrupoServicio);
		if (null != tmpGrupoServicio) {
			grupoServicioRepository.deleteById(id);
			logger.debug("GrupoServicio eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "GrupoServicio " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}


}
