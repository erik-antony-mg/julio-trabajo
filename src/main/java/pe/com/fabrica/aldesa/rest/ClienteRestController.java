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
import pe.com.fabrica.aldesa.service.ClienteService;

@RestController
@RequestMapping("/v1")
public class ClienteRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ClienteService clienteService;

	@Autowired
	public ClienteRestController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping("/clientes/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = clienteService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/tipoPersona/{tipoPersona}/slice/{page}")
	public ResponseEntity<?> findByTipoPersona(@PathVariable Integer tipoPersona, @PathVariable int page) {
		ApiResponse response = clienteService.findByTipoPersona(tipoPersona, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = clienteService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/nombre/{nombre}")
	public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
		ApiResponse response = clienteService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/slice-nombre/{nombre}/{page}")
	public ResponseEntity<?> findByNombrePag(@PathVariable String nombre, @PathVariable int page) {
		ApiResponse response = clienteService.findByNombrePag(nombre, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/codigo/{codigo}")
	public ResponseEntity<?> findByCodigo(@PathVariable String codigo) {
		ApiResponse response = clienteService.findByCodigo(codigo);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/numero-documento/{ndocumento}")
	public ResponseEntity<?> findByNumeroDocumento(@PathVariable String ndocumento) {
		ApiResponse response = clienteService.findByNumeroDocumento(ndocumento);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/razon-social/{rsocial}")
	public ResponseEntity<?> findByRazonSocial(@PathVariable String rsocial) {
		ApiResponse response = clienteService.findByRazonSocial(rsocial);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/slice-rsocial/{nombre}/{page}")
	public ResponseEntity<?> findByRazonSocialPag(@PathVariable String nombre, @PathVariable int page) {
		ApiResponse response = clienteService.findByRazonSocialPag(nombre, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/clientes/ruc/{ruc}")
	public ResponseEntity<?> findByRuc(@PathVariable String ruc) {
		ApiResponse response = clienteService.findByRuc(ruc);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = clienteService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/clientes")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = clienteService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = clienteService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
