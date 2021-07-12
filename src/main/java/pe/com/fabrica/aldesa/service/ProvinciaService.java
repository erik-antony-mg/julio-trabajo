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
import pe.com.fabrica.aldesa.beans.Departamento;
import pe.com.fabrica.aldesa.beans.Provincia;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DepartamentoRepository;
import pe.com.fabrica.aldesa.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private DepartamentoRepository departamentoRepository;

	public ApiResponse findAll() {
		List<Provincia> provincias = provinciaRepository.findAll();
		int total = provincias.size();
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), provincias, total);
	}

	public ApiResponse findByDepartamento(Integer id_departamento) {
		List<Provincia> provincias = provinciaRepository.findByDepartamento(id_departamento);
		int total = provincias.size();
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), provincias, total);
	}

	public ApiResponse findById(Integer id) throws ApiException {
		Provincia tmpProvincia = provinciaRepository.findById(id).orElse(null);
		logger.debug("Provincia: {}", tmpProvincia);
		if (null == tmpProvincia) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpProvincia);
	}
	
	public ApiResponse save(String request) throws ApiException {
		Provincia responseProvincia;

		JsonNode root;
		Integer	id = null;
		Integer	idDepartamento = null;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			idDepartamento = root.path("idDepartamento").asInt();
			logger.debug("idDepartamento: {}", idDepartamento);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idDepartamento || idDepartamento == 0  || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Departamento departamento = departamentoRepository.findById(idDepartamento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Provincia provincia = new Provincia();
			provincia.setDepartamento(departamento);
			provincia.setNombre(nombre);

			responseProvincia = provinciaRepository.save(provincia);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseProvincia);
	}

	public ApiResponse update(String request) throws ApiException {
		Provincia responseProvincia;

		JsonNode root;
		Integer	id = null;
		Integer	idDepartamento = null;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			idDepartamento = root.path("idDepartamento").asInt();
			logger.debug("idDepartamento: {}", idDepartamento);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idDepartamento || idDepartamento == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsidDepartamento = provinciaRepository.existsById(idDepartamento);
		logger.debug("Existe Departamento? {}", existsidDepartamento);
		if (!existsidDepartamento) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Departamento departamento = departamentoRepository.findById(idDepartamento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Provincia provincia = new Provincia();
			provincia.setIdProvincia(id);
			provincia.setDepartamento(departamento);
			provincia.setNombre(nombre);

			responseProvincia = provinciaRepository.save(provincia);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseProvincia);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Provincia tmpProvincia = provinciaRepository.findById(id).orElse(null);
		logger.debug("Provincia: {}", tmpProvincia);
		if (null != tmpProvincia) {
			provinciaRepository.deleteById(id);
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Provincia " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}
}
