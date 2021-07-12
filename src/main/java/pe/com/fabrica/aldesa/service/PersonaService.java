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
import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.TipoDocumento;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DireccionRepository;
import pe.com.fabrica.aldesa.repository.PersonaRepository;
import pe.com.fabrica.aldesa.repository.TipoDocumentoRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class PersonaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Persona> personasPage = personaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, personasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), personasPage.getContent(),
				Math.toIntExact(personasPage.getTotalElements()));
	}

	public ApiResponse searchByNombresPag(String nombre, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Persona> personasPage = personaRepository.searchByNombresPag(nombre,pageable);
		logger.debug("Página {} de: {}", pageNumber, personasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), personasPage.getContent(),
				Math.toIntExact(personasPage.getTotalElements()));
	}


	


	public ApiResponse findById(Long id) {
		Persona tmpPersona = personaRepository.findById(id).orElse(null);
		logger.debug("Persona: {}", tmpPersona);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpPersona);
	}

	public ApiResponse searchByNombres(String nombre) {
		List<Persona> personas = personaRepository.searchByNombres(nombre);
		logger.debug("Personas by Name: {}", personas);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), personas, personas.size());
	}

	public ApiResponse findByNumeroDocumento(String numeroDocumento) {
		Optional<Persona> optPerson = personaRepository.findByNumeroDocumento(numeroDocumento);
		logger.debug("Persona by Numero Documento: {}", optPerson);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optPerson);
	}

	//nuevo
	public ApiResponse findByNumeroDocumento1(String numeroDocumento) {
		List<Persona> optPerson = personaRepository.findByNumeroDocumento1(numeroDocumento);
		logger.debug("Persona by Numero Documento: {}", optPerson);
		
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optPerson);
	}

	public ApiResponse save(String request) throws ApiException {
		Persona responsePersona;

		JsonNode root;
		String numeroDocumento = null;
		String nombres = null;
		String apellidoPaterno = null;
		String apellidoMaterno = null;
		String sexo = null;
		String fechaNacimiento = null;
		String email = null;
		Integer idTipoDocumento = null;
		Integer idDireccion = null;
		String telefono = null;

		try {
			root = new ObjectMapper().readTree(request);

			idTipoDocumento = root.path("idTipoDocumento").asInt();
			logger.debug("idTipoDocumento: {}", idTipoDocumento);

			numeroDocumento = root.path("numeroDocumento").asText();
			logger.debug("numeroDocumento: {}", numeroDocumento);

			nombres = root.path("nombres").asText();
			logger.debug("nombres: {}", nombres);

			apellidoPaterno = root.path("apellidoPaterno").asText();
			logger.debug("apellidoPaterno: {}", apellidoPaterno);

			apellidoMaterno = root.path("apellidoMaterno").asText();
			logger.debug("apellidoMaterno: {}", apellidoMaterno);

			sexo = root.path("sexo").asText();
			logger.debug("sexo: {}", sexo);

			fechaNacimiento = root.path("fechaNacimiento").asText();
			logger.debug("fechaNacimiento: {}", fechaNacimiento);

			email = root.path("email").asText();
			logger.debug("email: {}", email);

			idDireccion = root.path("idDireccion").asInt();
			logger.debug("idDireccion: {}", idDireccion);

			telefono = root.path("telefono").asText();
			logger.debug("telefono: {}", telefono);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(numeroDocumento) || StringUtils.isBlank(nombres) || StringUtils.isBlank(apellidoPaterno)
				|| null == idTipoDocumento || idTipoDocumento == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		logger.debug("Buscando tipo de documento {}", idTipoDocumento);
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(idTipoDocumento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Tipo de documento {} encontrado", idTipoDocumento);

		Direccion direccion = null;
		if (idDireccion != 0) {
			direccion = direccionRepository.findById(idDireccion)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
		}
		logger.debug("Encontró dirección {} ? {}", idDireccion, (null != direccion));

		try {
			Persona persona = new Persona();
			persona.setNombres(nombres);
			persona.setApellidoPaterno(apellidoPaterno);
			persona.setApellidoMaterno(apellidoMaterno);
			persona.setTipoDocumento(tipoDocumento);
			persona.setNumeroDocumento(numeroDocumento);
			if (StringUtils.isNotBlank(sexo))
				persona.setSexo(sexo.charAt(0));
			if (StringUtils.isNotBlank(fechaNacimiento))
				persona.setFechaNacimiento(DateUtil.of(fechaNacimiento));
			persona.setEmail(email);
			persona.setDireccion(direccion);
			persona.setTelefono(telefono);

			responsePersona = personaRepository.save(persona);
			logger.debug("Persona guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responsePersona);
	}

	public ApiResponse update(String request) throws ApiException {
		Persona responsePersona;

		JsonNode root;
		Long id = null;
		String numeroDocumento = null;
		String nombres = null;
		String apellidoPaterno = null;
		String apellidoMaterno = null;
		String sexo = null;
		String fechaNacimiento = null;
		String email = null;
		Integer idTipoDocumento = null;
		Integer idDireccion = null;
		String telefono = null;

		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			idTipoDocumento = root.path("idTipoDocumento").asInt();
			logger.debug("idTipoDocumento: {}", idTipoDocumento);

			numeroDocumento = root.path("numeroDocumento").asText();
			logger.debug("numeroDocumento: {}", numeroDocumento);

			nombres = root.path("nombres").asText();
			logger.debug("nombres: {}", nombres);

			apellidoPaterno = root.path("apellidoPaterno").asText();
			logger.debug("apellidoPaterno: {}", apellidoPaterno);

			apellidoMaterno = root.path("apellidoMaterno").asText();
			logger.debug("apellidoMaterno: {}", apellidoMaterno);

			sexo = root.path("sexo").asText();
			logger.debug("sexo: {}", sexo);

			fechaNacimiento = root.path("fechaNacimiento").asText();
			logger.debug("fechaNacimiento: {}", fechaNacimiento);

			email = root.path("email").asText();
			logger.debug("email: {}", email);

			idDireccion = root.path("idDireccion").asInt();
			logger.debug("idDireccion: {}", idDireccion);

			telefono = root.path("telefono").asText();
			logger.debug("telefono: {}", telefono);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (id == 0 || null == id || StringUtils.isBlank(numeroDocumento) || StringUtils.isBlank(nombres)
				|| StringUtils.isBlank(apellidoPaterno) || null == idTipoDocumento || idTipoDocumento == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsPersona = personaRepository.existsById(id);
		logger.debug("Existe persona {}? {} ", id, existsPersona);
		if (!existsPersona) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		logger.debug("Buscando tipo de documento {}", idTipoDocumento);
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(idTipoDocumento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Tipo de documento {} encontrado", idTipoDocumento);

		Direccion direccion = null;
		if (idDireccion != 0) {
			direccion = direccionRepository.findById(idDireccion)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
		}
		logger.debug("Encontró dirección {} ? {}", idDireccion, (null != direccion));

		try {

			personaRepository.updatePersona(id, nombres, apellidoPaterno, apellidoMaterno, tipoDocumento,
					numeroDocumento, StringUtils.isBlank(sexo) || sexo.equals("null") ? null : sexo.charAt(0),
					StringUtils.isBlank(fechaNacimiento) || fechaNacimiento.equals("null") ? null
							: DateUtil.of(fechaNacimiento),
					email, telefono, direccion);
			logger.debug("Persona actualizada");

			logger.debug("Obteniendo Persona actualizada");
			responsePersona = personaRepository.findById(id).orElse(null);

			if (null == responsePersona) {
				throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
			}

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responsePersona);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Persona tmpPersona = personaRepository.findById(id).orElse(null);
		Long numch = personaRepository.finEliiminarChoferById(id);
		Long numc = personaRepository.finEliiminarClienteById(id);
		Long nump = personaRepository.finEliiminarProveedorById(id);
		Long numu = personaRepository.finEliiminarUsuarioById(id);
		Long numv = personaRepository.finEliiminarVendedorById(id);
		String dondeSeUsa = "persona";

		logger.debug("Persona: {}", tmpPersona);
		if (null != tmpPersona && numch == 0 && numc == 0 && nump == 0 && numu == 0 && numv == 0) {

			personaRepository.deleteById(id);
			logger.debug("Persona eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Persona " + tmpPersona.getNombres() + " eliminado");
		}
		if(numch >= 1 && numc>=1 && nump >=1 && numu >=1 && numv >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && numc >= 1 && nump >= 1 && numu >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
		}else if(numc >= 1 && nump >= 1 && numu >= 1 && numv >= 1 ){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && numc >= 1 && numu >= 1 && numv >= 1 ){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && numc >= 1 && nump >= 1 && numv >= 1 ){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && numc >= 1 && nump >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numc >= 1 && nump >= 1 && numu >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
		}else if(nump >= 1 && numu >= 1 && numv >= 1){
			dondeSeUsa = "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && numc >= 1 && numu >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
		}else if(numch >= 1 && numc >= 1 && numv >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numc >= 1 && nump >= 1 && numv >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(nump >= 1 && numu >= 1 && numch >= 1){
			dondeSeUsa = "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "chofer";
		}else if(numch >= 1 && nump >= 1 && numv >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numu >= 1 && numv >= 1 && numch >= 1){
			dondeSeUsa = "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
			dondeSeUsa = dondeSeUsa + ", " + "chofer";
		}else if(numc >= 1 && numu >= 1 && numv >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && numc >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "cliente";
		}else if(numc >= 1 && nump >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(nump >= 1 && numu >= 1){
			dondeSeUsa = "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
		}else if(numu >= 1 && numv >= 1){
			dondeSeUsa = "usuario";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numch >= 1 && nump >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numch >= 1 && numu >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
		}else if(numch >= 1 && numv >= 1){
			dondeSeUsa = "chofer";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numc >= 1 && numu >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "usuario";
		}else if(numc >= 1 && numv >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(nump >= 1 && numv >= 1){
			dondeSeUsa = "proveedor";
			dondeSeUsa = dondeSeUsa + ", " + "vendedor";
		}else if(numc >= 1){
			dondeSeUsa = "cliente";
		}else if(numch >= 1){
			dondeSeUsa = "chofer";
		}else if (nump >= 1){
			dondeSeUsa = "proveedor";
		}else if (numu >= 1){
			dondeSeUsa = "usuario";
		}else if(numv >= 1){
			dondeSeUsa = "vendedor";
		}

		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Persona " + tmpPersona.getNombres() + " esta siendo usado en " + dondeSeUsa + "." );
	}

}
