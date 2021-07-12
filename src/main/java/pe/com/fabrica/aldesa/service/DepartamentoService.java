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
import pe.com.fabrica.aldesa.beans.Pais;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DepartamentoRepository;
import pe.com.fabrica.aldesa.repository.PaisRepository;

@Service
public class DepartamentoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DepartamentoRepository departamentoRepository;

	@Autowired
	private PaisRepository paisRepository;

	public ApiResponse findAll() {
		List<Departamento> departamentos = departamentoRepository.findAll();
		int total = departamentos.size();
		logger.debug("Total Departamentos: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), departamentos, total);
	}

	public ApiResponse findById(Integer id) {
		Departamento tmpDepartamento = departamentoRepository.findById(id).orElse(null);
		logger.debug("Departamento: {}", tmpDepartamento);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpDepartamento);
	}

	public ApiResponse save(String request) throws ApiException {
		Departamento responseDepartamento;

		JsonNode root;
		Integer	idPais = null;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			idPais = root.path("idPais").asInt();
			logger.debug("idPais: {}", idPais);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idPais || idPais == 0  || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Pais pais = paisRepository.findById(idPais)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Departamento departamento = new Departamento();
			departamento.setPais(pais);
			departamento.setNombre(nombre);

			responseDepartamento = departamentoRepository.save(departamento);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDepartamento);
	}

	public ApiResponse update(String request) throws ApiException {
		Departamento responseDepartamento;

		JsonNode root;
		Integer	id = null;
		Integer	idPais = null;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			idPais = root.path("idPais").asInt();
			logger.debug("idPais: {}", idPais);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idPais || idPais == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsDepartamento = departamentoRepository.existsById(id);
		logger.debug("Existe departamento? {}", existsDepartamento);
		if (!existsDepartamento) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Pais pais = paisRepository.findById(idPais)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Departamento departamento = new Departamento();
			departamento.setIdDepartamento(id);
			departamento.setPais(pais);
			departamento.setNombre(nombre);

			responseDepartamento = departamentoRepository.save(departamento);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDepartamento);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Departamento tmpDepartamento = departamentoRepository.findById(id).orElse(null);
		logger.debug("Departamento: {}", tmpDepartamento);
		if (null != tmpDepartamento) {
			try {
				departamentoRepository.deleteById(id);
			} catch (Exception e) {
				throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
			}
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Departamento " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}
}
