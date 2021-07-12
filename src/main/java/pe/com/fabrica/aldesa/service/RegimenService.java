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
import pe.com.fabrica.aldesa.beans.Regimen;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.RegimenRepository;

@Service
public class RegimenService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RegimenRepository regimenRepository;
	
	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAllSP() {
		List<Regimen> regimen = regimenRepository.findAllSP();
		int total = regimen.size();
		logger.debug("Total regimen: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), regimen, total);
	}

	public ApiResponse findAllPag(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Regimen> regimenes = regimenRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, regimenes.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), regimenes.getContent(),
				Math.toIntExact(regimenes.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Regimen regimen = regimenRepository.findById(id).orElse(null);
		logger.debug("Regimen: {}", regimen);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), regimen);
	}

	public ApiResponse findByCodigoAduana(Integer codigoAduana) throws ApiException {
		List<Regimen> regimenes = regimenRepository.findByCodigoAduana(codigoAduana);
		if (regimenes.size() > 1) {
			throw new ApiException(ApiError.MULTIPLES_SIMILAR_ELEMENTS.getCode(), ApiError.MULTIPLES_SIMILAR_ELEMENTS.getMessage());
		}
		logger.debug("Regimenes: {}", regimenes.get(0));
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), regimenes.get(0));
	}

	public ApiResponse save(String request) throws ApiException {
		Regimen responseRegimen;

		JsonNode root;
		String	nombre = null;
		Integer	codigoAduana = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			codigoAduana = root.path("codigoAduana").asInt();
			logger.debug("codigoAduana: {}", codigoAduana);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == codigoAduana || codigoAduana == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Regimen regimen = new Regimen();
			regimen.setCodigoAduana(codigoAduana);
			regimen.setNombre(nombre);

			responseRegimen = regimenRepository.save(regimen);
			logger.debug("Regimen guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseRegimen);
	}

	public ApiResponse update(String request) throws ApiException {
		Regimen responseRegimen;

		JsonNode root;
		Integer id;
		String	nombre = null;
		Integer	codigoAduana = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", id);

			nombre = root.path("Nombre del Régimen").asText();
			logger.debug("Nombre del Régimen: {}", nombre);

			codigoAduana = root.path("codigoAduana").asInt();
			logger.debug("codigoAduana: {}", codigoAduana);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == codigoAduana || codigoAduana == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Regimen regimen = new Regimen();
			regimen.setIdRegimen(id);
			regimen.setCodigoAduana(codigoAduana);
			regimen.setNombre(nombre);

			responseRegimen = regimenRepository.save(regimen);
			logger.debug("Regimen actualizado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseRegimen);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Regimen tmpRegimen = regimenRepository.findById(id).orElse(null);
		logger.debug("Regimen: {}", tmpRegimen);
		Integer numt = regimenRepository.findEliminarTarjetaById(id);
		Integer numd = regimenRepository.findEliminarDamById(id);
		String dondeSeUsa = "regimen";
		if (null != tmpRegimen && numt == 0 && numd==0) {
			logger.info("eliminando");
			regimenRepository.deleteById(id);
			logger.debug("Regimen eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Regimen " + tmpRegimen.getNombre() + " eliminado");
		}

		if(numt >=1 && numd >= 1){
			dondeSeUsa = "tarjeta";
			dondeSeUsa = dondeSeUsa + ", " + "dam";
		}else if(numt >= 1){
			dondeSeUsa = "tarjeta";
		}else if(numd >= 1){
			dondeSeUsa = "dam";
		}

		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), "Regimen " + tmpRegimen.getNombre() + " esta siendo usado en " + dondeSeUsa + ".");
	}

	public ApiResponse findByNombre(String nombre) {
		List<Regimen> regimen = regimenRepository.findByNombre(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), regimen,
				regimen.size());
	}
}
