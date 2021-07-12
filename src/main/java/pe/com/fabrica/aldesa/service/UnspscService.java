package pe.com.fabrica.aldesa.service;

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
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Clase;
import pe.com.fabrica.aldesa.beans.Unspsc;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ClaseRepository;
import pe.com.fabrica.aldesa.repository.UnspscRepository;

@Service
public class UnspscService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UnspscRepository unspscRepository;

	@Autowired
	private ClaseRepository claseRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Unspsc> unspscPage = unspscRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, unspscPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), unspscPage.getContent(),
				Math.toIntExact(unspscPage.getTotalElements()));
	}

	public ApiResponse findAllClases(Integer pageNumber, Integer idClase) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Unspsc> unspscPage = unspscRepository.findByClase(pageable, idClase);
		logger.debug("Página {} de: {}", pageNumber, unspscPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), unspscPage.getContent(),
				Math.toIntExact(unspscPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Unspsc tmpUnspsc = unspscRepository.findById(id).orElse(null);
		logger.debug("Unspsc: {}", tmpUnspsc);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpUnspsc);
	}

	public ApiResponse save(String request) throws ApiException {
		Unspsc responseUnspsc;

		JsonNode root;
		String descripcion = null;
		String codigo = null;
		Integer idClase = null;
		try {
			root = new ObjectMapper().readTree(request);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			idClase = root.path("idClase").asInt();
			logger.debug("idClase: {}", idClase);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idClase || idClase == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Clase clase = new Clase();
		clase = claseRepository.findById(idClase)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Clase {} encontrado", idClase);

		try {
			Unspsc Unspsc = new Unspsc();

			Unspsc.setCodigo(codigo);
			Unspsc.setDescripcion(descripcion);
			Unspsc.setClase(clase);

			responseUnspsc = unspscRepository.save(Unspsc);
			logger.debug("Unspsc guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUnspsc);
	}

	public ApiResponse update(String request) throws ApiException {
		Unspsc responseUnspsc;

		JsonNode root;
		Long idCodigoSunat = null;
		String descripcion = null;
		String codigo = null;
		Integer idClase = null;
		try {
			root = new ObjectMapper().readTree(request);

			idCodigoSunat = root.path("idCodigoSunat").asLong();
			logger.debug("idCodigoSunat: {}", idCodigoSunat);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			idClase = root.path("idClase").asInt();
			logger.debug("idClase: {}", idClase);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idClase || idClase == 0 || null == idClase || idCodigoSunat == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existUnspsc = unspscRepository.existsById(idCodigoSunat);
		logger.debug("Existe Unspsc? {} {}", idCodigoSunat, existUnspsc);
		if (!existUnspsc) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Clase clase = new Clase();
		clase = claseRepository.findById(idClase)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Clase {} encontrado", idClase);

		try {
			Unspsc Unspsc = new Unspsc();

			Unspsc.setCodigo(codigo);
			Unspsc.setDescripcion(descripcion);
			Unspsc.setClase(clase);
			Unspsc.setIdCodigoSunat(idCodigoSunat);

			responseUnspsc = unspscRepository.save(Unspsc);
			logger.debug("Unspsc guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUnspsc);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Unspsc tmpUnspsc = unspscRepository.findById(id).orElse(null);
		logger.debug("Unspsc: {}", tmpUnspsc);
		if (null != tmpUnspsc) {
			unspscRepository.deleteById(id);
			logger.debug("Unspsc eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Unspsc " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByCodigo(String codigo) {
		Unspsc unspsc = unspscRepository.findByCodigo(codigo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), unspsc);
	}

	public ApiResponse findByDescripcion(String descripcion) {
		List<Unspsc> unspsc = unspscRepository.findByDescripcion(descripcion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), unspsc, unspsc.size());
	}

	public ApiResponse findByCodigoClase(String codigo, Integer idClase) {
		Unspsc unspsc = unspscRepository.findByCodigoClase(codigo, idClase);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), unspsc);
	}

	public ApiResponse findByDescripcionClase(String descripcion, Integer idClase) {
		List<Unspsc> unspsc = unspscRepository.findByDescripcionClase(descripcion, idClase);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), unspsc, unspsc.size());
	}
}
