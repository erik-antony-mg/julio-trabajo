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
import pe.com.fabrica.aldesa.service.TipoCambioService;

@RestController
@RequestMapping("/v1")
public class TipoCambioRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private TipoCambioService tipoCambioService;

	@Autowired
	public TipoCambioRestController(TipoCambioService tipoCambioService) {
		this.tipoCambioService = tipoCambioService;
	}

	@GetMapping("/tipos-cambio/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = tipoCambioService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-cambio/between/{fechaInicial}/{fechaFinal}/slice/{page}")
	public ResponseEntity<?> findByRangoFechas(@PathVariable String fechaInicial, @PathVariable String fechaFinal, @PathVariable int page) {
		ApiResponse response;
		try {
			response = tipoCambioService.findByRangoFechas(fechaInicial, fechaFinal, page);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-cambio/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = tipoCambioService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/tipos-cambio")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = tipoCambioService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/tipos-cambio")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = tipoCambioService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/tipos-cambio/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = tipoCambioService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-cambio/fecha/{fechaTipoCambio}")
	public ResponseEntity<?> findByCodigoFamilia(@PathVariable String fechaTipoCambio) {
		ApiResponse response = tipoCambioService.findByFecha(fechaTipoCambio);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-cambio/ultimo-registro")
	public ResponseEntity<?> ultimoRegistro() {
		ApiResponse response = tipoCambioService.ultimoRegistro();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tipos-cambio/fecha1/{fechaTipoCambio}/fecha2/{fechaTipoCambio2}")
	public ResponseEntity<?> findByCodigoFamilia(@PathVariable String fechaTipoCambio,
			@PathVariable String fechaTipoCambio2) {
		ApiResponse response = tipoCambioService.obteneerRangoFecha(fechaTipoCambio, fechaTipoCambio2);
		return ResponseEntity.ok(response);
	}


	@GetMapping("/tipos-cambio/promedio/year/{año}")
	public ResponseEntity<?> obtenerPromedio(@PathVariable Integer año) {
		ApiResponse response = tipoCambioService.obtenerPromedio(año);
		return ResponseEntity.ok(response);
	}
}
