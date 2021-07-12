package pe.com.fabrica.aldesa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.service.MercanciaDetalleService;

@RestController
@RequestMapping("/v1")
public class MercanciaDetalleRestController {


	private MercanciaDetalleService MercanciaDetalleService;

	@Autowired
	public MercanciaDetalleRestController(MercanciaDetalleService MercanciaDetalleService) {
		this.MercanciaDetalleService = MercanciaDetalleService;
	}

	@GetMapping("/mercaderia-detalles/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = MercanciaDetalleService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/mercaderia-detalles/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = MercanciaDetalleService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/mercaderia-detalles/mercancia/{id}")
	public ResponseEntity<?> findByIdTarjeta(@PathVariable Long id) {
		ApiResponse response = MercanciaDetalleService.findByIdMercancia(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/mercaderia-detalles/tarjeta/{id}")
	public ResponseEntity<?> findByTarjeta(@PathVariable Long id) {
		ApiResponse response = MercanciaDetalleService.findByTarjeta(id);
		return ResponseEntity.ok(response);
	}
}
