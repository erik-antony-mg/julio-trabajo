package pe.com.fabrica.aldesa.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.dto.ErrorResponse;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.service.UnspscService;

@RestController
@RequestMapping("/v1")
public class UnspscRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private UnspscService UnspscService;

	@Autowired
	public UnspscRestController(UnspscService UnspscService) {
		this.UnspscService = UnspscService;
	}

	@GetMapping("/codigoSunat/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = UnspscService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/codigoSunat/slice/{page}/clases/{idClases}")
	public ResponseEntity<?> findAllClases(@PathVariable int page, @PathVariable int idClases) {
		ApiResponse response = UnspscService.findAllClases(page, idClases);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/codigoSunat/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = UnspscService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/codigoSunat")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = UnspscService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/codigoSunat")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = UnspscService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/codigoSunat/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = UnspscService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/codigoSunat/codigo/{codigo}")
	public ResponseEntity<?> findByCodigo(@PathVariable String codigo) {
		ApiResponse response = UnspscService.findByCodigo(codigo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/codigoSunat/descripcion/{descripcion}")
	public ResponseEntity<?> findByDescripcion(@PathVariable String descripcion) {
		ApiResponse response = UnspscService.findByDescripcion(descripcion);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/codigoSunat/codigo/{codigo}/clase/{IdClase}")
	public ResponseEntity<?> findByCodigoFamilia(@PathVariable String codigo, @PathVariable int IdClase) {
		ApiResponse response = UnspscService.findByCodigoClase(codigo, IdClase);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/codigoSunat/descripcion/{descripcion}/clase/{IdClase}")
	public ResponseEntity<?> findByDescripcionFamilia(@PathVariable String descripcion, @PathVariable int IdClase) {
		ApiResponse response = UnspscService.findByDescripcionClase(descripcion, IdClase);
		return ResponseEntity.ok(response);
	}
}
