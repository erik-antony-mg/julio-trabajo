package pe.com.fabrica.aldesa.service;

import java.util.List;
import java.util.Optional;

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
import pe.com.fabrica.aldesa.beans.Servicio;
import pe.com.fabrica.aldesa.beans.Subservicio;
import pe.com.fabrica.aldesa.beans.SubservicioId;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ServicioRepository;
import pe.com.fabrica.aldesa.repository.SubservicioRepository;

@Service
public class SubservicioService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubservicioRepository subservicioRepository;

	@Autowired
	private ServicioRepository servicioRepository;

	public ApiResponse findAll() {
		List<Subservicio> subservicios = subservicioRepository.findAll();
		int total = subservicios.size();
		logger.debug("Total Subservicios: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), subservicios, total);
	}

	public ApiResponse findById(Integer idServicio, Integer idSubservicio) {
		SubservicioId subservicioId = new SubservicioId(idServicio, idSubservicio);
		Optional<Subservicio> optSubservicio = subservicioRepository.findById(subservicioId);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optSubservicio.isPresent()? optSubservicio.get(): null);
	}

	public ApiResponse findByIdServicio(Integer idServicio) throws ApiException {
		Optional<Servicio> optServicio = servicioRepository.findById(idServicio);
		if (!optServicio.isPresent()) {
			throw new ApiException(ApiError.SERVICIO_NOT_FOUND.getCode(), ApiError.SERVICIO_NOT_FOUND.getMessage());
		}
		List<Subservicio> subservicios = subservicioRepository.searchSubservicioByServicio(idServicio);
		int total = subservicios.size();
		logger.debug("Total Subservicios: {}", subservicios.size());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), subservicios, total);
	}

	public ApiResponse save(String request) throws ApiException {
		Subservicio responseSubservicio;

		JsonNode root;
		Integer idServicio = null;
		Integer idSubservicio = null;
		String	nombre = null;
		Double precioMN = null;
		Double precioME = null;
		try {
			root = new ObjectMapper().readTree(request);

			idServicio = root.path("idServicio").asInt();
			logger.debug("idServicio: {}", idServicio);

			idSubservicio = root.path("idSubservicio").asInt();
			logger.debug("idSubservicio: {}", idSubservicio);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			precioMN = root.path("precioMN").asDouble();
			logger.debug("precioMN: {}", precioMN);

			precioME = root.path("precioME").asDouble();
			logger.debug("precioME: {}", precioME);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (idServicio == 0 || idSubservicio == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			Subservicio subservicio = new Subservicio();
			subservicio.setSubservicioId(new SubservicioId(idServicio, idSubservicio));
			subservicio.setNombre(nombre);
			subservicio.setPrecioMN(precioMN);
			subservicio.setPrecioME(precioME);

			responseSubservicio = subservicioRepository.save(subservicio);
			logger.debug("Subservicio guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseSubservicio);
	}

	public ApiResponse delete(Integer idServicio, Integer idSubservicio) throws ApiException {
		SubservicioId subservicioId = new SubservicioId(idServicio, idSubservicio);
		Optional<Subservicio> optSubservicio = subservicioRepository.findById(subservicioId);
		logger.debug("Subservicio: {}", optSubservicio);
		if (optSubservicio.isPresent()) {
			subservicioRepository.delete(optSubservicio.get());
			logger.debug("Subservicio eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Subservicio " + idServicio + " - " + idSubservicio + " eliminado");
		}
		throw new ApiException(ApiError.SUBSERVICIO_NOT_FOUND.getCode(), ApiError.SUBSERVICIO_NOT_FOUND.getMessage());
	}


}
