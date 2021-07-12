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
import pe.com.fabrica.aldesa.service.UbicacionService;

@RestController
@RequestMapping("/v1")
public class UbicacionRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private UbicacionService ubicacionService;

	@Autowired
	public UbicacionRestController(UbicacionService ubicacionService) {
		this.ubicacionService = ubicacionService;
	}

	@GetMapping("/ubicaciones")
	public ResponseEntity<?> findAll() {
		ApiResponse response = ubicacionService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/ubicaciones/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = ubicacionService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/ubicaciones/area/{id}")
	public ResponseEntity<?> findByArea(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = ubicacionService.findByArea(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/ubicaciones")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ubicacionService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/ubicaciones")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ubicacionService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/ubicaciones/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = ubicacionService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
