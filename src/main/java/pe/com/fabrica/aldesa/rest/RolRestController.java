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
import pe.com.fabrica.aldesa.dto.RolContext;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.service.RolService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/v1")
public class RolRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RolService rolService;

	@Autowired
	public RolRestController(RolService rolService) {
		this.rolService = rolService;
	}

	@GetMapping("/roles")
	public ResponseEntity<?> findAll(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "1") int page) {
		ApiResponse response;
		if (name == null) {
			response = rolService.findAll(page);
		} else {
			response = rolService.findByNameContaining(name, page);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = rolService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/roles")
	public ResponseEntity<?> create(@RequestBody RolContext rolContext ) {
		ApiResponse response;
		try {
			response = rolService.save(rolContext);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/roles/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RolContext rolContext) {
		ApiResponse response;
		try {
			response = rolService.update(id, rolContext);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = rolService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
