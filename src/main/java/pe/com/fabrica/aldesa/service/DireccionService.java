package pe.com.fabrica.aldesa.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Distrito;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DireccionRepository;
import pe.com.fabrica.aldesa.repository.DistritoRepository;

@Service
public class DireccionService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DireccionRepository DireccionRepository;

	@Autowired
	private DistritoRepository distritoRepository;

	public ApiResponse save(String request) throws ApiException {
		Direccion responseDireccion;

		JsonNode root;
		Integer idDistrito = null;
		String descripcion = null;
		try {
			root = new ObjectMapper().readTree(request);

			idDistrito = root.path("idDistrito").asInt();
			logger.debug("idDistrito: {}", idDistrito);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idDistrito || idDistrito == 0 || StringUtils.isBlank(descripcion)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Distrito distrito = distritoRepository.findById(idDistrito)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {

			Direccion direccion = new Direccion();
			direccion.setDescripcion(descripcion);
			direccion.setDistrito(distrito);

			responseDireccion = DireccionRepository.save(direccion);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDireccion);
	}

	public ApiResponse update(String request) throws ApiException {

		Direccion responseDireccion;

		JsonNode root;
		Integer id = null;
		Integer idDistrito = null;
		String descripcion = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);
			
			idDistrito = root.path("idDistrito").asInt();
			logger.debug("idDistrito: {}", idDistrito);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idDistrito || idDistrito == 0 || StringUtils.isBlank(descripcion)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsDistrito = distritoRepository.existsById(id);
		logger.debug("Existe distrito? {}", existsDistrito);
		if (!existsDistrito) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Distrito distrito = distritoRepository.findById(idDistrito)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Direccion direccion = new Direccion();
			direccion.setIdDireccion(id);
			direccion.setDescripcion(descripcion);
			direccion.setDistrito(distrito);


			responseDireccion = DireccionRepository.save(direccion);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDireccion);
	}

}
