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
import pe.com.fabrica.aldesa.beans.Aduana;
import pe.com.fabrica.aldesa.beans.AgenciasAduanas;
import pe.com.fabrica.aldesa.beans.Dam;
import pe.com.fabrica.aldesa.beans.DepositoTemporal;
import pe.com.fabrica.aldesa.beans.Regimen;
import pe.com.fabrica.aldesa.beans.TipoBulto;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.AduanaRepository;
import pe.com.fabrica.aldesa.repository.AgenciaAduanasRepository;
import pe.com.fabrica.aldesa.repository.DamRepository;
import pe.com.fabrica.aldesa.repository.DepositoTemporalRepository;
import pe.com.fabrica.aldesa.repository.RegimenRepository;
import pe.com.fabrica.aldesa.repository.TipoBultoRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class DamService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DamRepository damRepository;

	@Autowired
	private AduanaRepository aduanaRepository;

	@Autowired
	private AgenciaAduanasRepository agenciaAduanasRepository;

	@Autowired
	private DepositoTemporalRepository depositoTemporalRepository;

	@Autowired
	private RegimenRepository regimenRepository;

	@Autowired
	private TipoBultoRepository tipoBultoRepository;

	public ApiResponse findAll() {
		List<Dam> dams = damRepository.findAll();
		int total = dams.size();
		logger.debug("Total Dam: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), dams, total);
	}

	public ApiResponse findById(Long id) {
		Dam tmpDam = damRepository.findById(id).orElse(null);
		logger.debug("Dam: {}", tmpDam);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpDam);
	}

	public ApiResponse save(String request) throws ApiException {
		Dam responseDam;

		JsonNode root;
		Integer	idAduana = null;
		Long idAgenciaAduanas = null;
		Integer	idDepositoTemporal = null;
		Integer	idRegimen = null;
		Integer	idTipoBulto = null;
		String	numeroDeclaracion = null;
		String	numeroManifiesto = null;
		String	fechaNumeracion = null;
		String	docTransporte = null;
		String	numeroFacComercial = null;
		String	fechaFacComercial = null;
		Integer	idClase = null;
		String	subpartidaNacional = null;
		Double	fob = null;
		Double	flete = null;
		Double	seguro = null;
		Double	ajuste = null;
		Double	total = null;
		try {
			root = new ObjectMapper().readTree(request);

			idAduana = root.path("idAduana").asInt();
			logger.debug("idAduana: {}", idAduana);

			idAgenciaAduanas = root.path("idAgenciaAduanas").asLong();
			logger.debug("idAgenciaAduanas: {}", idAgenciaAduanas);

			idDepositoTemporal = root.path("idDepositoTemporal").asInt();
			logger.debug("idDepositoTemporal: {}", idDepositoTemporal);

			idRegimen = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", idRegimen);

			idTipoBulto = root.path("idTipoBulto").asInt();
			logger.debug("idTipoBulto: {}", idTipoBulto);

			numeroDeclaracion = root.path("numeroDeclaracion").asText();
			logger.debug("numeroDeclaracion: {}", numeroDeclaracion);

			numeroManifiesto = root.path("numeroManifiesto").asText();
			logger.debug("numeroManifiesto: {}", numeroManifiesto);

			fechaNumeracion = root.path("fechaNumeracion").asText();
			logger.debug("fechaNumeracion: {}", fechaNumeracion);

			docTransporte = root.path("docTransporte").asText();
			logger.debug("docTransporte: {}", docTransporte);

			numeroFacComercial = root.path("numeroFacComercial").asText();
			logger.debug("numeroFacComercial: {}", numeroFacComercial);

			fechaFacComercial = root.path("fechaFacComercial").asText();
			logger.debug("fechaFacComercial: {}", fechaFacComercial);

			idClase = root.path("idClase").asInt();
			logger.debug("idClase: {}", idClase);

			subpartidaNacional = root.path("subpartidaNacional").asText();
			logger.debug("subpartidaNacional: {}", subpartidaNacional);

			fob = root.path("fob").asDouble();
			logger.debug("fob: {}", fob);

			flete = root.path("flete").asDouble();
			logger.debug("flete: {}", flete);

			seguro = root.path("seguro").asDouble();
			logger.debug("seguro: {}", seguro);

			ajuste = root.path("ajuste").asDouble();
			logger.debug("ajuste: {}", ajuste);

			total = root.path("total").asDouble();
			logger.debug("total: {}", total);


		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (idAduana == 0 || idAgenciaAduanas == 0 || idDepositoTemporal == 0
				|| idRegimen == 0 || idTipoBulto == 0 || StringUtils.isBlank(numeroDeclaracion)
				|| StringUtils.isBlank(numeroManifiesto) || StringUtils.isBlank(fechaNumeracion)
				|| StringUtils.isBlank(numeroFacComercial) || StringUtils.isBlank(fechaFacComercial)
				|| idClase == 0 || StringUtils.isBlank(subpartidaNacional) || fob == 0
				|| flete == 0 || seguro == 0 || ajuste == 0 || total == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Aduana aduana = aduanaRepository.findById(idAduana)
				.orElseThrow(() -> new ApiException(ApiError.ADUANA_NOT_FOUND.getCode(), ApiError.ADUANA_NOT_FOUND.getMessage()));
		logger.debug("Aduana: {}", aduana);

		AgenciasAduanas agenciaAduana = agenciaAduanasRepository.findById(idAgenciaAduanas)
				.orElseThrow(() -> new ApiException(ApiError.AGENCIA_ADUANA_NOT_FOUND.getCode(), ApiError.AGENCIA_ADUANA_NOT_FOUND.getMessage()));
		logger.debug("agenciaAduana: {}", agenciaAduana);

		DepositoTemporal depositoTemporal = depositoTemporalRepository.findById(idDepositoTemporal)
				.orElseThrow(() -> new ApiException(ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getCode(), ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getMessage()));
		logger.debug("depositoTemporal: {}", depositoTemporal);

		Regimen regimen = regimenRepository.findById(idRegimen)
				.orElseThrow(() -> new ApiException(ApiError.REGIMEN_NOT_FOUND.getCode(), ApiError.REGIMEN_NOT_FOUND.getMessage()));
		logger.debug("regimen: {}", regimen);

		TipoBulto tipoBulto = tipoBultoRepository.findById(idTipoBulto)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_BULTO_NOT_FOUND.getCode(), ApiError.TIPO_BULTO_NOT_FOUND.getMessage()));
		logger.debug("tipoBulto: {}", tipoBulto);

		try {
			Dam dam = new Dam();
			dam.setIdAduana(aduana);
			dam.setIdAgenciaAduanas(agenciaAduana);
			dam.setIdDepositoTemporal(depositoTemporal);
			dam.setIdRegimen(regimen);
			dam.setIdTipoBulto(tipoBulto);
			dam.setNumeroDeclaracion(numeroDeclaracion);
			dam.setNumeroManifiesto(numeroManifiesto);
			dam.setFechaNumeracion(DateUtil.of(fechaNumeracion));
			if (StringUtils.isNotBlank(docTransporte) && !"null".equals(docTransporte))
				dam.setDocTransporte(docTransporte);
			dam.setNumeroFacComercial(numeroFacComercial);
			dam.setFechaFacComercial(DateUtil.of(fechaFacComercial));
			dam.setIdClase(idClase);
			dam.setSubpartidaNacional(subpartidaNacional);
			dam.setFob(fob);
			dam.setFlete(flete);
			dam.setSeguro(seguro);
			dam.setAjuste(ajuste);
			dam.setTotal(total);

			responseDam = damRepository.save(dam);
			logger.debug("Dam guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDam);
	}

	public ApiResponse update(String request) throws ApiException {
		Dam responseDam;

		JsonNode root;
		Long id = null;
		Integer	idAduana = null;
		Long idAgenciaAduanas = null;
		Integer	idDepositoTemporal = null;
		Integer	idRegimen = null;
		Integer	idTipoBulto = null;
		String	numeroDeclaracion = null;
		String	numeroManifiesto = null;
		String	fechaNumeracion = null;
		String	docTransporte = null;
		String	numeroFacComercial = null;
		String	fechaFacComercial = null;
		Integer	idClase = null;
		String	subpartidaNacional = null;
		Double	fob = null;
		Double	flete = null;
		Double	seguro = null;
		Double	ajuste = null;
		Double	total = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			idAduana = root.path("idAduana").asInt();
			logger.debug("idAduana: {}", idAduana);

			idAgenciaAduanas = root.path("idAgenciaAduanas").asLong();
			logger.debug("idAgenciaAduanas: {}", idAgenciaAduanas);

			idDepositoTemporal = root.path("idDepositoTemporal").asInt();
			logger.debug("idDepositoTemporal: {}", idDepositoTemporal);

			idRegimen = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", idRegimen);

			idTipoBulto = root.path("idTipoBulto").asInt();
			logger.debug("idTipoBulto: {}", idTipoBulto);

			numeroDeclaracion = root.path("numeroDeclaracion").asText();
			logger.debug("numeroDeclaracion: {}", numeroDeclaracion);

			numeroManifiesto = root.path("numeroManifiesto").asText();
			logger.debug("numeroManifiesto: {}", numeroManifiesto);

			fechaNumeracion = root.path("fechaNumeracion").asText();
			logger.debug("fechaNumeracion: {}", fechaNumeracion);

			docTransporte = root.path("docTransporte").asText();
			logger.debug("docTransporte: {}", docTransporte);

			numeroFacComercial = root.path("numeroFacComercial").asText();
			logger.debug("numeroFacComercial: {}", numeroFacComercial);

			fechaFacComercial = root.path("fechaFacComercial").asText();
			logger.debug("fechaFacComercial: {}", fechaFacComercial);

			idClase = root.path("idClase").asInt();
			logger.debug("idClase: {}", idClase);

			subpartidaNacional = root.path("subpartidaNacional").asText();
			logger.debug("subpartidaNacional: {}", subpartidaNacional);

			fob = root.path("fob").asDouble();
			logger.debug("fob: {}", fob);

			flete = root.path("flete").asDouble();
			logger.debug("flete: {}", flete);

			seguro = root.path("seguro").asDouble();
			logger.debug("seguro: {}", seguro);

			ajuste = root.path("ajuste").asDouble();
			logger.debug("ajuste: {}", ajuste);

			total = root.path("total").asDouble();
			logger.debug("total: {}", total);


		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (id == 0 || idAduana == 0 || idAgenciaAduanas == 0 || idDepositoTemporal == 0
				|| idRegimen == 0 || idTipoBulto == 0 || StringUtils.isBlank(numeroDeclaracion)
				|| StringUtils.isBlank(numeroManifiesto) || StringUtils.isBlank(fechaNumeracion)
				|| StringUtils.isBlank(numeroFacComercial) || StringUtils.isBlank(fechaFacComercial)
				|| idClase == 0 || StringUtils.isBlank(subpartidaNacional) || fob == 0
				|| flete == 0 || seguro == 0 || ajuste == 0 || total == 0 || StringUtils.isBlank(docTransporte)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsDam = damRepository.existsById(id);
		logger.debug("Exists Dam {}? {}", id, existsDam);
		if (!existsDam ) {
			throw new ApiException(ApiError.DAM_NOT_FOUND.getCode(), ApiError.DAM_NOT_FOUND.getMessage());
		}

		Aduana aduana = aduanaRepository.findById(idAduana)
				.orElseThrow(() -> new ApiException(ApiError.ADUANA_NOT_FOUND.getCode(), ApiError.ADUANA_NOT_FOUND.getMessage()));
		logger.debug("Aduana: {}", aduana);

		AgenciasAduanas agenciaAduana = agenciaAduanasRepository.findById(idAgenciaAduanas)
				.orElseThrow(() -> new ApiException(ApiError.AGENCIA_ADUANA_NOT_FOUND.getCode(), ApiError.AGENCIA_ADUANA_NOT_FOUND.getMessage()));
		logger.debug("agenciaAduana: {}", agenciaAduana);

		DepositoTemporal depositoTemporal = depositoTemporalRepository.findById(idDepositoTemporal)
				.orElseThrow(() -> new ApiException(ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getCode(), ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getMessage()));
		logger.debug("depositoTemporal: {}", depositoTemporal);

		Regimen regimen = regimenRepository.findById(idRegimen)
				.orElseThrow(() -> new ApiException(ApiError.REGIMEN_NOT_FOUND.getCode(), ApiError.REGIMEN_NOT_FOUND.getMessage()));
		logger.debug("regimen: {}", regimen);

		TipoBulto tipoBulto = tipoBultoRepository.findById(idTipoBulto)
				.orElseThrow(() -> new ApiException(ApiError.TIPO_BULTO_NOT_FOUND.getCode(), ApiError.TIPO_BULTO_NOT_FOUND.getMessage()));
		logger.debug("tipoBulto: {}", tipoBulto);

		try {
			Dam dam = new Dam();
			dam.setIdDam(id);
			dam.setIdAduana(aduana);
			dam.setIdAgenciaAduanas(agenciaAduana);
			dam.setIdDepositoTemporal(depositoTemporal);
			dam.setIdRegimen(regimen);
			dam.setIdTipoBulto(tipoBulto);
			dam.setNumeroDeclaracion(numeroDeclaracion);
			dam.setNumeroManifiesto(numeroManifiesto);
			dam.setFechaNumeracion(DateUtil.of(fechaNumeracion));
			if (StringUtils.isNotBlank(docTransporte) && !"null".equals(docTransporte))
				dam.setDocTransporte(docTransporte);
			dam.setNumeroFacComercial(numeroFacComercial);
			dam.setFechaFacComercial(DateUtil.of(fechaFacComercial));
			dam.setIdClase(idClase);
			dam.setSubpartidaNacional(subpartidaNacional);
			dam.setFob(fob);
			dam.setFlete(flete);
			dam.setSeguro(seguro);
			dam.setAjuste(ajuste);
			dam.setTotal(total);

			responseDam = damRepository.save(dam);
			logger.debug("Dam actualizada");

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseDam);

	}

}
