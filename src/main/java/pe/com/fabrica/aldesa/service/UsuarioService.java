package pe.com.fabrica.aldesa.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.Rol;
import pe.com.fabrica.aldesa.beans.TipoDocumento;
import pe.com.fabrica.aldesa.beans.Usuario;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.DireccionRepository;
import pe.com.fabrica.aldesa.repository.PersonaRepository;
import pe.com.fabrica.aldesa.repository.RolRepository;
import pe.com.fabrica.aldesa.repository.TipoDocumentoRepository;
import pe.com.fabrica.aldesa.repository.UsuarioRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class UsuarioService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private DireccionRepository direccionRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEnconder;

	// @Autowired
	// private PersonaRepository personaRepository;

	public ApiResponse findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		int total = usuarios.size();
		logger.debug("Total Usuarios: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), usuarios, total);
	}

	public ApiResponse findById(Long id) {
		Usuario tmpUser = usuarioRepository.findById(id).orElse(null);
		logger.debug("Usuario found: {}", tmpUser);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpUser);
	}

	public ApiResponse save(String request) throws ApiException {
		Usuario responseUser;

		JsonNode root;
		Long idPersona = null;
		String username = null;
		String password = null;
		Integer idRol = null;
		String imagen = null;
		String usuarioCreador = null;
		String usuarioModificador = null;
		Long fechaCreacion = null;
		Long fechaModificacion = null;
		String fechaCre = null;
		String fechaMod = null;
		String activo = null;

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		try {
			root = new ObjectMapper().readTree(request);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

			username = root.path("username").asText();
			logger.debug("username: {}", username);

			password = root.path("password").asText();
			logger.debug("password: {}", password);

			password = passwordEnconder.encode(password);

			idRol = root.path("idRol").asInt();
			logger.debug("idRol: {}", idRol);

			imagen = root.path("imagen").asText();
			logger.debug("imagen: {}", imagen);

			usuarioCreador = root.path("usuarioCreador").asText();
			logger.debug("usuarioCreador: {}", usuarioCreador);
			usuarioModificador = root.path("usuarioModificador").asText();
			logger.debug("usuarioModificador: {}", usuarioModificador);

			fechaCreacion = root.path("fechaCreacion").asLong();
			logger.debug("fechaCreacion: {}", fechaCreacion);

			fechaCre = simpleDateFormat.format(new Date(fechaCreacion));

			fechaModificacion = root.path("fechaModificacion").asLong();
			logger.debug("fechaModificacion: {}", fechaModificacion);

			fechaMod = simpleDateFormat.format(new Date(fechaCreacion));

			activo = root.path("activo").asText();
			logger.debug("activo: {}", activo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		// Rol rol = rolRepository.findById(idRol)
		// 		.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage()));
		// logger.debug("Rol {} encontrado", idRol);

		try {
			// Usuario usuario = new Usuario();
			// usuario.setUsername(username);
			// usuario.setPassword(passwordEnconder.encode(password));
			// usuario.setRol(rol);
			// usuario.setImagen(imagen);
			// usuario.setUsuarioCreador(usuarioCreador);
			// usuario.setUsuarioModificador(usuarioModificador);
			// usuario.setFechaCreacion(DateUtil.of(fechaCre));
			// usuario.setFechaModificacion(DateUtil.of(fechaMod));
			// usuario.setActivo(activo);
			 usuarioRepository.createUser(idPersona, idRol, username, password, activo, imagen,usuarioCreador,usuarioModificador,fechaCre,fechaMod);
			// responseUser = usuarioRepository.save(usuario);
			logger.debug("Usuario guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		// return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUser);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage());
	}

	public ApiResponse update(String request) throws ApiException {
		Usuario responseUser;

		JsonNode root;
		String username = null;
		Integer idRol = null;
		Long idPersona = null;
		String numeroDocumento = null;
		String nombres = null;
		String apellidoPaterno = null;
		String apellidoMaterno = null;
		String sexo = null;
		String fechaNacimiento = null;
		String email = null;
		String imagen = null;
		Integer idTipoDocumento = null;
		Integer idDireccion = null;
		String activo = null;

		try {
			root = new ObjectMapper().readTree(request);

			username = root.path("username").asText();
			logger.debug("username: {}", username);

			idPersona = root.path("idPersona").asLong();
			logger.debug("idPersona: {}", idPersona);

			idRol = root.path("idRol").asInt();
			logger.debug("idRol: {}", idRol);

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

			imagen = root.path("imagen").asText();
			logger.debug("imagen: {}", imagen);

			idDireccion = root.path("idDireccion").asInt();
			logger.debug("idDireccion: {}", idDireccion);

			activo = root.path("activo").asText();
			logger.debug("activo: {}", activo);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		// if (StringUtils.isBlank(username) || idRol == 0 || null == idRol ||
		// StringUtils.isBlank(numeroDocumento) || StringUtils.isBlank(nombres) ||
		// StringUtils.isBlank(activo) || "null".equals(activo)
		// || StringUtils.isBlank(apellidoPaterno) || idTipoDocumento == 0 || null ==
		// idTipoDocumento) {
		// throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
		// ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		// }

		logger.debug("Buscando tipo de documento {}", idTipoDocumento);
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(idTipoDocumento)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Tipo de documento {} encontrado", idTipoDocumento);

		logger.debug("Buscando rol {}", idRol);
		Rol rol = rolRepository.findById(idRol)
				.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
						ApiError.RESOURCE_NOT_FOUND.getMessage()));
		logger.debug("Rol {} encontrado", idRol);

		Direccion direccion = null;
		if (0 != idDireccion) {
			direccion = direccionRepository.findById(idDireccion)
					.orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(),
							ApiError.RESOURCE_NOT_FOUND.getMessage()));
		}
		logger.debug("Encontró dirección {} ? {}", idDireccion, (null != direccion));

		boolean existsUsuario = usuarioRepository.existsById(idPersona);
		logger.debug("Usuario existe? {}", existsUsuario);
		if (!existsUsuario) {
			throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
		}

		try {
			usuarioRepository.updateUsuario(idPersona, username, rol, nombres, apellidoPaterno, apellidoMaterno,
					tipoDocumento, numeroDocumento,
					StringUtils.isBlank(sexo) || sexo.equals("null") ? null : sexo.charAt(0),
					StringUtils.isBlank(fechaNacimiento) || fechaNacimiento.equals("null") ? null
							: DateUtil.of(fechaNacimiento),
					email, imagen, direccion, activo);
			logger.debug("Usuario actualizado");

			logger.debug("Obteniendo usuario actualizado");
			responseUser = usuarioRepository.findById(idPersona).orElse(null);

			if (null == responseUser) {
				throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
			}

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUser);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Usuario tmpUsuario = usuarioRepository.findById(id).orElse(null);
		logger.debug("Usuario: {}", tmpUsuario);
		if (null != tmpUsuario) {
			usuarioRepository.deleteById(id);
			logger.debug("Usuario eliminado");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Usuario " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

	public ApiResponse updatePassword(String request) throws ApiException {
		Usuario responseUser;

		JsonNode root;
		Long idPersona = null;
		String password = null;

		try {
			root = new ObjectMapper().readTree(request);

			idPersona = root.path("idPersona").asLong();
			password = root.path("password").asText();

			if (idPersona == 0 || idPersona == null || StringUtils.isBlank(password)) {
				throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(),
						ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
			}

			usuarioRepository.updatePassword(idPersona, passwordEnconder.encode(password));
			logger.debug("Contraseña actualizada");

			logger.debug("Obteniendo usuario actualizado");
			responseUser = usuarioRepository.findById(idPersona).orElse(null);
			logger.debug("Usuario: {}", responseUser);
			if (null == responseUser) {
				throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
			}

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseUser);
	}
}
