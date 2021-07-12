package pe.com.fabrica.aldesa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.service.ContenedorService;

@RestController
@RequestMapping("/v1")
public class ContenedorRestController {


	private ContenedorService ContenedorService;

	@Autowired
	public ContenedorRestController(ContenedorService ContenedorService) {
		this.ContenedorService = ContenedorService;
	}

	@GetMapping("/contenedores/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = ContenedorService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/contenedores/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = ContenedorService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/contenedores/tarjeta/{id}")
	public ResponseEntity<?> findByIdTarjeta(@PathVariable Long id) {
		ApiResponse response = ContenedorService.findByIdTarjeta(id);
		return ResponseEntity.ok(response);
	}
}
