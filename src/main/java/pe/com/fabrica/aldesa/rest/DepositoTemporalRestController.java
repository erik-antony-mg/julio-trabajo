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
import pe.com.fabrica.aldesa.service.DepositoTemporalService;

@RestController
@RequestMapping("/v1")
public class DepositoTemporalRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private DepositoTemporalService depositoTemporalService;

	@Autowired
	public DepositoTemporalRestController(DepositoTemporalService depositoTemporalService) {
		this.depositoTemporalService = depositoTemporalService;
	}

	@GetMapping("/depositos-temporales")
	public ResponseEntity<?> findAllSP() {
		ApiResponse response = depositoTemporalService.findAllSP();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/depositos-temporales/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = depositoTemporalService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/depositos-temporales/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = depositoTemporalService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/depositos-temporales")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = depositoTemporalService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/depositos-temporales")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = depositoTemporalService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/depositos-temporales/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = depositoTemporalService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}


	@GetMapping("/depositos-temporales/razonSocial/{razonSocial}")
	public ResponseEntity<?> findByEmpresa(@PathVariable String razonSocial) {
		ApiResponse response = depositoTemporalService.findByRazonSocial(razonSocial);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/depositos-temporales/aduana/{codigoAgencia}")
	public ResponseEntity<?> findByCodigoAduana(@PathVariable String codigoAgencia) {
		ApiResponse response;
		try {
			response = depositoTemporalService.findByCodigoAduana(codigoAgencia);
		} catch (ApiException e) {
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
