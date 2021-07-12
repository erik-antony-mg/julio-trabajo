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
import pe.com.fabrica.aldesa.beans.DepositoTemporal;
import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DepositoTemporalRepository;
import pe.com.fabrica.aldesa.repository.EmpresaRepository;

@Service
public class DepositoTemporalService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DepositoTemporalRepository depositoTemporalRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final int PAGE_LIMIT = 10;
	public ApiResponse findAllSP() {
		List<DepositoTemporal> depositos = depositoTemporalRepository.findAllSP();
		int total = depositos.size();
		logger.debug("Total DepositoTemporal: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), depositos, total);
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<DepositoTemporal> depositosPage = depositoTemporalRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, depositosPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), depositosPage.getContent(),
				Math.toIntExact(depositosPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		DepositoTemporal tmpDeposito = depositoTemporalRepository.findById(id).orElse(null);
		logger.debug("DepositoTemporal: {}", tmpDeposito);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpDeposito);
	}

	public ApiResponse save(String request) throws ApiException {
		DepositoTemporal responseDepositoTemporal;

		JsonNode root;
		Long idEmpresa = null;
		String	codigoAduana = null;
		try {
			root = new ObjectMapper().readTree(request);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

			codigoAduana = root.path("codigoAduana").asText();
			logger.debug("codigoAduana: {}", codigoAduana);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(codigoAduana) || null == idEmpresa || idEmpresa == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Empresa empresa = empresaRepository.findById(idEmpresa)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			DepositoTemporal deposito = new DepositoTemporal();
			deposito.setEmpresa(empresa);
			deposito.setCodigoAduana(codigoAduana);

			responseDepositoTemporal = depositoTemporalRepository.save(deposito);
			logger.debug("Deposito guradado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDepositoTemporal);
	}

	public ApiResponse update(String request) throws ApiException {
		DepositoTemporal responseDepositoTemporal;

		JsonNode root;
		Integer id = null;
		Long idEmpresa = null;
		String	codigoAduana = null;
		try {
			root = new ObjectMapper().readTree(request);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			codigoAduana = root.path("codigoAduana").asText();
			logger.debug("codigoAduana: {}", codigoAduana);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(codigoAduana) || null == idEmpresa || idEmpresa == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsDeposito = depositoTemporalRepository.existsById(id);
		logger.debug("Existe depósito? {}", existsDeposito);
		if (!existsDeposito) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Empresa empresa = empresaRepository.findById(idEmpresa)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			DepositoTemporal deposito = new DepositoTemporal();
			deposito.setIdDeposito(id);
			deposito.setEmpresa(empresa);
			deposito.setCodigoAduana(codigoAduana);

			responseDepositoTemporal = depositoTemporalRepository.save(deposito);
			logger.debug("Deposito actualizado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDepositoTemporal);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		DepositoTemporal tmpDepositoTemporal = depositoTemporalRepository.findById(id).orElse(null);
		logger.debug("DepositoTemporal: {}", tmpDepositoTemporal);
		Integer numt = depositoTemporalRepository.findEliminarTarjetaById(id);
		if (null != tmpDepositoTemporal && numt == 0) {
			depositoTemporalRepository.deleteById(id);
			logger.debug("DepositoTemporal eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Deposito Temporal "
					+ tmpDepositoTemporal.getEmpresa().getRazonSocial() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), "Deposito Temporal " + tmpDepositoTemporal.getEmpresa().getRazonSocial() +
				" esta siendo usado en tarjeta.");
	}

	public ApiResponse findByRazonSocial(String razonSocial) {
		List<DepositoTemporal> depositosTemporales = depositoTemporalRepository.findByRazonSocial(razonSocial);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), depositosTemporales,
				depositosTemporales.size());
	}

	
	public ApiResponse findByCodigoAduana(String codigoAduana) throws ApiException {
		List<DepositoTemporal> depositosTemporales = depositoTemporalRepository.findByCodigoAduana(codigoAduana);
		if (depositosTemporales.size() > 1) {
			throw new ApiException(ApiError.MULTIPLES_SIMILAR_ELEMENTS.getCode(),
					ApiError.MULTIPLES_SIMILAR_ELEMENTS.getMessage());
		}
		logger.debug("depositosTemporales: {}", depositosTemporales.get(0));
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), depositosTemporales.get(0));
	}

}
