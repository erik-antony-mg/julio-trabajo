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
import pe.com.fabrica.aldesa.beans.Familia;
import pe.com.fabrica.aldesa.beans.Segmento;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.FamiliaRepository;
import pe.com.fabrica.aldesa.repository.SegmentoRepository;

@Service
public class FamiliaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private FamiliaRepository FamiliaRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	private SegmentoRepository segmentoRepository;

	@Autowired
	public FamiliaService(FamiliaRepository FamiliaRepository) {
		this.FamiliaRepository = FamiliaRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Familia> FamiliaPage = FamiliaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, FamiliaPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), FamiliaPage.getContent(),
				Math.toIntExact(FamiliaPage.getTotalElements()));
	}

	public ApiResponse findAllSegmento(Integer pageNumber, Integer IdSegmento) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Familia> FamiliaPage = FamiliaRepository.findAllSegmento(pageable, IdSegmento);
		logger.debug("Página {} de: {}", pageNumber, FamiliaPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), FamiliaPage.getContent(),
				Math.toIntExact(FamiliaPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Familia tmpFamilia = FamiliaRepository.findById(id).orElse(null);
		logger.debug("Familia: {}", tmpFamilia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpFamilia);
	}

	public ApiResponse save(String request) throws ApiException {
		Familia responseFamilia;

		JsonNode root;
		String descripcion = null;
		String codigo = null;
		Integer idSegmento = null;

		try {
			root = new ObjectMapper().readTree(request);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			idSegmento = root.path("idSegmento").asInt();
			logger.debug("idSegmento: {}", idSegmento);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(descripcion) || null == codigo) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Segmento segmento = new Segmento();
		segmento = segmentoRepository.findById(idSegmento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Segmento {} encontrado", idSegmento);

		try {
			Familia Familia = new Familia();
			Familia.setDescripcion(descripcion);
			Familia.setCodigo(codigo);
			Familia.setSegemento(segmento);

			responseFamilia = FamiliaRepository.save(Familia);
			logger.debug("Familia guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseFamilia);
	}

	public ApiResponse update(String request) throws ApiException {
		Familia responseFamilia;

		JsonNode root;
		Integer idFamilia;
		String descripcion = null;
		String codigo = null;
		Integer idSegmento = null;

		try {
			root = new ObjectMapper().readTree(request);

			idFamilia = root.path("idFamilia").asInt();
			logger.debug("idFamilia: {}", idFamilia);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

			idSegmento = root.path("idSegmento").asInt();
			logger.debug("idSegmento: {}", idSegmento);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(descripcion) || null == codigo) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Segmento segmento = new Segmento();
		segmento = segmentoRepository.findById(idSegmento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Segmento {} encontrado", idSegmento);

		try {
			Familia Familia = new Familia();
			Familia.setDescripcion(descripcion);
			Familia.setCodigo(codigo);
			Familia.setSegemento(segmento);
			Familia.setIdFamilia(idFamilia);

			responseFamilia = FamiliaRepository.save(Familia);
			logger.debug("Familia actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseFamilia);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Familia tmpFamilia = FamiliaRepository.findById(id).orElse(null);
		logger.debug("Familia: {}", tmpFamilia);
		if (null != tmpFamilia) {
			FamiliaRepository.deleteById(id);
			logger.debug("Familia eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Familia " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByCodigo(String codigo) {
		Familia Familia = FamiliaRepository.findByCodigo(codigo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Familia);
	}

	public ApiResponse findByDescripcion(String descripcion) {
		List<Familia> Familia = FamiliaRepository.findByDescripcion(descripcion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Familia, Familia.size());
	}

	public ApiResponse findByCodigoSegmento(String codigo, Integer idSegmento) {
		Familia Familia = FamiliaRepository.findByCodigoSegmento(codigo, idSegmento);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Familia);
	}

	public ApiResponse findByDescripcionSegmento(String descripcion, Integer idSegmento) {
		List<Familia> Familia = FamiliaRepository.findByDescripcionSegmento(descripcion, idSegmento);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Familia, Familia.size());
	}

}
