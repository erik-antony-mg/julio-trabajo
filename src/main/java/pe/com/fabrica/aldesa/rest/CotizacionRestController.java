package pe.com.fabrica.aldesa.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import pe.com.fabrica.aldesa.service.CotizacionService;

@RestController
@RequestMapping("/v1")
public class CotizacionRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CotizacionService cotizacionService;

	@Autowired
	public CotizacionRestController(CotizacionService cotizacionService) {
		this.cotizacionService = cotizacionService;
	}

	@GetMapping("/cotizaciones/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = cotizacionService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/cotizaciones/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = cotizacionService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/cotizaciones/between/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<?> findByRangoFechas(@PathVariable String fechaInicial, @PathVariable String fechaFinal) {
		ApiResponse response = cotizacionService.findByRangoFechas(fechaInicial, fechaFinal);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/cotizaciones")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = cotizacionService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/cotizaciones")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = cotizacionService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/cotizaciones/filtro/{filtro}")
	public ResponseEntity<?> searchByFiltro(@PathVariable String filtro) {
		ApiResponse response = cotizacionService.searchByFiltro(filtro);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/cotizaciones/filtro/{filtro}/cliente/{idCliente}")
	public ResponseEntity<?> searchByFiltroCliente(@PathVariable String filtro, @PathVariable Integer idCliente) {
		ApiResponse response = cotizacionService.searchByFiltroCliente(filtro, idCliente);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/cotizaciones/cliente/{idCliente}")
	public ResponseEntity<?> searchByFiltroCliente(@PathVariable Integer idCliente) {
		ApiResponse response = cotizacionService.searchByCliente(idCliente);
		return ResponseEntity.ok(response);
	}
}
