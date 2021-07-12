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
import pe.com.fabrica.aldesa.service.FamiliaService;

@RestController
@RequestMapping("/v1")
public class FamiliaRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private FamiliaService FamiliaService;

	@Autowired
	public FamiliaRestController(FamiliaService FamiliaService) {
		this.FamiliaService = FamiliaService;
	}

	@GetMapping("/familias/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = FamiliaService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/familias/slice/{page}/segmento/{IdSegmento}")
	public ResponseEntity<?> findAllSegmento(@PathVariable int page, @PathVariable int IdSegmento) {
		ApiResponse response = FamiliaService.findAllSegmento(page, IdSegmento);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/familias/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = FamiliaService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/familias")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = FamiliaService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/familias")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = FamiliaService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/familias/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = FamiliaService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/familias/codigo/{codigo}")
	public ResponseEntity<?> findByCodigo(@PathVariable String codigo) {
		ApiResponse response = FamiliaService.findByCodigo(codigo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/familias/descripcion/{descripcion}")
	public ResponseEntity<?> findByDescripcion(@PathVariable String descripcion) {
		ApiResponse response = FamiliaService.findByDescripcion(descripcion);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/familias/codigo/{codigo}/segmento/{IdSegmento}")
	public ResponseEntity<?> findByCodigoSegmento(@PathVariable String codigo, @PathVariable int IdSegmento) {
		ApiResponse response = FamiliaService.findByCodigoSegmento(codigo, IdSegmento);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/familias/descripcion/{descripcion}/segmento/{IdSegmento}")
	public ResponseEntity<?> findByDescripcionSegmento(@PathVariable String descripcion, @PathVariable int IdSegmento) {
		ApiResponse response = FamiliaService.findByDescripcionSegmento(descripcion, IdSegmento);
		return ResponseEntity.ok(response);
	}
}
