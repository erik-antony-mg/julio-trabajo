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
import pe.com.fabrica.aldesa.beans.Area;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.AreaRepository;

@Service
public class AreaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AreaRepository areaRepository;

	@Autowired
	public AreaService(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAllSP() {
		List<Area> areas = areaRepository.findAllSP();
		int total = areas.size();
		logger.debug("Total áreas: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), areas, total);
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Area> areasPage = areaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, areasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), areasPage.getContent(),
				Math.toIntExact(areasPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Area tmpArea = areaRepository.findById(id).orElse(null);
		logger.debug("Area: {}", tmpArea);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpArea);
	}

	public ApiResponse save(String request) throws ApiException {
		Area responseArea;

		JsonNode root;
		String nombre = null;
		String abreviatura = null;
		String activo = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			activo = root.path("activo").asText();
			logger.debug("activo: {}", activo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || StringUtils.isBlank(activo)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Area area = new Area();
			area.setNombre(nombre);
			area.setAbreviatura(abreviatura.toUpperCase());
			area.setActivo(activo);

			responseArea = areaRepository.save(area);
			logger.debug("Area guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseArea);
	}

	public ApiResponse update(String request) throws ApiException {
		Area responseArea;

		JsonNode root;
		Integer id = null;
		String nombre = null;
		String abreviatura = null;
		String activo = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			activo = root.path("activo").asText();
			logger.debug("activo: {}", activo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(activo)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsArea = areaRepository.existsById(id);
		logger.debug("Existe Area? {}", existsArea);
		if (!existsArea) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		try {
			Area area = new Area();
			area.setIdArea(id);
			area.setNombre(nombre);
			area.setAbreviatura(abreviatura.toUpperCase());
			area.setActivo(activo);

			responseArea = areaRepository.save(area);
			logger.debug("Area actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseArea);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Area tmpArea = areaRepository.findById(id).orElse(null);
		logger.debug("Area: {}", tmpArea);
		Integer numr = areaRepository.findEliminarUbicacionById(id);
		if (null != tmpArea && numr == 0) {
			areaRepository.deleteById(id);
			logger.debug("Area eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Area " + tmpArea.getNombre() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), "Area " + tmpArea.getNombre() + " esta siendo usado en ubicacion.");
	}

	public ApiResponse findByNombre(String nombre) {
		List<Area> areas = areaRepository.findByNombre(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), areas, areas.size());
	}

	public ApiResponse findByNombrePag(String nombre, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Area> areasPage = areaRepository.findByNombrePag(nombre, pageable);
		logger.debug("Página {} de: {}", pageNumber, areasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), areasPage.getContent(),
				Math.toIntExact(areasPage.getTotalElements()));
	}

	public ApiResponse findByAbreviatura(String abreviatura) {
		List<Area> areas = areaRepository.findByAbreviatura(abreviatura);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), areas, areas.size());
	}

	public ApiResponse findByAbreviaturaPag(String abreviatura, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Area> areasPage = areaRepository.findByAbreviaturaPag(abreviatura, pageable);
		logger.debug("Página {} de: {}", pageNumber, areasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), areasPage.getContent(),
				Math.toIntExact(areasPage.getTotalElements()));
	}
}
