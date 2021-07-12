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
import pe.com.fabrica.aldesa.service.EmpresaService;

@RestController
@RequestMapping("/v1")
public class EmpresaRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private EmpresaService empresaService;

	@Autowired
	public EmpresaRestController(EmpresaService empresaService) {
		this.empresaService = empresaService;
	}

	@GetMapping("/empresas/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = empresaService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/empresas/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = empresaService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/empresas/ruc/{ruc}")
	public ResponseEntity<?> findById(@PathVariable String ruc) {
		ApiResponse response = empresaService.findByRuc(ruc);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/empresas/razon-social/{razon}")
	public ResponseEntity<?> findByRazonSocial(@PathVariable String razon) {
		ApiResponse response = empresaService.findByRazonSocial(razon);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/empresas/slice-razon/{razon}/{page}")
	public ResponseEntity<?> findByRazonSocialPag(@PathVariable String razon, @PathVariable int page) {
		ApiResponse response = empresaService.findByRazonSocialPag(razon,page);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/empresas")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = empresaService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/empresas")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = empresaService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/empresas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = empresaService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
