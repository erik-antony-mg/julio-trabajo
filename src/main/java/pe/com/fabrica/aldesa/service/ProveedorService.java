package pe.com.fabrica.aldesa.service;

import java.util.List;
import java.util.Optional;

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
import pe.com.fabrica.aldesa.constant.PersonType;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.Proveedor;
import pe.com.fabrica.aldesa.beans.TipoPersona;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.EmpresaRepository;
import pe.com.fabrica.aldesa.repository.PersonaRepository;
import pe.com.fabrica.aldesa.repository.ProveedorRepository;
import pe.com.fabrica.aldesa.repository.TipoPersonaRepository;

@Service
public class ProveedorService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProveedorRepository proveedorRepository;

	@Autowired
	private TipoPersonaRepository tipoPersonaRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Proveedor> proveedorPage = proveedorRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, proveedorPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), proveedorPage.getContent(),
				Math.toIntExact(proveedorPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Proveedor tmpProveedor = proveedorRepository.findById(id).orElse(null);
		logger.debug("Proveedor: {}", tmpProveedor);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpProveedor);
	}

	public ApiResponse findByNombre(String nombre) {
		List<Proveedor> proveedores = proveedorRepository.findByNombre(nombre);
		logger.debug("Proveedor by Nombre: {}", proveedores);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), proveedores,
				proveedores.size());
	}

	public ApiResponse findByTipoPersona(Integer tipoPersona, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Proveedor> proveedores = proveedorRepository.findByTipoPersona(tipoPersona, pageable);
		logger.debug("Página {} de: {}", pageNumber, proveedores.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), proveedores.getContent(),
				Math.toIntExact(proveedores.getTotalElements()));
	}

	public ApiResponse findByNumeroDocumento(String numeroDocumento) {
		Optional<Proveedor> optPro = proveedorRepository.findByNumeroDocumento(numeroDocumento);
		logger.debug("Proveedor by Numero de documento: {}", optPro);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optPro);
	}

	public ApiResponse findByRazonSocial(String razonSocial) {
		List<Proveedor> Proveedors = proveedorRepository.findByRazonSocial(razonSocial);
		logger.debug("Proveedor by Razon Social: {}", Proveedors);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), Proveedors, Proveedors.size());
	}

	public ApiResponse findByRuc(String ruc) {
		Optional<Proveedor> optCli = proveedorRepository.findByRuc(ruc);
		logger.debug("Proveedor by RUC: {}", optCli);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optCli);
	}

	public ApiResponse save(String request) throws ApiException {
		Proveedor responseProveedor;

		JsonNode root;
		Integer idTipoPersona = null;
		Long idPersona = null;
		Long idEmpresa = null;
		try {
			root = new ObjectMapper().readTree(request);

			idTipoPersona = root.path("idTipoPersona").asInt();
			logger.debug("idTipoPersona: {}", idTipoPersona);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == idTipoPersona || idTipoPersona == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		logger.debug("Buscando tipo persona {}", idTipoPersona);
		TipoPersona tipoPersona = tipoPersonaRepository.findById(idTipoPersona)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Tipo persona {} encontrado", idTipoPersona);

		Persona persona = null;
		if (PersonType.NATURAL.equals(idTipoPersona)) {
			logger.debug("Buscando Persona {}", idPersona);
			persona = personaRepository.findById(idPersona)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
			logger.debug("Persona {} encontrado", idPersona);

		}

		// Valida que no se vuelva a repetir la combinación tipoPersona con Persona en
		// la tabla Proveedor
		if (null != tipoPersona && null != persona) {
			boolean exists = proveedorRepository.existsProveedor(tipoPersona, persona);
			logger.debug("Exists Proveedor con tipoPersona: {} y persona: {}? {}", tipoPersona.getIdTipoPersona(),
					persona.getIdPersona(), exists);
			if (exists)
				throw new ApiException(ApiError.ALREADY_EXISTS.getCode(), ApiError.ALREADY_EXISTS.getMessage());
		}

		Empresa empresa = null;
		if (PersonType.LEGAL.equals(idTipoPersona)) {
			logger.debug("Buscando empresa {}", idEmpresa);
			empresa = empresaRepository.findById(idEmpresa)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
			logger.debug("Empresa {} encontrado", idEmpresa);
		}

		// Valida que no se vuelva a repetir la combinación tipoPersona con Empresa en
		// la tabla Proveedor
		if (null != tipoPersona && null != empresa) {
			boolean exists = proveedorRepository.existsProveedor(tipoPersona, empresa);
			logger.debug("Exists Proveedor con tipoPersona: {} y empresa: {}? {}", tipoPersona.getIdTipoPersona(),
					empresa.getIdEmpresa(), exists);
			if (exists)
				throw new ApiException(ApiError.ALREADY_EXISTS.getCode(), ApiError.ALREADY_EXISTS.getMessage());
		}

		try {
			Proveedor Proveedor = new Proveedor();
			Proveedor.setTipoPersona(tipoPersona);
			Proveedor.setPersona(persona);
			Proveedor.setEmpresa(empresa);

			responseProveedor = proveedorRepository.save(Proveedor);
			logger.debug("Proveedor guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseProveedor);
	}

	public ApiResponse update(String request) throws ApiException {
		Proveedor responseProveedor;

		JsonNode root;
		Long id = null;
		Integer idTipoPersona = null;
		Long idPersona = null;
		Long idEmpresa = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			idTipoPersona = root.path("idTipoPersona").asInt();
			logger.debug("idTipoPersona: {}", idTipoPersona);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idTipoPersona || idTipoPersona == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existProveedor = proveedorRepository.existsById(id);
		logger.debug("Existe Proveedor? {} {}", id, existProveedor);
		if (!existProveedor) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		logger.debug("Buscando tipo persona {}", idTipoPersona);
		TipoPersona tipoPersona = tipoPersonaRepository.findById(idTipoPersona)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Tipo persona {} encontrado", idTipoPersona);

		Persona persona = null;
		if (PersonType.NATURAL.equals(idTipoPersona)) {
			logger.debug("Buscando Persona {}", idPersona);
			persona = personaRepository.findById(idPersona)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
			logger.debug("Persona {} encontrado", idPersona);
		}

		Empresa empresa = null;
		if (PersonType.LEGAL.equals(idTipoPersona)) {
			logger.debug("Buscando empresa {}", idEmpresa);
			empresa = empresaRepository.findById(idEmpresa)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
			logger.debug("Empresa {} encontrado", idEmpresa);
		}

		try {
			Proveedor Proveedor = new Proveedor();
			Proveedor.setIdProveedor(id);
			Proveedor.setTipoPersona(tipoPersona);
			Proveedor.setPersona(persona);
			Proveedor.setEmpresa(empresa);

			responseProveedor = proveedorRepository.save(Proveedor);
			logger.debug("Proveedor guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseProveedor);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Proveedor tmpProveedor = proveedorRepository.findById(id).orElse(null);
		logger.debug("Proveedor: {}", tmpProveedor);
		if (null != tmpProveedor) {
			proveedorRepository.deleteById(id);
			logger.debug("Proveedor eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Proveedor " + tmpProveedor.getEmpresa().getRazonSocial() + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

}
