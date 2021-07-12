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
import pe.com.fabrica.aldesa.beans.Segmento;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.SegmentoRepository;

@Service
public class SegmentoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SegmentoRepository SegmentoRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public SegmentoService(SegmentoRepository SegmentoRepository) {
		this.SegmentoRepository = SegmentoRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Segmento> segmentoPage = SegmentoRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, segmentoPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), segmentoPage.getContent(),
				Math.toIntExact(segmentoPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Segmento tmpSegmento = SegmentoRepository.findById(id).orElse(null);
		logger.debug("Segmento: {}", tmpSegmento);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpSegmento);
	}

	public ApiResponse save(String request) throws ApiException {
		Segmento responseSegmento;

		JsonNode root;
		String descripcion = null;
		String codigo = null;
		try {
			root = new ObjectMapper().readTree(request);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(descripcion) || null == codigo) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Segmento Segmento = new Segmento();
			Segmento.setDescripcion(descripcion);
			Segmento.setCodigo(codigo);

			responseSegmento = SegmentoRepository.save(Segmento);
			logger.debug("Segmento guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseSegmento);
	}

	public ApiResponse update(String request) throws ApiException {
		Segmento responseSegmento;

		JsonNode root;
		Integer idSegmento;
		String descripcion = null;
		String codigo = null;
		try {
			root = new ObjectMapper().readTree(request);

			idSegmento = root.path("idSegmento").asInt();
			logger.debug("idSegmento: {}", idSegmento);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(descripcion) || null == codigo) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Segmento Segmento = new Segmento();
			Segmento.setDescripcion(descripcion);
			Segmento.setCodigo(codigo);

			responseSegmento = SegmentoRepository.save(Segmento);
			logger.debug("Segmento actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseSegmento);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Segmento tmpSegmento = SegmentoRepository.findById(id).orElse(null);
		logger.debug("Segmento: {}", tmpSegmento);
		if (null != tmpSegmento) {
			SegmentoRepository.deleteById(id);
			logger.debug("Segmento eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Segmento " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByCodigo(String codigo) {
		Segmento segmento = SegmentoRepository.findByCodigo(codigo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), segmento);
	}

	public ApiResponse findByDescripcion(String descripcion) {
		List<Segmento> segmento = SegmentoRepository.findByDescripcion(descripcion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), segmento,segmento.size());
	}

}
