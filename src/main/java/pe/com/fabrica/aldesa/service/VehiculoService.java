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
import pe.com.fabrica.aldesa.beans.TipoVehiculo;
import pe.com.fabrica.aldesa.beans.Vehiculo;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.TipoVehiculoRepository;
import pe.com.fabrica.aldesa.repository.VehiculoRepository;

@Service
public class VehiculoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private TipoVehiculoRepository tipoVehiculoRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Vehiculo> vehiculos = vehiculoRepository.findAll(pageable);
		logger.debug("Empresa {} de: {}", pageNumber, vehiculos.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), vehiculos.getContent(),
				Math.toIntExact(vehiculos.getTotalElements()));
	}


	public ApiResponse findById(Long id) {
		Vehiculo tmpCamion = vehiculoRepository.findById(id).orElse(null);
		logger.debug("Vehiculo: {}", tmpCamion);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpCamion);
	}

	public ApiResponse save(String request) throws ApiException {
		Vehiculo responseVehiculo;

		JsonNode root;
		Integer codeTipoVehiculo = null;
		String placa = null;
		String marca = null;
		String certificado = null;
		Double largo = null;
		Double ancho = null;
		Double alto = null;
		Double peso = null;
		String eje = null;
		try {
			root = new ObjectMapper().readTree(request);

			codeTipoVehiculo = root.path("codeTipoVehiculo").asInt();
			logger.debug("codeTipoVehiculo: {}", codeTipoVehiculo);

			placa = root.path("placa").asText();
			logger.debug("placa: {}", placa);

			marca = root.path("marca").asText();
			logger.debug("marca: {}", marca);

			certificado = root.path("certificado").asText();
			logger.debug("certificado: {}", certificado);

			largo = root.path("largo").asDouble();
			logger.debug("largo: {}", largo);

			ancho = root.path("ancho").asDouble();
			logger.debug("ancho: {}", ancho);

			alto = root.path("alto").asDouble();
			logger.debug("alto: {}", alto);

			peso = root.path("peso").asDouble();
			logger.debug("peso: {}", peso);

			eje = root.path("eje").asText();
			logger.debug("eje: {}", eje);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (0 == codeTipoVehiculo || StringUtils.isBlank(placa) || StringUtils.isBlank(marca)
				|| StringUtils.isBlank(certificado) || null == largo || null == ancho
				|| null == alto || null == peso || null == eje) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(codeTipoVehiculo)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Vehiculo vehiculo = new Vehiculo();
			vehiculo.setTipoVehiculo(tipoVehiculo);
			vehiculo.setPlaca(placa);
			vehiculo.setMarca(marca);
			vehiculo.setCertificado(certificado);
			vehiculo.setLargo(largo);
			vehiculo.setAncho(ancho);
			vehiculo.setAlto(alto);
			vehiculo.setPeso(peso);
			vehiculo.setEje(eje);

			responseVehiculo = vehiculoRepository.save(vehiculo);
			logger.debug("Vehiculo guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseVehiculo);
	}

	public ApiResponse update(String request) throws ApiException {
		Vehiculo responseVehiculo;

		JsonNode root;
		Long id = null;
		Integer codeTipoVehiculo = null;
		String placa = null;
		String marca = null;
		String certificado = null;
		Double largo = null;
		Double ancho = null;
		Double alto = null;
		Double peso = null;
		String eje = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			codeTipoVehiculo = root.path("codeTipoVehiculo").asInt();
			logger.debug("codeTipoVehiculo: {}", codeTipoVehiculo);

			placa = root.path("placa").asText();
			logger.debug("placa: {}", placa);

			marca = root.path("marca").asText();
			logger.debug("marca: {}", marca);

			certificado = root.path("certificado").asText();
			logger.debug("certificado: {}", certificado);

			largo = root.path("largo").asDouble();
			logger.debug("largo: {}", largo);

			ancho = root.path("ancho").asDouble();
			logger.debug("ancho: {}", ancho);

			alto = root.path("alto").asDouble();
			logger.debug("alto: {}", alto);

			peso = root.path("peso").asDouble();
			logger.debug("peso: {}", peso);

			eje = root.path("eje").asText();
			logger.debug("eje: {}", eje);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == codeTipoVehiculo || StringUtils.isBlank(placa) || StringUtils.isBlank(marca)
				|| StringUtils.isBlank(certificado) || null == largo || null == ancho
				|| null == alto || null == peso || null == eje) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(codeTipoVehiculo)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));

		try {
			Vehiculo vehiculo = new Vehiculo();
			vehiculo.setIdVehiculo(id);
			vehiculo.setTipoVehiculo(tipoVehiculo);
			vehiculo.setPlaca(placa);
			vehiculo.setMarca(marca);
			vehiculo.setCertificado(certificado);
			vehiculo.setLargo(largo);
			vehiculo.setAncho(ancho);
			vehiculo.setAlto(alto);
			vehiculo.setPeso(peso);
			vehiculo.setEje(eje);

			responseVehiculo = vehiculoRepository.save(vehiculo);
			logger.debug("Vehiculo actualizado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseVehiculo);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Vehiculo tmpCamion = vehiculoRepository.findById(id).orElse(null);
		logger.debug("Camion: {}", tmpCamion);
		Long numtp = vehiculoRepository.findEliminarTicketPesajeById(id);
		if (null != tmpCamion && numtp==0) {
			vehiculoRepository.deleteById(id);
			logger.debug("Vehiculo eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Camion " + tmpCamion.getPlaca() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Camion " + tmpCamion.getPlaca() + " esta siendo usado en tieckt pesaje.");
	}

	public ApiResponse filtradoVehiculos(String filtro) {
		List<Vehiculo> Vehiculo = vehiculoRepository.filtradoVehiculos(filtro);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Vehiculo, Vehiculo.size());
	}
}
