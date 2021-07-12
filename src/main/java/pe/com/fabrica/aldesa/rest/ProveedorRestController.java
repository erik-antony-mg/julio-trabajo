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
import pe.com.fabrica.aldesa.service.ProveedorService;

@RestController
@RequestMapping("/v1")
public class ProveedorRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ProveedorService ProveedorService;

	@Autowired
	public ProveedorRestController(ProveedorService ProveedorService) {
		this.ProveedorService = ProveedorService;
	}

	@GetMapping("/proveedores/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = ProveedorService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/proveedores/tipoPersona/{tipoPersona}/slice/{page}")
	public ResponseEntity<?> findByTipoPersona(@PathVariable Integer tipoPersona, @PathVariable int page) {
		ApiResponse response = ProveedorService.findByTipoPersona(tipoPersona, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/proveedores/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = ProveedorService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/proveedores/nombre/{nombre}")
	public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
		ApiResponse response = ProveedorService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/proveedores/numero-documento/{ndocumento}")
	public ResponseEntity<?> findByNumeroDocumento(@PathVariable String ndocumento) {
		ApiResponse response = ProveedorService.findByNumeroDocumento(ndocumento);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/proveedores/razon-social/{rsocial}")
	public ResponseEntity<?> findByRazonSocial(@PathVariable String rsocial) {
		ApiResponse response = ProveedorService.findByRazonSocial(rsocial);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/proveedores/ruc/{ruc}")
	public ResponseEntity<?> findByRuc(@PathVariable String ruc) {
		ApiResponse response = ProveedorService.findByRuc(ruc);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/proveedores")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ProveedorService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/proveedores")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ProveedorService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/proveedores/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = ProveedorService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
