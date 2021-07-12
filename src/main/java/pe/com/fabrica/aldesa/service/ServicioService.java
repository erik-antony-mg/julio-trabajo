package pe.com.fabrica.aldesa.service;

import java.util.List;
import java.util.Optional;

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
import pe.com.fabrica.aldesa.beans.GrupoServicio;
import pe.com.fabrica.aldesa.beans.Servicio;
import pe.com.fabrica.aldesa.beans.Unspsc;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.GrupoServicioRepository;
import pe.com.fabrica.aldesa.repository.ServicioRepository;
import pe.com.fabrica.aldesa.repository.UnspscRepository;

@Service
public class ServicioService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private GrupoServicioRepository grupoServicioRepository;

	@Autowired
	private UnspscRepository unspscRepository;

	private static final int PAGE_LIMIT = 10;
	/* public ApiResponse findAll() {
		List<Servicio> servicios = servicioRepository.findAll();
		int total = servicios.size();
		logger.debug("Total Servicio: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), servicios, total);
	} */
	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Servicio> servicioPage = servicioRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, servicioPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), servicioPage.getContent(),
				Math.toIntExact(servicioPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Servicio tmpServicio = servicioRepository.findById(id).orElse(null);
		logger.debug("Servicio: {}", tmpServicio);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpServicio);
	}

	public ApiResponse findByGrupoServicios(Integer idGrupoServicio) throws ApiException {
		Optional<GrupoServicio> optGruposervicio = grupoServicioRepository.findById(idGrupoServicio);
		if (!optGruposervicio.isPresent()) {
			throw new ApiException(ApiError.GRUPO_SERVICIO_NOT_FOUND.getCode(),
					ApiError.GRUPO_SERVICIO_NOT_FOUND.getMessage());
		}
		List<Servicio> servicios = servicioRepository.findByGrupoServicio(optGruposervicio.get());
		int total = servicios.size();
		logger.debug("Servicio por grupo de servicios: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), servicios, total);
	}

	public ApiResponse save(String request) throws ApiException {
		Servicio responseServicio;

		JsonNode root;
		Integer idGrupoServicio = null;
		String nombre = null;
		String codigo = null;
		String pct = null;
		Double precioMonedaNacional = null;
		Double precioMonedaExtranjera = null;
		Long idCodigoSunat = null;

		try {
			root = new ObjectMapper().readTree(request);

			idGrupoServicio = root.path("idGrupoServicio").asInt();
			logger.debug("idGrupoServicio: {}", idGrupoServicio);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			idCodigoSunat = root.path("idCodigoSunat").asLong();
			logger.debug("idCodigoSunat: {}", idCodigoSunat);

			pct = root.path("pct").asText();
			logger.debug("pct: {}", pct);

			precioMonedaNacional = root.path("precioMonedaNacional").asDouble();
			logger.debug("precioMonedaNacional: {}", precioMonedaNacional);

			precioMonedaExtranjera = root.path("precioMonedaExtranjera").asDouble();
			logger.debug("precioMonedaExtranjera: {}", precioMonedaExtranjera);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(nombre) || null == idGrupoServicio || idGrupoServicio == 0
				|| StringUtils.isBlank(pct)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		GrupoServicio grupoServicio = grupoServicioRepository.findById(idGrupoServicio)
				.orElseThrow(() -> new ApiException(ApiError.GRUPO_SERVICIO_NOT_FOUND.getCode(),
						ApiError.GRUPO_SERVICIO_NOT_FOUND.getMessage()));

		Unspsc Unspsc = new Unspsc();
		Unspsc = unspscRepository.findById(idCodigoSunat)
				.orElseThrow(() -> new ApiException(ApiError.CODIGO_PRODUCTO_SUNAT_NOT_FOUND.getCode(),
						ApiError.CODIGO_PRODUCTO_SUNAT_NOT_FOUND.getMessage()));

		try {
			Servicio servicio = new Servicio();
			servicio.setNombre(nombre);
			servicio.setCodigo(codigo);
			servicio.setGrupoServicio(grupoServicio);
			servicio.setPct(pct);
			servicio.setPrecioMonedaNacional(precioMonedaNacional);
			servicio.setPrecioMonedaExtranjera(precioMonedaExtranjera);
			servicio.setUnspsc(Unspsc);

			responseServicio = servicioRepository.save(servicio);
			logger.debug("Servicio guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseServicio);
	}

	public ApiResponse update(String request) throws ApiException {
		Servicio responseServicio;

		JsonNode root;
		Integer id = null;
		Integer idGrupoServicio = null;
		String nombre = null;
		String pct = null;
		Double precioMonedaNacional = null;
		Double precioMonedaExtrangera = null;
		Long idCodigoSunat = null;
		String codigo = null;

		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asInt();
			logger.debug("id: {}", id);

			idGrupoServicio = root.path("idGrupoServicio").asInt();
			logger.debug("idGrupoServicio: {}", idGrupoServicio);

			nombre = root.path("nombre").asText();
			logger.debug("nombre: {}", nombre);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

			pct = root.path("pct").asText();
			logger.debug("pct: {}", pct);

			precioMonedaNacional = root.path("precioMonedaNacional").asDouble();
			logger.debug("precioMonedaNacional: {}", precioMonedaNacional);

			precioMonedaExtrangera = root.path("precioMonedaExtranjera").asDouble();
			logger.debug("precioMonedaExtranjera: {}", precioMonedaExtrangera);

			idCodigoSunat = root.path("idCodigoSunat").asLong();
			logger.debug("idCodigoSunat: {}", idCodigoSunat);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(nombre) || null == idGrupoServicio || idGrupoServicio == 0
				|| StringUtils.isBlank(pct)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		GrupoServicio grupoServicio = grupoServicioRepository.findById(idGrupoServicio)
				.orElseThrow(() -> new ApiException(ApiError.GRUPO_SERVICIO_NOT_FOUND.getCode(),
						ApiError.GRUPO_SERVICIO_NOT_FOUND.getMessage()));

		Unspsc Unspsc = new Unspsc();
		Unspsc = unspscRepository.findById(idCodigoSunat)
				.orElseThrow(() -> new ApiException(ApiError.CODIGO_PRODUCTO_SUNAT_NOT_FOUND.getCode(),
						ApiError.CODIGO_PRODUCTO_SUNAT_NOT_FOUND.getMessage()));

		boolean existServicio = servicioRepository.existsById(id);
		logger.debug("Existe servicio? {}", existServicio);

		if (!existServicio) {
			throw new ApiException(ApiError.SERVICIO_NOT_FOUND.getCode(), ApiError.SERVICIO_NOT_FOUND.getMessage());
		}

		try {
			Servicio servicio = new Servicio();
			servicio.setIdServicio(id);
			servicio.setNombre(nombre);
			servicio.setGrupoServicio(grupoServicio);
			servicio.setPct(pct);
			servicio.setCodigo(codigo);
			servicio.setPrecioMonedaNacional(precioMonedaNacional);
			servicio.setPrecioMonedaExtranjera(precioMonedaExtrangera);
			servicio.setUnspsc(Unspsc);;

			responseServicio = servicioRepository.save(servicio);
			logger.debug("Servicio guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseServicio);
	}

	public ApiResponse delete(Integer id) throws ApiException {
		Servicio tmpServicio = servicioRepository.findById(id).orElse(null);
		logger.debug("Servicio: {}", tmpServicio);
		Integer nums = servicioRepository.findEliminarSubservicioById(id);
		Integer numcd = servicioRepository.findEliminarCotizacionDetalleById(id);
		String usadoEn = "servicio";

		if (null != tmpServicio && nums==0 && numcd==0) {
			servicioRepository.deleteById(id);
			logger.debug("Servicio eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Servicio " + tmpServicio.getNombre() + " eliminado");
		}
		if(nums>=1 && numcd >=1){
			usadoEn = "subservicio";
			usadoEn = usadoEn + ", " + "cotización detalle";
		}else if(nums>=1){
			usadoEn = "subservicio";
		}else if(numcd>=1){
			usadoEn = "cotización detalle";
		}

		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Servicio " + tmpServicio.getNombre() + " esta siendo usado en " + usadoEn + ".");
	}

	public ApiResponse findByCodigoProductoSunat(Integer codigo) {
		List<Servicio> listaServicios = servicioRepository.findByCodigoProducto(codigo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listaServicios , listaServicios.size());
	}

	public ApiResponse findByDescripcion(String nombre) {
		List<Servicio> listaServicios = servicioRepository.findByNombre(nombre);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listaServicios, listaServicios.size());
	}

	public ApiResponse findByGrupo(Integer idGrupo) {
		List<Servicio> listaServicios = servicioRepository.findByGrupo(idGrupo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listaServicios, listaServicios.size());
	}

	public ApiResponse findByCodigo(String codigo) {
		Servicio servicio = servicioRepository.findByCodigo(codigo);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), servicio);
	}
	
}
