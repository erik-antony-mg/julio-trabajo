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
import pe.com.fabrica.aldesa.service.TipoComprobanteService;

@RestController
@RequestMapping("/v1")
public class TipoComprobanteRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoComprobanteService tipoComprobanteService;

	@Autowired
	public TipoComprobanteRestController(TipoComprobanteService tipoComprobanteService) {
		this.tipoComprobanteService = tipoComprobanteService;
	}

	@GetMapping("/tipos-comprobantes/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = tipoComprobanteService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-comprobantes/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = tipoComprobanteService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/tipos-comprobantes")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = tipoComprobanteService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/tipos-comprobantes")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = tipoComprobanteService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/tipos-comprobantes/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = tipoComprobanteService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-comprobantes/nombre/{nombre}")
	public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
		ApiResponse response = tipoComprobanteService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-comprobantes/abreviatura/{abreviatura}")
	public ResponseEntity<?> findByAbreviatura(@PathVariable String abreviatura) {
		ApiResponse response = tipoComprobanteService.findByAbreviatura(abreviatura);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-comprobantes/codigoSunat/{codigoSunat}")
	public ResponseEntity<?> findByCodigoSunat(@PathVariable String codigoSunat) {
		ApiResponse response = tipoComprobanteService.findByCodigoSunat(codigoSunat);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-comprobantes")
	public ResponseEntity<?> findAll1() {
		ApiResponse response = tipoComprobanteService.findAll1();
		return ResponseEntity.ok(response);
	}
}
