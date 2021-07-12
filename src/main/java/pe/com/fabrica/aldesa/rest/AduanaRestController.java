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
import pe.com.fabrica.aldesa.service.AduanaService;

@RestController
@RequestMapping("/v1")
public class AduanaRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AduanaService aduanaService;

	@Autowired
	public AduanaRestController(AduanaService aduanaService) {
		this.aduanaService = aduanaService;
	}

	@GetMapping("/aduanas")
	public ResponseEntity<?> findAllSP() {
		ApiResponse response = aduanaService.findAllSP();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/aduanas/slice/{page}")
	public ResponseEntity<?> findAllSP(@PathVariable int page) {
		ApiResponse response = aduanaService.findAllPag(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/aduanas/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = aduanaService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/aduanas")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = aduanaService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/aduanas")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = aduanaService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/aduanas/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = aduanaService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/agencias/nombre/{nombre}")
	public ResponseEntity<?> findByEmpresa(@PathVariable String nombre) {
		ApiResponse response = aduanaService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

}
