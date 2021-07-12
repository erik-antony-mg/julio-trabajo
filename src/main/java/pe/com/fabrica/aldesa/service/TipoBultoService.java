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
import pe.com.fabrica.aldesa.beans.TipoBulto;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.TipoBultoRepository;

@Service
public class TipoBultoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoBultoRepository tipoBultoRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public TipoBultoService(TipoBultoRepository tipoBultoRepository) {
		this.tipoBultoRepository = tipoBultoRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<TipoBulto> tiposBultos = tipoBultoRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, tiposBultos.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tiposBultos.getContent(),
				Math.toIntExact(tiposBultos.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		TipoBulto tmpTipBulto = tipoBultoRepository.findById(id).orElse(null);
		logger.debug("Tipo bulto: {}", tmpTipBulto);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTipBulto);
	}

	public ApiResponse save(String request) throws ApiException {
		TipoBulto responseTipoBulto;

		JsonNode root;
		String nombre = null;
		String abreviatura = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			TipoBulto tipoBulto = new TipoBulto();
			tipoBulto.setNombre(nombre);
			tipoBulto.setAbreviatura(abreviatura.toUpperCase());

			responseTipoBulto = tipoBultoRepository.save(tipoBulto);
			logger.debug("Tipo bulto guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoBulto);
	}

	public ApiResponse update(String request) throws ApiException {
		TipoBulto responseTipoBulto;

		JsonNode root;
		Integer id = null;
		String nombre = null;
		String abreviatura = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("idTipoBulto").asInt();
			logger.debug("idTipoBulto: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			TipoBulto tipoBulto = new TipoBulto();
			tipoBulto.setIdTipoBulto(id);
			tipoBulto.setNombre(nombre);
			tipoBulto.setAbreviatura(abreviatura.toUpperCase());

			responseTipoBulto = tipoBultoRepository.save(tipoBulto);
			logger.debug("Tipo bulto actualizado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoBulto);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		TipoBulto tmpTipoBulto = tipoBultoRepository.findById(id).orElse(null);
		logger.debug("Tipo bulto: {}", tmpTipoBulto);
		Integer numd =  tipoBultoRepository.findEliminarDamById(id);
		Integer numm = tipoBultoRepository.findEliminarMercanciaById(id);
		String usadoEn = "tipo bulto";

		if (null != tmpTipoBulto && numd==0 && numm==0) {
			tipoBultoRepository.deleteById(id);
			logger.debug("Tipo bulto eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Tipo bulto " + id + " eliminado");
		}

		if(numd >=1 && numm >=1){
			usadoEn = "dam";
			usadoEn = usadoEn + ", " + "mercancia";
		}else if(numd >= 1){
			usadoEn = "dam";
		}else if(numm >= 1){
			usadoEn = "mercancia";
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Tipo bulto " + tmpTipoBulto.getNombre() + " esta siendo usado en " + usadoEn + ".");
	}

	public ApiResponse findByNombre(String nombre) {
		List<TipoBulto> listaServicios = tipoBultoRepository.findByNombre(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listaServicios,
				listaServicios.size());
	}

	public ApiResponse findByAbreviatura(String abreviatura) {
		List<TipoBulto> listaServicios = tipoBultoRepository.findByAbreviatura(abreviatura);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listaServicios, listaServicios.size());
	}

}
