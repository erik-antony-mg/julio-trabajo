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
import pe.com.fabrica.aldesa.beans.SerieComprobante;
import pe.com.fabrica.aldesa.beans.TipoComprobante;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.SerieComprobanteRepository;
import pe.com.fabrica.aldesa.repository.TipoComprobanteRepository;

@Service
public class SerieComprobanteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SerieComprobanteRepository serieComprobanteRepository;

	@Autowired
	private TipoComprobanteRepository tipoComprobanteRespository;
	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<SerieComprobante> SerieComprobantePage = serieComprobanteRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, SerieComprobantePage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
				SerieComprobantePage.getContent(), Math.toIntExact(SerieComprobantePage.getTotalElements()));
	}

	public ApiResponse findAllTipo(Integer tipoComprobante) {
		List<SerieComprobante> series = serieComprobanteRepository.searchBySerieTipo(tipoComprobante);
		int total = series.size();
		logger.debug("Total Series: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), series, total);
	}

	public ApiResponse findById(Integer id) {
		SerieComprobante agencia = serieComprobanteRepository.findById(id).orElse(null);
		logger.debug("Series: {}", agencia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), agencia);
	}

	public ApiResponse findByTipoComprobante(Integer tipoComprobante, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<SerieComprobante> serieComprobante = serieComprobanteRepository.findByTipoComprobante(tipoComprobante,
				pageable);
		logger.debug("Página {} de: {}", pageNumber, serieComprobante.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), serieComprobante.getContent(),
				Math.toIntExact(serieComprobante.getTotalElements()));
	}

	// nuevo
	public ApiResponse findBySerie(String serie) {
		List<SerieComprobante> comprobanteserie = serieComprobanteRepository.findBySerie(serie);
		logger.debug("SerieComprobante by serie: {}", comprobanteserie);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobanteserie,
				comprobanteserie.size());
	}

	// nuevo descripcion
	public ApiResponse findByDesNew(String serie, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		List<SerieComprobante> comprobanteserie = serieComprobanteRepository.findByDesNew(serie, pageable);
		logger.debug("SerieComprobante by descripcion: {}", comprobanteserie);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobanteserie,
				comprobanteserie.size());
	}

	// nuevo de nuevo
	public ApiResponse findBySerieNew(Integer id_ticomprobante, String termino, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		List<SerieComprobante> comprobanteserie = serieComprobanteRepository.findBySerieNew(id_ticomprobante, termino,
				pageable);
		logger.debug("SerieComprobante by serie : {}", comprobanteserie);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobanteserie,
				comprobanteserie.size());
	}

	// nuevo de nuevo descripcion
	public ApiResponse findByDes(Integer id_ticomprobante, String termino, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		List<SerieComprobante> comprobanteserie = serieComprobanteRepository.findByDes(id_ticomprobante, termino,
				pageable);
		logger.debug("SerieComprobante by serie : {}", comprobanteserie);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobanteserie,
				comprobanteserie.size());
	}

	public ApiResponse save(String request) throws ApiException {
		SerieComprobante responseSerie;

		JsonNode root;
		Integer idTipoComprobante = null;
		String serie = null;
		String numero = null;
		String descripcion = null;
		try {
			root = new ObjectMapper().readTree(request);

			idTipoComprobante = root.path("idTipoComprobante").asInt();
			logger.debug("idTipoComprobante: {}", idTipoComprobante);

			serie = root.path("serie").asText();
			logger.debug("serie: {}", serie);

			numero = root.path("numero").asText();
			logger.debug("numero {}", numero);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion {}", descripcion);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idTipoComprobante || idTipoComprobante == 0 || StringUtils.isBlank(serie)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		TipoComprobante tipoComprobante = tipoComprobanteRespository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		try {
			SerieComprobante serieComprobante = new SerieComprobante();
			serieComprobante.setTipoComprobante(tipoComprobante);
			serieComprobante.setSerie(serie);
			serieComprobante.setNumero(numero);
			serieComprobante.setDescripcion(descripcion);

			responseSerie = serieComprobanteRepository.save(serieComprobante);
			logger.debug("SerieComprobante guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseSerie);
	}

	public ApiResponse update(String request) throws ApiException {
		SerieComprobante responseSerie;

		JsonNode root;
		Integer id = null;
		Integer idTipoComprobante = null;
		String serie = null;
		String numero = null;
		String descripcion = null;

		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			idTipoComprobante = root.path("idTipoComprobante").asInt();
			logger.debug("idTipoComprobante: {}", idTipoComprobante);

			serie = root.path("serie").asText();
			logger.debug("serie: {}", serie);

			numero = root.path("numero").asText();
			logger.debug("numero: {}", numero);

			descripcion = root.path("descripcion").asText();
			logger.debug("descripcion: {}", descripcion);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idTipoComprobante || idTipoComprobante == 0
				|| StringUtils.isBlank(serie)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsIdSerie = serieComprobanteRepository.existsById(id);
		logger.debug("Existe idSerie {}? {}", id, existsIdSerie);
		if (!existsIdSerie) {
			throw new ApiException(ApiError.SERIE_NOT_FOUND.getCode(), ApiError.SERIE_NOT_FOUND.getMessage());
		}

		TipoComprobante tipoComprobante = tipoComprobanteRespository.findById(idTipoComprobante)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_COMPROBANTE_NOT_FOUND.getCode(),
						ApiError.TIPO_COMPROBANTE_NOT_FOUND.getMessage()));

		try {
			SerieComprobante serieComprobante = new SerieComprobante();
			serieComprobante.setIdSerieComprobante(id);
			serieComprobante.setTipoComprobante(tipoComprobante);
			serieComprobante.setSerie(serie);
			serieComprobante.setNumero(numero);
			serieComprobante.setDescripcion(descripcion);
			responseSerie = serieComprobanteRepository.save(serieComprobante);
			logger.debug("SerieComprobante actualizada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseSerie);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		SerieComprobante tmpSerie = serieComprobanteRepository.findById(id).orElse(null);
		logger.debug("Serie Comprobante: {}", tmpSerie);
		if (null != tmpSerie) {
			serieComprobanteRepository.deleteById(id);
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Serie " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse findByComprobante(String comprobante) {
		List<SerieComprobante> serieComprobante = serieComprobanteRepository
				.findByTipoComprobanteNombreContaining(comprobante);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), serieComprobante,
				serieComprobante.size());
	}

	public ApiResponse findByDescripcion(String descripcion) {
		List<SerieComprobante> serieComprobante = serieComprobanteRepository.findByDescripcionContaining(descripcion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), serieComprobante,
				serieComprobante.size());
	}

}
