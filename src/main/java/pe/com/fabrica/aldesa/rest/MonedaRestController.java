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
import pe.com.fabrica.aldesa.service.MonedaService;

@RestController
@RequestMapping("/v1")
public class MonedaRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MonedaService monedaService;

	@Autowired
	public MonedaRestController(MonedaService monedaService) {
		this.monedaService = monedaService;
	}

	@GetMapping("/monedas")
	public ResponseEntity<?> findAll() {
		ApiResponse response = monedaService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/monedas/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = monedaService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/monedas")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = monedaService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/monedas")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = monedaService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/monedas/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = monedaService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
