package pe.com.fabrica.aldesa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.service.ContenedorDetalleService;

@RestController
@RequestMapping("/v1")
public class ContenedorDetalleRestController {

	private ContenedorDetalleService ContenedorDetalleService;

	@Autowired
	public ContenedorDetalleRestController(ContenedorDetalleService ContenedorDetalleService) {
		this.ContenedorDetalleService = ContenedorDetalleService;
	}

	@GetMapping("/contenedor-detalles/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = ContenedorDetalleService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/contenedor-detalles/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = ContenedorDetalleService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/contenedor-detalles/contenedor/{id}")
	public ResponseEntity<?> findByIdTarjeta(@PathVariable Long id) {
		ApiResponse response = ContenedorDetalleService.findByIdContenedor(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/contenedor-detalles/tarjeta/{id}")
	public ResponseEntity<?> findByTarjeta(@PathVariable Long id) {
		ApiResponse response = ContenedorDetalleService.findByTarjeta(id);
		return ResponseEntity.ok(response);
	}
}
