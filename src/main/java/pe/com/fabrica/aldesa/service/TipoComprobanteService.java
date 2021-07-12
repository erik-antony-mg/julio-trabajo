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
import pe.com.fabrica.aldesa.beans.TipoComprobante;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.TipoComprobanteRepository;

@Service
public class TipoComprobanteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoComprobanteRepository tipoComprobanteRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public TipoComprobanteService(TipoComprobanteRepository tipoComprobanteRepository) {
		this.tipoComprobanteRepository = tipoComprobanteRepository;
	}

	public ApiResponse findAll1() {
		List<TipoComprobante> listTypeDocuments = tipoComprobanteRepository.findAll();
		int total = listTypeDocuments.size();
		logger.debug("Total Tipo de Comprobante: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listTypeDocuments, total);
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<TipoComprobante> tiposComprobantesPage = tipoComprobanteRepository.findAll(pageable);
		//int total = tiposComprobantes.size();
		logger.debug("Página {} de: {}", pageNumber, tiposComprobantesPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tiposComprobantesPage.getContent(),
				Math.toIntExact(tiposComprobantesPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		TipoComprobante tmpTipCompro = tipoComprobanteRepository.findById(id).orElse(null);
		logger.debug("Tipo Comprobante: {}", tmpTipCompro);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTipCompro);
	}

	public ApiResponse save(String request) throws ApiException {
		TipoComprobante responseTipoComprobante;

		JsonNode root;
		String	nombre = null;
		String	abreviatura = null;
		String	codigoSunat = null;
		try {
			root = new ObjectMapper().readTree(request);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

			codigoSunat = root.path("codSunat").asText();
			logger.debug("codigoSunat: {}", codigoSunat);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		try {
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setNombre(nombre);
			tipoComprobante.setAbreviatura(abreviatura.toUpperCase());
			tipoComprobante.setCodSunat(codigoSunat);

			responseTipoComprobante = tipoComprobanteRepository.save(tipoComprobante);
			logger.debug("Tipo comprobante guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoComprobante);
	}

	public ApiResponse update(String request) throws ApiException {
		TipoComprobante responseTipoComprobante;

		JsonNode root;
		Integer	id = null;
		String	nombre = null;
		String	codigoSunat = null;
		String	abreviatura = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("idTipoComprobante").asInt();
			logger.debug("id: {}", id);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			codigoSunat = root.path("codSunat").asText();
			logger.debug("codigoSunat: {}", codigoSunat);

			abreviatura = root.path("abreviatura").asText();
			logger.debug("abreviatura: {}", abreviatura);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || StringUtils.isBlank(abreviatura)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsTipoComprobante = tipoComprobanteRepository.existsById(id);
		logger.debug("Existe TipoComprobante? {}", existsTipoComprobante);
		if (!existsTipoComprobante) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		try {
			TipoComprobante tipoComprobante = new TipoComprobante();
			tipoComprobante.setIdTipoComprobante(id);
			tipoComprobante.setNombre(nombre);
			tipoComprobante.setAbreviatura(abreviatura.toUpperCase());
			tipoComprobante.setCodSunat(codigoSunat);

			responseTipoComprobante = tipoComprobanteRepository.save(tipoComprobante);
			logger.debug("Tipo Comprobante actualizado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTipoComprobante);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		TipoComprobante tmpTipoComprobante = tipoComprobanteRepository.findById(id).orElse(null);
		logger.debug("TipoComprobante: {}", tmpTipoComprobante);
		if (null != tmpTipoComprobante) {
			tipoComprobanteRepository.deleteById(id);
			logger.debug("TipoComprobante eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "TipoComprobante " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByNombre(String nombre) {
		List<TipoComprobante> tipoComprobante = tipoComprobanteRepository.findByNombreContaining(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tipoComprobante,tipoComprobante.size());
	}

	public ApiResponse findByAbreviatura(String abreviatura) {
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findByAbreviatura(abreviatura);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tipoComprobante);
	}

	public ApiResponse findByCodigoSunat(String codigoSunat) {
		TipoComprobante tipoComprobante = tipoComprobanteRepository.findByCodigoSunat(codigoSunat);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tipoComprobante);
	}

}
