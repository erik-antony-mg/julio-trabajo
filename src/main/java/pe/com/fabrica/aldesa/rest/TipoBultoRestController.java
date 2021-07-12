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
import pe.com.fabrica.aldesa.service.TipoBultoService;

@RestController
@RequestMapping("/v1")
public class TipoBultoRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoBultoService tipoBultoService;

	@Autowired
	public TipoBultoRestController(TipoBultoService tipoBultoService) {
		this.tipoBultoService = tipoBultoService;
	}

	@GetMapping("/tipos-bultos/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = tipoBultoService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-bultos/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = tipoBultoService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/tipos-bultos")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = tipoBultoService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/tipos-bultos")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = tipoBultoService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/tipos-bultos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = tipoBultoService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-bultos/nombre/{nombre}")
	public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
		ApiResponse response = tipoBultoService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-bultos/abreviatura/{abreviatura}")
	public ResponseEntity<?> findByAbreviatura(@PathVariable String abreviatura) {
		ApiResponse response = tipoBultoService.findByAbreviatura(abreviatura);
		return ResponseEntity.ok(response);
	}

}
