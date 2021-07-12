package pe.com.fabrica.aldesa.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import pe.com.fabrica.aldesa.service.DamService;

@RestController
@RequestMapping("/v1")
public class DamRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DamService damService;

	@Autowired
	public DamRestController(DamService damService) {
		this.damService = damService;
	}

	@GetMapping("/dams")
	public ResponseEntity<?> findAll() {
		ApiResponse response = damService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/dams/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = damService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/dams")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = damService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/dams")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = damService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

}
