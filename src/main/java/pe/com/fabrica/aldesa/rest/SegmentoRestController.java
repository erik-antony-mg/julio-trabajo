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
import pe.com.fabrica.aldesa.service.SegmentoService;

@RestController
@RequestMapping("/v1")
public class SegmentoRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SegmentoService SegmentoService;

	@Autowired
	public SegmentoRestController(SegmentoService SegmentoService) {
		this.SegmentoService = SegmentoService;
	}

	@GetMapping("/segmentos/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = SegmentoService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/segmentos/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = SegmentoService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/segmentos")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = SegmentoService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/segmentos")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = SegmentoService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/segmentos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = SegmentoService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/segmentos/codigo/{codigo}")
	public ResponseEntity<?> findByCodigo(@PathVariable String codigo) {
		ApiResponse response = SegmentoService.findByCodigo(codigo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/segmentos/descripcion/{descripcion}")
	public ResponseEntity<?> findByDescripcion(@PathVariable String descripcion) {
		ApiResponse response = SegmentoService.findByDescripcion(descripcion);
		return ResponseEntity.ok(response);
	}
}	
