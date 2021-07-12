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
import pe.com.fabrica.aldesa.service.AreaService;

@RestController
@RequestMapping("/v1")
public class AreaRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AreaService areaService;

	@Autowired
	public AreaRestController(AreaService areaService) {
		this.areaService = areaService;
	}

	@GetMapping("/areas")
	public ResponseEntity<?> findAllSP() {
		ApiResponse response = areaService.findAllSP();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/areas/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = areaService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/areas/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = areaService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/areas")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = areaService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/areas")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = areaService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/areas/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = areaService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/areas/nombre/{nombre}")
	public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
		ApiResponse response = areaService.findByNombre(nombre);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/areas/slice-nombre/{nombre}/{page}")
	public ResponseEntity<?> findByNombrePag(@PathVariable String nombre, @PathVariable int page) {
		ApiResponse response = areaService.findByNombrePag(nombre, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/areas/abreviatura/{abreviatura}")
	public ResponseEntity<?> findByAbreviatura(@PathVariable String abreviatura) {
		ApiResponse response = areaService.findByAbreviatura(abreviatura);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/areas/slice-abre/{abreviatura}/{page}")
	public ResponseEntity<?> findByAbreviaturaPag(@PathVariable String abreviatura, @PathVariable int page) {
		ApiResponse response = areaService.findByAbreviaturaPag(abreviatura, page);
		return ResponseEntity.ok(response);
		
	}
}
