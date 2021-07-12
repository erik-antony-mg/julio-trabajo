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
import pe.com.fabrica.aldesa.service.RegimenService;

@RestController
@RequestMapping("/v1")
public class RegimenRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RegimenService regimenService;

	@Autowired
	public RegimenRestController(RegimenService regimenService) {
		this.regimenService = regimenService;
	}

	@GetMapping("/regimenes")
	public ResponseEntity<?> findAllSP() {
		ApiResponse response = regimenService.findAllSP();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/regimenes/slice/{page}")
	public ResponseEntity<?> findAllPag(@PathVariable int page) {
		ApiResponse	response = regimenService.findAllPag(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/regimenes/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = regimenService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/regimenes/aduana/{codigoAgencia}")
	public ResponseEntity<?> findByCodigoAduana(@PathVariable Integer codigoAgencia) {
		ApiResponse response;
		try {
			response = regimenService.findByCodigoAduana(codigoAgencia);
		} catch (ApiException e) {
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/regimenes")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = regimenService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/regimenes")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = regimenService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/regimenes/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = regimenService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}


	@GetMapping("/regimenes/nombre/{nombre}")
	public ResponseEntity<?> findByNombResponseEntity(@PathVariable String nombre) {
		ApiResponse response = regimenService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

}
