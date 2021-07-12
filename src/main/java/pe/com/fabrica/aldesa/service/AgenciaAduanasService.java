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
import pe.com.fabrica.aldesa.beans.AgenciasAduanas;
import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.AgenciaAduanasRepository;
import pe.com.fabrica.aldesa.repository.EmpresaRepository;

@Service
public class AgenciaAduanasService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AgenciaAduanasRepository agenciaAduanasRepository;

	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll() {
		List<AgenciasAduanas> agencias = agenciaAduanasRepository.findAll();
		int total = agencias.size();
		logger.debug("Total AgenciaAduanas: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agencias, total);
	}

	public ApiResponse findAllPag(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<AgenciasAduanas> agenciaPage = agenciaAduanasRepository.findAllPag(pageable);
		logger.debug("Página {} de: {}", pageNumber, agenciaPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agenciaPage.getContent(),
				Math.toIntExact(agenciaPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		AgenciasAduanas agencia = agenciaAduanasRepository.findById(id).orElse(null);
		logger.debug("Agencia: {}", agencia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agencia);
	}

	public ApiResponse findByCodigoAduana(Integer codigoAduana) throws ApiException {
		List<AgenciasAduanas> agencias = agenciaAduanasRepository.findByCodigoAduana(codigoAduana);
		if (agencias.size() > 1) {
			throw new ApiException(ApiError.MULTIPLES_SIMILAR_ELEMENTS.getCode(),
					ApiError.MULTIPLES_SIMILAR_ELEMENTS.getMessage());
		}
		logger.debug("Agencias: {}", agencias.get(0));
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agencias.get(0));
	}

	public ApiResponse save(String request) throws ApiException {
		AgenciasAduanas responseAgencia;

		JsonNode root;
		Integer codigoAduana = null;
		Long idEmpresa = null;
		try {
			root = new ObjectMapper().readTree(request);

			codigoAduana = root.path("codigoAduana").asInt();
			logger.debug("codigoAduana: {}", codigoAduana);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == codigoAduana || idEmpresa == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Empresa empresa = null;
		empresa = empresaRepository.findById(idEmpresa)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Empresa {} encontrado", idEmpresa);

		try {
			AgenciasAduanas agencia = new AgenciasAduanas();
			agencia.setCodigoAduana(codigoAduana);
			agencia.setEmpresa(empresa);
			responseAgencia = agenciaAduanasRepository.save(agencia);
			logger.debug("Agencia guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseAgencia);
	}

	public ApiResponse update(String request) throws ApiException {
		AgenciasAduanas responseAgencia;

		JsonNode root;
		Long idAgenciaAduanas = null;
		Integer codigoAduana = null;
		Long idEmpresa = null;
		try {
			root = new ObjectMapper().readTree(request);

			idAgenciaAduanas = root.path("idAgenciaAduanas").asLong();
			logger.debug("idAgenciaAduanas: {}", idAgenciaAduanas);

			codigoAduana = root.path("codigoAduana").asInt();
			logger.debug("codigoAduana: {}", codigoAduana);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == codigoAduana || codigoAduana == 0 || null == idAgenciaAduanas || idAgenciaAduanas == 0 || null == idEmpresa
				|| idEmpresa == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsAgencia = agenciaAduanasRepository.existsById(idAgenciaAduanas);
		logger.debug("Existe empresa {}? {}", idAgenciaAduanas, existsAgencia);
		if (!existsAgencia) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Empresa empresa = null;
		empresa = empresaRepository.findById(idEmpresa)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Empresa {} encontrado", idEmpresa);

		try {
			AgenciasAduanas agencia = new AgenciasAduanas();

			agencia.setEmpresa(empresa);
			agencia.setCodigoAduana(codigoAduana);
			agencia.setIdAgencia(idAgenciaAduanas);

			responseAgencia = agenciaAduanasRepository.save(agencia);
			logger.debug("Agencia actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseAgencia);
	}

	public ApiResponse delete(Long id) throws ApiException {
		AgenciasAduanas tmpAgencia = agenciaAduanasRepository.findById(id).orElse(null);
		logger.debug("Agencia: {}", tmpAgencia);
		Long numt = agenciaAduanasRepository.findEliminarTarjetaById(id);
		Long numd = agenciaAduanasRepository.findEliminarDamById(id);
		String siendoUsado = "agencia aduanas";
		if (null != tmpAgencia && numt==0 && numd==0) {
			agenciaAduanasRepository.deleteById(id);
			logger.debug("Agencia eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Agencia " + id + " eliminada");
		}

		if(numt>=1 && numd>=1){
			siendoUsado = "tarjeta";
			siendoUsado = siendoUsado + ", " + "dam";
		}else if(numd >= 1){
			siendoUsado = "dam";
		}else if(numd >= 1){
			siendoUsado = "tarjeta";
		}

		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Agencia aduana " + tmpAgencia.getEmpresa().getRazonSocial() + "esta siendo usado en " + siendoUsado + ".");
	}

	public ApiResponse findByRazonSocial(String razonSocial) {
		List<AgenciasAduanas> agenciaAduanas = agenciaAduanasRepository.findByRazonSocial(razonSocial);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agenciaAduanas,
				agenciaAduanas.size());
	}
}
