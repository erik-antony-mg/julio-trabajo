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
import pe.com.fabrica.aldesa.service.PersonaService;

@RestController
@RequestMapping("/v1")
public class PersonaRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private PersonaService personaService;

	@Autowired
	public PersonaRestController(PersonaService personaService) {
		this.personaService = personaService;
	}

	@GetMapping("/personas/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = personaService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/personas/slicenombre/{nombre}/{page}")
	public ResponseEntity<?> searchByNombresPag(@PathVariable String nombre, @PathVariable int page) {
		ApiResponse response = personaService.searchByNombresPag(nombre,page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/personas/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = personaService.findById(id);
		return ResponseEntity.ok(response);
	}


	@GetMapping("/personas/nombre/{nombre}")
	public ResponseEntity<?> searchByNombres(@PathVariable String nombre) {
		ApiResponse response = personaService.searchByNombres(nombre);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/personas/numero-documento/{ndocumento}")
	public ResponseEntity<?> findByNumeroDocumento(@PathVariable String ndocumento) {
		
		ApiResponse response = personaService.findByNumeroDocumento(ndocumento);
		return ResponseEntity.ok(response);
	}
	//nuevo
	@GetMapping("/personas/numerodoc/{ndocumento}")
	public ResponseEntity<?> findByNumeroDocumento1(@PathVariable String ndocumento) {
		
		ApiResponse response = personaService.findByNumeroDocumento1(ndocumento);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/personas")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = personaService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/personas")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = personaService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/personas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ApiResponse response;
		try {
			response = personaService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

}
