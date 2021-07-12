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
import pe.com.fabrica.aldesa.service.MovimientoService;

@RestController
@RequestMapping("/v1")
public class MovimientoRestController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MovimientoService movimientoService;

	@Autowired
	public MovimientoRestController(MovimientoService movimientoService) {
		this.movimientoService = movimientoService;
	}

	@GetMapping("/movimientos/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = movimientoService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/tipo/{tipoMovimiento}/fecha-inicio/{fechaInicio}/fecha-fin/{fechaFin}/slice/{page}")
	public ResponseEntity<?> findByTipoFecha(@PathVariable String tipoMovimiento, @PathVariable String fechaInicio,
			@PathVariable String fechaFin, @PathVariable int page) {
		ApiResponse response = movimientoService.findByTipoFecha(tipoMovimiento, fechaInicio, fechaFin, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/fecha-inicio/{fechaInicio}/fecha-fin/{fechaFin}/slice/{page}")
	public ResponseEntity<?> findByFecha(@PathVariable String fechaInicio, @PathVariable String fechaFin,
			@PathVariable int page) {
		ApiResponse response = movimientoService.findByFecha(fechaInicio, fechaFin, page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/tipo-movimiento/{tipoMovimiento}/mercancia/{idmercancia}")
	public ResponseEntity<?> findByFecha(@PathVariable String tipoMovimiento, @PathVariable int idmercancia) {
		ApiResponse response = movimientoService.findByTipoAndMercancia(tipoMovimiento, idmercancia);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = movimientoService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/tarjeta/{nroTarjeta}")
	public ResponseEntity<?> findMovimientoByTarjetaByNumero(@PathVariable String nroTarjeta) {
		ApiResponse response = movimientoService.findMovimientoByTarjetaByNumero(nroTarjeta);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/tarjeta/cliente-ruc/{ruc}")
	public ResponseEntity<?> findMovimientoByTarjetaByClienteByRuc(@PathVariable String ruc) {
		ApiResponse response = movimientoService.findMovimientoByTarjetaByClienteByRuc(ruc);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/movimientos/tarjeta/cliente-rsocial/{razonSocial}")
	public ResponseEntity<?> findMovimientoByTarjetaByClienteByRazonSocial(@PathVariable String razonSocial) {
		ApiResponse response = movimientoService.findMovimientoByTarjetaByClienteByRazonSocial(razonSocial);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/movimientos")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = movimientoService.save(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/movimientos/anular")
	public ResponseEntity<?> anulacion(@RequestBody String request) {
		ApiResponse response;
		try {
			response = movimientoService.anulacion(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()),
					HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}
}
