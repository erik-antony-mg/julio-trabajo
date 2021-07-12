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
import pe.com.fabrica.aldesa.service.ClaseService;

@RestController
@RequestMapping("/v1")
public class ClaseRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ClaseService ClaseService;

	@Autowired
	public ClaseRestController(ClaseService ClaseService) {
		this.ClaseService = ClaseService;
	}

	@GetMapping("/clases/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = ClaseService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clases/slice/{page}/familia/{idFamilia}")
	public ResponseEntity<?> findAllSegmento(@PathVariable int page, @PathVariable int idFamilia) {
		ApiResponse response = ClaseService.findAllFamilia(page, idFamilia);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clases/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = ClaseService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/clases")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ClaseService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/clases")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ClaseService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/clases/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = ClaseService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clases/codigo/{codigo}")
	public ResponseEntity<?> findByCodigo(@PathVariable String codigo) {
		ApiResponse response = ClaseService.findByCodigo(codigo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clases/descripcion/{descripcion}")
	public ResponseEntity<?> findByDescripcion(@PathVariable String descripcion) {
		ApiResponse response = ClaseService.findByDescripcion(descripcion);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clases/codigo/{codigo}/segmento/{IdSegmento}")
	public ResponseEntity<?> findByCodigoFamilia(@PathVariable String codigo, @PathVariable int IdSegmento) {
		ApiResponse response = ClaseService.findByCodigoFamilia(codigo, IdSegmento);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clases/descripcion/{descripcion}/segmento/{IdSegmento}")
	public ResponseEntity<?> findByDescripcionFamilia(@PathVariable String descripcion, @PathVariable int IdSegmento) {
		ApiResponse response = ClaseService.findByDescripcionFamilia(descripcion, IdSegmento);
		return ResponseEntity.ok(response);
	}
}	
