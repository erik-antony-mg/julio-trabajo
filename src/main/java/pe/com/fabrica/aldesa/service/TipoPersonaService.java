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
import pe.com.fabrica.aldesa.beans.TipoPersona;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.TipoPersonaRepository;

@Service
public class TipoPersonaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoPersonaRepository tipoPersonaRepository;

	@Autowired
	public TipoPersonaService(TipoPersonaRepository tipoPersonaRepository) {
		this.tipoPersonaRepository = tipoPersonaRepository;
	}

	public ApiResponse findAll() {
		List<TipoPersona> tiposPersonas = tipoPersonaRepository.findAll();
		int total = tiposPersonas.size();
		logger.debug("Total Tipos de personas: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tiposPersonas, total);
	}

	public ApiResponse findById(Integer id) {
		TipoPersona tmpTipoPersona = tipoPersonaRepository.findById(id).orElse(null);
		logger.debug("Tipo persona: {}", tmpTipoPersona);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTipoPersona);
	}

	public ApiResponse save(String request) throws ApiException {
		TipoPersona responseTipoPersona;

		JsonNode root;
		String	nombre = null;
		String	abreviatura = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setNombre(nombre);
			tipoPersona.setAbreviatura(abreviatura.toUpperCase());

			responseTipoPersona = tipoPersonaRepository.save(tipoPersona);
			logger.debug("Tipo persona guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoPersona);
	}

	public ApiResponse update(String request) throws ApiException {
		TipoPersona responseTipoPersona;

		JsonNode root;
		Integer	id = null;
		String	nombre = null;
		String	abreviatura = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsTipoPersona = tipoPersonaRepository.existsById(id);
		logger.debug("Existe Tipo Persona? {}", existsTipoPersona);
		if (!existsTipoPersona) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		try {
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(id);
			tipoPersona.setNombre(nombre);
			tipoPersona.setAbreviatura(abreviatura.toUpperCase());

			responseTipoPersona = tipoPersonaRepository.save(tipoPersona);
			logger.debug("Tipo persona actualizado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoPersona);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		TipoPersona tmpTipoPersona = tipoPersonaRepository.findById(id).orElse(null);
		logger.debug("Tipo persona: {}", tmpTipoPersona);
		if (null != tmpTipoPersona) {
			tipoPersonaRepository.deleteById(id);
			logger.debug("Tipo persona eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Tipo persona " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

}
