package pe.com.fabrica.aldesa.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Choferes;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ChoferRepository;
import pe.com.fabrica.aldesa.repository.PersonaRepository;

@Service
public class ChoferService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ChoferRepository choferRepository;

	@Autowired
	private PersonaRepository personaRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Choferes> choferPage = choferRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, choferPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), choferPage.getContent(),
				Math.toIntExact(choferPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Choferes tmpChofer = choferRepository.findById(id).orElse(null);
		logger.debug("Chofer: {}", tmpChofer);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpChofer);
	}

	public ApiResponse save(String request) throws ApiException {
		Choferes responseChofer;

		JsonNode root;
		String numeroLicencia = null;
		Long idPersona = null;

		try {
			root = new ObjectMapper().readTree(request);

			numeroLicencia = root.path("numeroLicencia").asText();
			logger.debug("numeroLicencia: {}", numeroLicencia);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(numeroLicencia)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Persona persona = null;
		logger.debug("Buscando Persona {}", idPersona);
		persona = personaRepository.findById(idPersona)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Persona {} encontrado", idPersona);

		try {
			Choferes chofer = new Choferes();
			chofer.setNumeroLicencia(numeroLicencia);
			chofer.setPersona(persona);

			responseChofer = choferRepository.save(chofer);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseChofer);
	}

	public ApiResponse update(String request) throws ApiException {
		Choferes responseChofer;

		JsonNode root;
		String numeroLicencia = null;
		Long idPersona = null;
		Long idChofer = null;

		try {
			root = new ObjectMapper().readTree(request);

			numeroLicencia = root.path("numeroLicencia").asText();
			logger.debug("numeroLicencia: {}", numeroLicencia);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

			idChofer = root.path("idChofer").asLong();
			logger.debug("idChofer: {}", idChofer);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(numeroLicencia)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Persona persona = null;
		logger.debug("Buscando Persona {}", idPersona);
		persona = personaRepository.findById(idPersona)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Persona {} encontrado", idPersona);

		try {
			Choferes chofer = new Choferes();
			chofer.setidChofer(idChofer);
			chofer.setNumeroLicencia(numeroLicencia);
			chofer.setPersona(persona);

			responseChofer = choferRepository.save(chofer);
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseChofer);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Choferes tmpChofer = choferRepository.findById(id).orElse(null);
		logger.debug("Chofer: {}", tmpChofer);
		Long numtp = choferRepository.findEliminarTicketPesajeById(id);

		if (null != tmpChofer && numtp==0) {
			choferRepository.deleteById(id);
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Chofer " + tmpChofer.getPersona().getNombres() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Chofer" + tmpChofer.getPersona().getNombres()+ "esta siendo usado en ticket pesaje.");
	}

	public ApiResponse findByDocumentoPersona(String documento) {
		Choferes choferes = choferRepository.findByDocumento(documento);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), choferes);
	}

	public ApiResponse findByNombreCompletoPersona(String filtro) {
		List<Choferes> choferes = choferRepository.findByNombreCompleto(filtro);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), choferes, choferes.size());
	}
}
