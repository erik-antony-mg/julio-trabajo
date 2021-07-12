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
import pe.com.fabrica.aldesa.service.AgenciaAduanasService;

@RestController
@RequestMapping("/v1")
public class AgenciaAduanasRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AgenciaAduanasService agenciaAduanasService;

	@Autowired
	public AgenciaAduanasRestController(AgenciaAduanasService agenciaAduanasService) {
		this.agenciaAduanasService = agenciaAduanasService;
	}

	@GetMapping("/agencias")
	public ResponseEntity<?> findAll() {
		ApiResponse	response = agenciaAduanasService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/agencias/slice/{page}")
	public ResponseEntity<?> findAllPag(@PathVariable int page) {
		ApiResponse	response = agenciaAduanasService.findAllPag(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/agencias/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = agenciaAduanasService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/agencias/aduana/{codigoAgencia}")
	public ResponseEntity<?> findByCodigoAduana(@PathVariable Integer codigoAgencia) {
		ApiResponse response;
		try {
			response = agenciaAduanasService.findByCodigoAduana(codigoAgencia);
		} catch (ApiException e) {
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/agencias")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = agenciaAduanasService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/agencias")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = agenciaAduanasService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/agencias/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = agenciaAduanasService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}


	@GetMapping("/agencias/razonSocial/{razonSocial}")
	public ResponseEntity<?> findByEmpresa(@PathVariable String razonSocial) {
		ApiResponse response = agenciaAduanasService.findByRazonSocial(razonSocial);
		return ResponseEntity.ok(response);
	}
}
