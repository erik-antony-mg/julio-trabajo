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
import pe.com.fabrica.aldesa.service.SerieComprobanteService;

@RestController
@RequestMapping("/v1")
public class SerieComprobanteRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SerieComprobanteService serieComprobanteService;

	@Autowired
	public SerieComprobanteRestController(SerieComprobanteService serieComprobanteService) {
		this.serieComprobanteService = serieComprobanteService;
	}

	@GetMapping("/series/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = serieComprobanteService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/series/tipo/{id}")
	public ResponseEntity<?> findAllTipo(@PathVariable Integer id) {
		ApiResponse response = serieComprobanteService.findAllTipo(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/series/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = serieComprobanteService.findById(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/series/tipoComprobante/{tipoComprobante}/slice/{page}")
	public ResponseEntity<?> findByTipoComprobante(@PathVariable Integer tipoComprobante, @PathVariable int page) {
		ApiResponse response = serieComprobanteService.findByTipoComprobante(tipoComprobante, page);
		return ResponseEntity.ok(response);
	}

	
	//nuevo
	@GetMapping("/series/serie/{serie}")
	public ResponseEntity<?> findBySerie(@PathVariable String serie) {
		ApiResponse response = serieComprobanteService.findBySerie(serie);
		return ResponseEntity.ok(response);
	}

	//nuevo descripcion
	@GetMapping("/series/descripcion/slice/{page}/{termino}")
	public ResponseEntity<?> findByDesNew(@PathVariable String termino, @PathVariable int page) {
		ApiResponse response = serieComprobanteService.findByDesNew(termino,page);
		return ResponseEntity.ok(response);
	}

	//nuevo de nuevo
	@GetMapping("/series/serie/{id_tipocomprobante}/slice/{page}/{termino}")
	public ResponseEntity<?> findBySerieNew(@PathVariable Integer id_tipocomprobante,@PathVariable String termino,@PathVariable int page) {
		ApiResponse response = serieComprobanteService.findBySerieNew(id_tipocomprobante,termino,page);
		return ResponseEntity.ok(response);
	}

	//nuevo de descripcion
	@GetMapping("/series/descripcion/{id_tipocomprobante}/slice/{page}/{termino}")
	public ResponseEntity<?> findByDes(@PathVariable Integer id_tipocomprobante,@PathVariable String termino,@PathVariable int page) {
		ApiResponse response = serieComprobanteService.findByDes(id_tipocomprobante,termino,page);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/series")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = serieComprobanteService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/series")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = serieComprobanteService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/series/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = serieComprobanteService.delete(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/series/descripcion/{descripcion}")
	public ResponseEntity<?> findByDescripcion(@PathVariable String descripcion) {
		ApiResponse response = serieComprobanteService.findByDescripcion(descripcion);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/series/comprobante/{comprobante}")
	public ResponseEntity<?> findByComprobante(@PathVariable String comprobante) {
		ApiResponse response = serieComprobanteService.findByComprobante(comprobante);
		return ResponseEntity.ok(response);
	}

}
