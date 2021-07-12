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
import pe.com.fabrica.aldesa.service.DistritoService;

@RestController
@RequestMapping("/v1")
public class DistritoRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DistritoService distritoService;

	@Autowired
	public DistritoRestController(DistritoService distritoService) {
		this.distritoService = distritoService;
	}

	@GetMapping("/distritos")
	public ResponseEntity<?> findAll() {
		ApiResponse response = distritoService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/distritos/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = distritoService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/distritos/provincias/{id_provincia}")
	public ResponseEntity<?> findByProvincia(@PathVariable Integer id_provincia) {
		ApiResponse response = distritoService.findByProvincia(id_provincia);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/distritos")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = distritoService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/distritos")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = distritoService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/distritos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = distritoService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}
}
