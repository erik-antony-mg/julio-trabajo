package pe.com.fabrica.aldesa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.service.ComprobanteDetalleService;

@RestController
@RequestMapping("/v1")
public class ComprobanteDetalleRestController {

	private ComprobanteDetalleService comprobanteDetalleService;

	@Autowired
	public ComprobanteDetalleRestController(ComprobanteDetalleService comprobanteDetalleService) {
		this.comprobanteDetalleService = comprobanteDetalleService;
	}

	@GetMapping("/comprobante-detalle/comprobante/{id}")
	public ResponseEntity<?> findByTipoComprobanteCliente(@PathVariable Integer id) {
		ApiResponse response;
		response = comprobanteDetalleService.findByComprobante(id);
		return ResponseEntity.ok(response);
	}
}
