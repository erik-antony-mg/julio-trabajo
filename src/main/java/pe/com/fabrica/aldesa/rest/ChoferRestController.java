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
import pe.com.fabrica.aldesa.service.ChoferService;

@RestController
@RequestMapping("/v1")
public class ChoferRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ChoferService choferService;

	@Autowired
	public ChoferRestController(ChoferService camionService) {
		this.choferService = camionService;
	}

	@GetMapping("/choferes/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = choferService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/choferes/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = choferService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/choferes")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = choferService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/choferes")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = choferService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/choferes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = choferService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}


	@GetMapping("/choferes/nombrecompleto/{nombrecompleto}")
	public ResponseEntity<?> findByNombreCompleto(@PathVariable String nombrecompleto) {
		ApiResponse response =choferService.findByNombreCompletoPersona(nombrecompleto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/choferes/documento/{documento}")
	public ResponseEntity<?> findByDocumento(@PathVariable String documento) {
		ApiResponse response =choferService.findByDocumentoPersona(documento);
		return ResponseEntity.ok(response);
	}

}
