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
import pe.com.fabrica.aldesa.service.ComprobanteService;

@RestController
@RequestMapping("/v1")
public class ComprobanteRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ComprobanteService comprobanteService;

	@Autowired
	public ComprobanteRestController(ComprobanteService comprobanteService) {
		this.comprobanteService = comprobanteService;
	}

	@PutMapping("/comprobantes")
	public ResponseEntity<?> update(@RequestBody String request) {
		ApiResponse response;
		try {
			response = comprobanteService.update(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/comprobantes/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = comprobanteService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/id/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = comprobanteService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/id-tipo-comprobante/{idTipoComprobante}/between/{fechaInicial}/{fechaFinal}/slice/{page}")
	public ResponseEntity<?> findByTipoComprobanteRangoFechas(@PathVariable Integer idTipoComprobante,
			@PathVariable String fechaInicial, @PathVariable String fechaFinal, @PathVariable int page) {
		ApiResponse response;
		try {
			response = comprobanteService.findByTipoComprobanteRangoFechas(idTipoComprobante, fechaInicial, fechaFinal, page);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/id-tipo-comprobante/{idTipoComprobante}/comprobante/{comprobante}")
	public ResponseEntity<?> findByTipoComprobanteComprobante(@PathVariable Integer idTipoComprobante,
			@PathVariable String comprobante) {
		ApiResponse response;
		try {
			response = comprobanteService.findByTipoComprobanteComprobante(idTipoComprobante, comprobante);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/id-tipo-comprobante/{idTipoComprobante}/cliente/{idCliente}/between/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<?> findByTipoComprobanteClienteRangoFechas(@PathVariable Integer idTipoComprobante,
			@PathVariable Long idCliente, @PathVariable String fechaInicial, @PathVariable String fechaFinal) {
		ApiResponse response;
		try {
			response = comprobanteService.findByTipoComprobanteClienteRangoFechas(idTipoComprobante, idCliente,
					fechaInicial, fechaFinal);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/cliente/{idCliente}")
	public ResponseEntity<?> findByTipoComprobanteCliente(@PathVariable Long idCliente) {
		ApiResponse response;
		try {
			response = comprobanteService.findByCliente(idCliente);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/id-tipo-comprobante/{idTipoComprobante}/tarjeta/{idTarjeta}/between/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<?> findByTipoComprobanteTarjetaRangoFechas(@PathVariable Integer idTipoComprobante,
			@PathVariable Long idTarjeta, @PathVariable String fechaInicial, @PathVariable String fechaFinal) {
		ApiResponse response;
		try {
			response = comprobanteService.findByTipoComprobanteTarjetaRangoFechas(idTipoComprobante, idTarjeta,
					fechaInicial, fechaFinal);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/comprobantes/id-tipo-comprobante/{idTipoComprobante}/cliente/{idCliente}/tarjeta/{idTarjeta}/between/{fechaInicial}/{fechaFinal}")
	public ResponseEntity<?> findByTipoComprobanteClienteTarjetaRangoFechas(@PathVariable Integer idTipoComprobante,
			@PathVariable Long idCliente, @PathVariable Long idTarjeta, @PathVariable String fechaInicial,
			@PathVariable String fechaFinal) {
		ApiResponse response;
		try {
			response = comprobanteService.findByTipoComprobanteClienteTarjetaRangoFechas(idTipoComprobante, idCliente,
					idTarjeta, fechaInicial, fechaFinal);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	/*
	 * @GetMapping(
	 * "/comprobantes/serie/{serie}/numero/{numero}/id-tipo-comprobante/{idTipoComprobante}")
	 * public ResponseEntity<?> findComprobante(@PathVariable String
	 * serie, @PathVariable Long numero, @PathVariable Integer idTipoComprobante) {
	 * ApiResponse response; try { response =
	 * comprobanteService.findComprobante(serie, numero, idTipoComprobante); } catch
	 * (ApiException e) { logger.error(e.getMessage(), e); return new
	 * ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(),
	 * e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED); } return
	 * ResponseEntity.ok(response); }
	 * 
	 * @GetMapping("/comprobantes/between/{fechaInicial}/{fechaFinal}") public
	 * ResponseEntity<?> findByRangoFechas(@PathVariable String
	 * fechaInicial, @PathVariable String fechaFinal) { ApiResponse response =
	 * comprobanteService.findByRangoFechas(fechaInicial, fechaFinal); return
	 * ResponseEntity.ok(response); }
	 * 
	 * @GetMapping("/comprobantes/{numeroDocumento}") public ResponseEntity<?>
	 * findByRangoFechas(@PathVariable String numeroDocumento) { ApiResponse
	 * response = comprobanteService.searchByNumeroDocumento(numeroDocumento);
	 * return ResponseEntity.ok(response); }
	 */
	@PostMapping("/comprobantes")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = comprobanteService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/comprobantes/anular/{id}")
	public ResponseEntity<?> anular(@PathVariable Integer id) {
		ApiResponse response;
		try {
			response = comprobanteService.anular(id);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

}
