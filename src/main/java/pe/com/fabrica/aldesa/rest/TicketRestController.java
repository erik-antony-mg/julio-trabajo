package pe.com.fabrica.aldesa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.service.TicketService;

@RestController
@RequestMapping("/v1")
public class TicketRestController {

	private TicketService ticketService;

	@Autowired
	public TicketRestController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@GetMapping("/tickets")
	public ResponseEntity<?> findAll() {
		ApiResponse response = ticketService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/tickets/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		ApiResponse response = ticketService.findById(id);
		return ResponseEntity.ok(response);
	}

	/*@PostMapping("/tickets")
	public ResponseEntity<?> create(@RequestBody String request) {
		ApiResponse response;
		try {
			response = ticketService.saveOrUpdate(request);
		} catch (ApiException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetailMessage()), HttpStatus.PRECONDITION_FAILED);
		}
		return ResponseEntity.ok(response);
	}*/

}
