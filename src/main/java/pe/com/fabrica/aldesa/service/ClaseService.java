package pe.com.fabrica.aldesa.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Clase;
import pe.com.fabrica.aldesa.beans.Familia;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ClaseRepository;
import pe.com.fabrica.aldesa.repository.FamiliaRepository;

@Service
public class ClaseService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ClaseRepository ClaseRepository;

	@Autowired
	private FamiliaRepository FamiliaRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public ClaseService(ClaseRepository ClaseRepository) {
		this.ClaseRepository = ClaseRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Clase> ClasePage = ClaseRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, ClasePage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), ClasePage.getContent(),
				Math.toIntExact(ClasePage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Clase tmpClase = ClaseRepository.findById(id).orElse(null);
		logger.debug("Clase: {}", tmpClase);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpClase);
	}

	public ApiResponse save(String request) throws ApiException {
		Clase responseClase;

		JsonNode root;
		String descripcion = null;
		String codigo = null;
		Integer idFamilia = null;

		try {
			root = new ObjectMapper().readTree(request);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			idFamilia = root.path("idFamilia").asInt();
			logger.debug("idFamilia: {}", idFamilia);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(descripcion) || null == codigo) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Familia familia = new Familia();
		familia = FamiliaRepository.findById(idFamilia)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Familia {} encontrado", idFamilia);

		try {
			Clase Clase = new Clase();
			Clase.setDescripcion(descripcion);
			Clase.setCodigo(codigo);
			Clase.setFamilia(familia);

			responseClase = ClaseRepository.save(Clase);
			logger.debug("Clase guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseClase);
	}

	public ApiResponse update(String request) throws ApiException {
		Clase responseClase;

		JsonNode root;
		Integer idClase;
		String descripcion = null;
		String codigo = null;
		Integer idFamilia = null;

		try {
			root = new ObjectMapper().readTree(request);

			idClase = root.path("idClase").asInt();
			logger.debug("idClase: {}", idClase);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			idFamilia = root.path("idFamilia").asInt();
			logger.debug("idFamilia: {}", idFamilia);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(descripcion) || null == codigo) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Familia familia = new Familia();
		familia = FamiliaRepository.findById(idFamilia)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Familia {} encontrado", idFamilia);

		try {
			Clase Clase = new Clase();
			Clase.setDescripcion(descripcion);
			Clase.setCodigo(codigo);
			Clase.setIdClase(idClase);
			Clase.setFamilia(familia);

			responseClase = ClaseRepository.save(Clase);
			logger.debug("Clase actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseClase);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Clase tmpClase = ClaseRepository.findById(id).orElse(null);
		logger.debug("Clase: {}", tmpClase);
		if (null != tmpClase) {
			ClaseRepository.deleteById(id);
			logger.debug("Clase eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Clase " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByCodigo(String codigo) {
		Clase Clase = ClaseRepository.findByCodigo(codigo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Clase);
	}

	public ApiResponse findByDescripcion(String descripcion) {
		List<Clase> Clase = ClaseRepository.findByDescripcion(descripcion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Clase, Clase.size());
	}

	public ApiResponse findByCodigoFamilia(String codigo, Integer idFamilia) {
		Clase clase = ClaseRepository.findByCodigoFamilia(codigo, idFamilia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clase);
	}

	public ApiResponse findByDescripcionFamilia(String descripcion, Integer idFamilia) {
		List<Clase> Familia = ClaseRepository.findByDescripcionFamilia(descripcion, idFamilia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Familia, Familia.size());
	}

	public ApiResponse findAllFamilia(Integer pageNumber, Integer idFamilia) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Clase> clasePage = ClaseRepository.findAllFamilia(pageable, idFamilia);
		logger.debug("Página {} de: {}", pageNumber, clasePage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clasePage.getContent(),
				Math.toIntExact(clasePage.getTotalElements()));
	}
}
