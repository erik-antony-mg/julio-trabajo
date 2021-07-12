package pe.com.fabrica.aldesa.service;

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
import pe.com.fabrica.aldesa.beans.TipoMercancia;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.TipoMercanciaRepository;

@Service
public class TipoMercanciaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoMercanciaRepository tipoMercanciaRepository;
	private static final int PAGE_LIMIT = 10;

	@Autowired
	public TipoMercanciaService(TipoMercanciaRepository aduanaRepository) {
		this.tipoMercanciaRepository = aduanaRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<TipoMercancia> tipomercanciasPage = tipoMercanciaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, tipomercanciasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
				tipomercanciasPage.getContent(), Math.toIntExact(tipomercanciasPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		TipoMercancia tmpTipoMercancia = tipoMercanciaRepository.findById(id).orElse(null);
		logger.debug("TipoMercancia: {}", tmpTipoMercancia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTipoMercancia);
	}

	public ApiResponse save(String request) throws ApiException {
		TipoMercancia responseTipoMercancia;

		JsonNode root;
		String nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			TipoMercancia tipoMercancia = new TipoMercancia();
			tipoMercancia.setNombre(nombre);

			responseTipoMercancia = tipoMercanciaRepository.save(tipoMercancia);
			logger.debug("TipoMercancia guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoMercancia);
	}

	public ApiResponse update(String request) throws ApiException {
		TipoMercancia responseTipoMercancia;

		JsonNode root;
		Integer id = null;
		String nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			TipoMercancia tipoMercancia = new TipoMercancia();
			tipoMercancia.setIdTipoMercancia(id);
			tipoMercancia.setNombre(nombre);

			responseTipoMercancia = tipoMercanciaRepository.save(tipoMercancia);
			logger.debug("TipoMercancia actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoMercancia);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		TipoMercancia tmpTipoMercancia = tipoMercanciaRepository.findById(id).orElse(null);
		logger.debug("TipoMercancia: {}", tmpTipoMercancia);
		Integer numm = tipoMercanciaRepository.findEliminarMercanciaById(id);
		if (null != tmpTipoMercancia && numm==0) {
			tipoMercanciaRepository.deleteById(id);
			logger.debug("TipoMercancia eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"TipoMercancia " + tmpTipoMercancia.getNombre() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Tipo mercancia " + tmpTipoMercancia.getNombre() + " esta siendo usado en mercancia.");
	}

	public ApiResponse findByNombre(String nombre) {
		List<TipoMercancia> listaServicios = tipoMercanciaRepository.findByNombre(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listaServicios,
				listaServicios.size());
	}
}
