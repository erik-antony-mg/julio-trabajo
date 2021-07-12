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
import pe.com.fabrica.aldesa.beans.FormaPago;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.FormaPagoRepository;

@Service
public class FormaPagoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private FormaPagoRepository formaPagoRepository;

	@Autowired
	public FormaPagoService(FormaPagoRepository formaPagoRepository) {
		this.formaPagoRepository = formaPagoRepository;
	}

	public ApiResponse findAll() {
		List<FormaPago> formasPago = formaPagoRepository.findAll();
		int total = formasPago.size();
		logger.debug("Total Formas Pago: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), formasPago, total);
	}

	public ApiResponse findById(Integer id) {
		FormaPago tmpFormaPago = formaPagoRepository.findById(id).orElse(null);
		logger.debug("Forma Pago: {}", tmpFormaPago);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpFormaPago);
	}

	public ApiResponse save(String request) throws ApiException {
		FormaPago responseFormaPago;

		JsonNode root;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			FormaPago formaPago = new FormaPago();
			formaPago.setNombre(nombre);

			responseFormaPago = formaPagoRepository.save(formaPago);
			logger.debug("FormaPago guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseFormaPago);
	}

	public ApiResponse update(String request) throws ApiException {
		FormaPago responseFormaPago;

		JsonNode root;
		Integer id = null;
		String	nombre = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			FormaPago formaPago = new FormaPago();
			formaPago.setIdFormaPago(id);
			formaPago.setNombre(nombre);

			responseFormaPago = formaPagoRepository.save(formaPago);
			logger.debug("FormaPago actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseFormaPago);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		FormaPago tmpFormaPago = formaPagoRepository.findById(id).orElse(null);
		logger.debug("FormaPago: {}", tmpFormaPago);
		if (null != tmpFormaPago) {
			formaPagoRepository.deleteById(id);
			logger.debug("FormaPago eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "FormaPago " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

}
