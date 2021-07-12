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
import pe.com.fabrica.aldesa.beans.Aduana;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.AduanaRepository;

@Service
public class AduanaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AduanaRepository aduanaRepository;
	
	private static final int PAGE_LIMIT = 10;

	@Autowired
	public AduanaService(AduanaRepository aduanaRepository) {
		this.aduanaRepository = aduanaRepository;
	}

	public ApiResponse findAllSP() {
		List<Aduana> aduana = aduanaRepository.findAllSP();
		int total = aduana.size();
		logger.debug("Total aduanas: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), aduana, total);
	}

	public ApiResponse findAllPag(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Aduana> aduanaPage = aduanaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, aduanaPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), aduanaPage.getContent(),
				Math.toIntExact(aduanaPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Aduana tmpAduana = aduanaRepository.findById(id).orElse(null);
		logger.debug("Aduana: {}", tmpAduana);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpAduana);
	}

	public ApiResponse save(String request) throws ApiException {
		Aduana responseAduana;

		JsonNode root;
		String nombre = null;
		Integer codigoAduana = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			codigoAduana = root.path("codigoAduana").asInt();
			logger.debug("codigoAduana: {}", codigoAduana);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || null == codigoAduana || codigoAduana == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Aduana aduana = new Aduana();
			aduana.setNombre(nombre);
			aduana.setCodigoAduana(codigoAduana);

			responseAduana = aduanaRepository.save(aduana);
			logger.debug("Aduana guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseAduana);
	}

	public ApiResponse update(String request) throws ApiException {
		Aduana responseAduana;

		JsonNode root;
		Integer id;
		String nombre = null;
		Integer codigoAduana = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			codigoAduana = root.path("codigoAduana").asInt();
			logger.debug("codigoAduana: {}", codigoAduana);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || null == codigoAduana || codigoAduana == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Aduana aduana = new Aduana();
			aduana.setIdAduana(id);
			aduana.setNombre(nombre);
			aduana.setCodigoAduana(codigoAduana);

			responseAduana = aduanaRepository.save(aduana);
			logger.debug("Aduana actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseAduana);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Aduana tmpAduana = aduanaRepository.findById(id).orElse(null);
		logger.debug("Aduana: {}", tmpAduana);
		Integer numd = aduanaRepository.findEliminarDamById(id);

		if (null != tmpAduana && numd==0) {
			aduanaRepository.deleteById(id);
			logger.debug("Aduana eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Aduana " + tmpAduana.getNombre() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), "Aduana " + tmpAduana.getNombre() + " esta siendo usado en dam.");
	}

	public ApiResponse findByNombre(String nombre) {
		List<Aduana> agenciaAduanas = aduanaRepository.findByNombre(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agenciaAduanas,
				agenciaAduanas.size());
	}
}
