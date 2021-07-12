package pe.com.fabrica.aldesa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.service.MercanciaService;

@RestController
@RequestMapping("/v1")
public class MercanciaRestController {


	private MercanciaService MercanciaService;

	@Autowired
	public MercanciaRestController(MercanciaService MercanciaService) {
		this.MercanciaService = MercanciaService;
	}

	@GetMapping("/mercaderias/slice/{page}")
	public ResponseEntity<?> findAll(@PathVariable int page) {
		ApiResponse response = MercanciaService.findAll(page);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/mercaderias/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ApiResponse response = MercanciaService.findById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/mercaderias/tarjeta/{id}")
	public ResponseEntity<?> findByIdTarjeta(@PathVariable Long id) {
		ApiResponse response = MercanciaService.findByIdTarjeta(id);
		return ResponseEntity.ok(response);
	}
}
