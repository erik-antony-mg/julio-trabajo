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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.dto.ErrorResponse;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.service.SubservicioService;

@RestController
@RequestMapping("/v1")
public class SubservicioRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SubservicioService subservicioService;

	@Autowired
	public SubservicioRestController(SubservicioService subservicioService) {
		this.subservicioService = subservicioService;
	}

	@GetMapping("/subservicios")
	public ResponseEntity<?> findAll() {
		ApiResponse response = subservicioService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/subservicios/serv/{idServicio}/subserv/{idSubservicio}")
	public ResponseEntity<?> findById(@PathVariable Integer idServicio, @PathVariable Integer idSubservicio) {
		ApiResponse response = subservicioService.findById(idServicio, idSubservicio);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/subservicios/serv/{idServicio}")
	public ResponseEntity<?> findByIdServicio(@PathVariable Integer idServicio) {
		ApiResponse response;
		try {
			response = subservicioService.findByIdServicio(idServicio);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/subservicios")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = subservicioService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/subservicios/serv/{idServicio}/subserv/{idSubservicio}")
	public ResponseEntity<?> delete(@PathVariable Integer idServicio, @PathVariable Integer idSubservicio) {
		ApiResponse response;
		try {
			response = subservicioService.delete(idServicio, idSubservicio);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
