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
import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DireccionRepository;
import pe.com.fabrica.aldesa.repository.EmpresaRepository;

@Service
public class EmpresaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Empresa> empresasPage = empresaRepository.findAll(pageable);
		logger.debug("Empresa {} de: {}", pageNumber, empresasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), empresasPage.getContent(),
				Math.toIntExact(empresasPage.getTotalElements()));
	}

	public ApiResponse findByRazonSocialPag(String nombre,Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Empresa> empresasPage = empresaRepository.findByRazonSocialPag(nombre,pageable);
		logger.debug("Empresa {} de: {}", pageNumber, empresasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), empresasPage.getContent(),
				Math.toIntExact(empresasPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Empresa empresa = empresaRepository.findById(id).orElse(null);
		logger.debug("Empresa: {}", empresa);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), empresa);
	}

	public ApiResponse findByRuc(String ruc) {
		Empresa empresa = empresaRepository.findByRuc(ruc);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), empresa);
	}

	public ApiResponse findByRazonSocial(String razonSocial) {
		List<Empresa> empresas = empresaRepository.findByRazonSocial(razonSocial);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), empresas, empresas.size());
	}

	public ApiResponse save(String request) throws ApiException {
		Empresa responseEmpresa;

		JsonNode root;
		String ruc = null;
		String razonSocial = null;
		String nombreComercial = null;
		Integer idDireccion = null;
		String contacto = null;
		String telefono = null;
		String correo = null;

		try {
			root = new ObjectMapper().readTree(request);

			ruc = root.path("ruc").asText();
			logger.debug("ruc: {}", ruc);

			razonSocial = root.path("razonSocial").asText();
			logger.debug("razonSocial: {}", razonSocial);

			idDireccion = root.path("idDireccion").asInt();
			logger.debug("idDireccion: {}", idDireccion);

			nombreComercial = root.path("nombreComercial").asText();
			logger.debug("nombreComercial: {}", nombreComercial);

			contacto = root.path("contacto").asText();
			logger.debug("contacto: {}", contacto);

			telefono = root.path("telefono").asText();
			logger.debug("telefono: {}", telefono);

			correo = root.path("correo").asText();
			logger.debug("correo: {}", correo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (StringUtils.isBlank(ruc) || StringUtils.isBlank(razonSocial)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Direccion direccion = null;
		if (idDireccion != 0) {
			direccion = direccionRepository.findById(idDireccion)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
		}
		logger.debug("Encontró dirección {} ? {}", idDireccion, (null != direccion));

		try {
			Empresa empresa = new Empresa();
			empresa.setRuc(ruc);
			empresa.setRazonSocial(razonSocial);
			empresa.setDireccion(direccion);
			empresa.setNombreComercial(nombreComercial);
			empresa.setContacto(contacto);
			empresa.setTelefono(telefono);
			empresa.setCorreo(correo);

			responseEmpresa = empresaRepository.save(empresa);
			logger.debug("Empresa guardada");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseEmpresa);
	}

	public ApiResponse update(String request) throws ApiException {
		Empresa responseEmpresa;

		JsonNode root;
		Long id = null;
		String ruc = null;
		String razonSocial = null;
		String nombreComercial = null;
		Integer idDireccion = null;
		String contacto = null;
		String telefono = null;
		String correo = null;

		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			ruc = root.path("ruc").asText();
			logger.debug("ruc: {}", ruc);

			razonSocial = root.path("razonSocial").asText();
			logger.debug("razonSocial: {}", razonSocial);

			idDireccion = root.path("idDireccion").asInt();
			logger.debug("idDireccion: {}", idDireccion);

			nombreComercial = root.path("nombreComercial").asText();
			logger.debug("nombreComercial: {}", nombreComercial);

			contacto = root.path("contacto").asText();
			logger.debug("contacto: {}", contacto);

			telefono = root.path("telefono").asText();
			logger.debug("telefono: {}", telefono);

			correo = root.path("correo").asText();
			logger.debug("correo: {}", correo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || StringUtils.isBlank(ruc) || StringUtils.isBlank(razonSocial)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existsEmpresa = empresaRepository.existsById(id);
		logger.debug("Existe empresa {}? {}", id, existsEmpresa);
		if (!existsEmpresa) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		Direccion direccion = null;
		if (idDireccion != 0) {
			direccion = direccionRepository.findById(idDireccion)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
		}
		logger.debug("Encontró dirección {} ? {}", idDireccion, (null != direccion));

		try {

			empresaRepository.updateEmpresa(id, ruc, razonSocial, nombreComercial, direccion, contacto, telefono,
					correo);
			logger.debug("Empresa actualizada");

			responseEmpresa = empresaRepository.findById(id)
					.orElseThrow(() -> new ApiException(ApiError.EMPRESA_NOT_FOUND.getCode(),
							ApiError.EMPRESA_NOT_FOUND.getMessage()));
			logger.debug("Empresa encontrada: {}", responseEmpresa);

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseEmpresa);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Empresa tmpEmpresa = empresaRepository.findById(id).orElse(null);
		logger.debug("Empresa: {}", tmpEmpresa);
		Long numc = empresaRepository.findEliminarClienteById(id);
		Long numa = empresaRepository.findEliminarAgencuiaAduanasById(id);
		Long numd = empresaRepository.findEliminarDepositoTemporalById(id);
		Long nump = empresaRepository.findEliminarProveedorById(id);
		String dondeSeUsa = "empresa";

		if (null != tmpEmpresa && numc == 0 && numa == 0 && numd == 0 && nump==0) {
			empresaRepository.deleteById(id);
			logger.debug("Empresa eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Empresa " + tmpEmpresa.getRazonSocial()+ " eliminado");
		}

		if(numc >= 1 && numa>=1 && numd >=1 && nump >=1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "agencia aduanas";
			dondeSeUsa = dondeSeUsa + ", " + "diposito temporal";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numc >= 1 && numa >= 1 && numd >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "agencia aduanas";
			dondeSeUsa = dondeSeUsa + ", " + "diposito temporal";
		}else if(numa >= 1 && numd >= 1 && nump >= 1){
			dondeSeUsa = "agencia aduana";
			dondeSeUsa = dondeSeUsa + ", " + "deposito temporal";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numc >= 1 && numa >= 1 && nump >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "agencia aduanas";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numc >= 1 && numd >= 1 && nump >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "deposito temporal";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numc >= 1 && numa >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "agencia aduanas";
		}else if(numc >= 1 && numd >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "deposito temoral";
		}else if(numc >= 1 && nump >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numa >= 1 && numd >= 1){
			dondeSeUsa = "agencia aduana";
			dondeSeUsa = dondeSeUsa + ", " + "deposito temporal";
		}else if(numa >= 1 && nump >= 1){
			dondeSeUsa = "agencia aduana";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numd >= 1 && nump >= 1){
			dondeSeUsa = "deposito temporal";
			dondeSeUsa = dondeSeUsa + ", " + "proveedor";
		}else if(numc >= 1 && numa >= 1){
			dondeSeUsa = "cliente";
			dondeSeUsa = dondeSeUsa + ", " + "agencia aduanas";
		}else if(numc >= 1 ){
			dondeSeUsa = "cliente";
		}else if(numa >= 1){
			dondeSeUsa = "agencia aduana";
		}else if(numd >= 1){
			dondeSeUsa = "deposito temporal";
		}else if(nump >= 1){
			dondeSeUsa = "proveedor";
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), "Empresa " + tmpEmpresa.getRazonSocial() + " esta siendo usado en " + dondeSeUsa + ".");
	}

}
