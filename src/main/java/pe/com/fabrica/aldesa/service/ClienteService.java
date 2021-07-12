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
import pe.com.fabrica.aldesa.beans.Cliente;
import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.TipoPersona;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.ClienteRepository;
import pe.com.fabrica.aldesa.repository.EmpresaRepository;
import pe.com.fabrica.aldesa.repository.PersonaRepository;
import pe.com.fabrica.aldesa.repository.TipoPersonaRepository;

@Service
public class ClienteService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TipoPersonaRepository tipoPersonaRepository;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Cliente> clientesPage = clienteRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, clientesPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientesPage.getContent(),
				Math.toIntExact(clientesPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Cliente tmpCliente = clienteRepository.findById(id).orElse(null);
		logger.debug("Cliente: {}", tmpCliente);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpCliente);
	}

	public ApiResponse findByNombre(String nombre) {
		List<Cliente> clientes = clienteRepository.findByNombre(nombre);
		logger.debug("Cliente by Nombre: {}", clientes);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientes, clientes.size());
	}

	public ApiResponse findByNombrePag(String nombre, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Cliente> clientesPage = clienteRepository.findByNombrePag(nombre, pageable);
		logger.debug("Página {} de: {}", pageNumber, clientesPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientesPage.getContent(),
				Math.toIntExact(clientesPage.getTotalElements()));
	}

	public ApiResponse findByCodigo(String codigo) {
		List<Cliente> clientes = clienteRepository.findByCodigo(codigo);
		logger.debug("Cliente by Nombre: {}", clientes);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientes, clientes.size());
	}

	public ApiResponse findByTipoPersona(Integer tipoPersona, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Cliente> clientes = clienteRepository.findByTipoPersona(tipoPersona, pageable);
		logger.debug("Página {} de: {}", pageNumber, clientes.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientes.getContent(),
				Math.toIntExact(clientes.getTotalElements()));
	}

	public ApiResponse findByNumeroDocumento(String numeroDocumento) {
		Optional<Cliente> optCli = clienteRepository.findByNumeroDocumento(numeroDocumento);
		logger.debug("Cliente by Numero de documento: {}", optCli);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optCli);
	}

	public ApiResponse findByRazonSocial(String razonSocial) {
		List<Cliente> clientes = clienteRepository.findByRazonSocial(razonSocial);
		logger.debug("Cliente by Razon Social: {}", clientes);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientes, clientes.size());
	}

	public ApiResponse findByRazonSocialPag(String nombre, Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Cliente> clientesPage = clienteRepository.findByRazonSocialPag(nombre, pageable);
		logger.debug("Página {} de: {}", pageNumber, clientesPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), clientesPage.getContent(),
				Math.toIntExact(clientesPage.getTotalElements()));
	}

	public ApiResponse findByRuc(String ruc) {
		Optional<Cliente> optCli = clienteRepository.findByRuc(ruc);
		logger.debug("Cliente by RUC: {}", optCli);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), optCli);
	}

	public ApiResponse save(String request) throws ApiException {
		Cliente responseCliente;

		JsonNode root;
		Integer idTipoPersona = null;
		String codigo = null;
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

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

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
		// la tabla cliente
		if (null != tipoPersona && null != persona) {
			boolean exists = clienteRepository.existsCliente(tipoPersona, persona);
			logger.debug("Exists cliente con tipoPersona: {} y persona: {}? {}", tipoPersona.getIdTipoPersona(),
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
		// la tabla cliente
		if (null != tipoPersona && null != empresa) {
			boolean exists = clienteRepository.existsCliente(tipoPersona, empresa);
			logger.debug("Exists cliente con tipoPersona: {} y empresa: {}? {}", tipoPersona.getIdTipoPersona(),
					empresa.getIdEmpresa(), exists);
			if (exists)
				throw new ApiException(ApiError.ALREADY_EXISTS.getCode(), ApiError.ALREADY_EXISTS.getMessage());
		}

		try {
			Cliente cliente = new Cliente();
			cliente.setTipoPersona(tipoPersona);
			cliente.setPersona(persona);
			cliente.setEmpresa(empresa);
			cliente.setCodigo(codigo);
			responseCliente = clienteRepository.save(cliente);
			logger.debug("Cliente guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseCliente);
	}

	public ApiResponse update(String request) throws ApiException {
		Cliente responseCliente;

		JsonNode root;
		Long id = null;
		Integer idTipoPersona = null;
		Long idPersona = null;
		Long idEmpresa = null;
		String codigo = null;

		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", id);

			idTipoPersona = root.path("idTipoPersona").asInt();
			logger.debug("idTipoPersona: {}", idTipoPersona);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

			idEmpresa = root.path("idEmpresa").asLong();
			logger.debug("idEmpresa: {}", idEmpresa);

			codigo = root.path("codigo").asText();
			logger.debug("codigo: {}", codigo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (null == id || id == 0 || null == idTipoPersona || idTipoPersona == 0) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
					ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		boolean existCliente = clienteRepository.existsById(id);
		logger.debug("Existe cliente? {} {}", id, existCliente);
		if (!existCliente) {
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
			Cliente cliente = new Cliente();
			cliente.setIdCliente(id);
			cliente.setTipoPersona(tipoPersona);
			cliente.setPersona(persona);
			cliente.setEmpresa(empresa);
			cliente.setCodigo(codigo);

			responseCliente = clienteRepository.save(cliente);
			logger.debug("Cliente guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseCliente);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Cliente tmpCliente = clienteRepository.findById(id).orElse(null);
		logger.debug("Cliente: {}", tmpCliente);

		Long numc = clienteRepository.findEliminarComprobanteById(id);
		Long numt = clienteRepository.findEliminarTarjetaById(id);
		Long numct = clienteRepository.findEliminarCotizacionById(id);
		String dondeSeUsa = "cliente";

		if (null != tmpCliente && numc==0 && numt==0 && numct==0) {
			clienteRepository.deleteById(id);
			logger.debug("Cliente eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), "Cliente " + tmpCliente.getEmpresa().getRazonSocial() + " eliminado");
		}

		if(numc >= 1 && numt >= 1 && numct>=1){
			dondeSeUsa = "comprobante";
			dondeSeUsa = dondeSeUsa + ", " + "tarjeta";
			dondeSeUsa = dondeSeUsa + ", " + "cotización";
		}else if(numt >= 1 && numc>=1){
			dondeSeUsa = "comprobante";
			dondeSeUsa = dondeSeUsa + ", " + "tajera";
		}else if (numt >= 1 && numct >= 1){
			dondeSeUsa = "tarjeta";
			dondeSeUsa = dondeSeUsa + ", " + "cotización";
		}else if(numc >= 1 && numct >=1){
			dondeSeUsa = "comprobante";
			dondeSeUsa = dondeSeUsa + ", " + "cotización";
		}else if(numc >=1){
			dondeSeUsa = "comprobante";
		}else if(numt >= 1){
			dondeSeUsa = "tarjeta";
		}else if(numct >= 1){
			dondeSeUsa = "cotización";
		}

		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
				"Cliente " + tmpCliente.getEmpresa().getRazonSocial() + " esta siendo usado en " + dondeSeUsa + ".");
	}

}
