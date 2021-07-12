package pe.com.fabrica.aldesa.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Ticket;
import pe.com.fabrica.aldesa.repository.TicketRepository;

@Service
public class TicketService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TicketRepository ticketRepository;


	public ApiResponse findAll() {
		List<Ticket> tickets = ticketRepository.findAll();
		int total = tickets.size();
		logger.debug("Total Tickets: {}", total);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tickets, total);
	}

	public ApiResponse findById(Long id) {
		Ticket tmpTicket = ticketRepository.findById(id).orElse(null);
		logger.debug("Ticket: {}", tmpTicket);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTicket);
	}

	/*public ApiResponse saveOrUpdate(String request) throws ApiException {
		/*Ticket responseTicket;

		JsonNode root;
		Long id = null;
		Long idVehiculo = null;
		Long idChofer = null;
		Double peso1 = null;
		Double peso2 = null;
		String fechaPeso1 = null;
		String fechaPeso2 = null;
		try {
			root = new ObjectMapper().readTree(request);

			id = root.path("id").asLong();
			logger.debug("id: {}", id);

			idVehiculo = root.path("idVehiculo").asLong();
			logger.debug("idVehiculo: {}", idVehiculo);

			idChofer = root.path("idChofer").asLong();
			logger.debug("idChofer: {}", idChofer);

			peso1 = root.path("peso1").asDouble();
			logger.debug("peso1: {}", peso1);

			peso2 = root.path("peso2").asDouble();
			logger.debug("peso2: {}", peso2);

			fechaPeso1 = root.path("fechaPeso1").asText();
			logger.debug("fechaPeso1: {}", fechaPeso1);

			fechaPeso2 = root.path("fechaPeso2").asText();
			logger.debug("fechaPeso2: {}", fechaPeso2);

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		if (id == 0 || idVehiculo == 0 || idChofer == 0 || peso1 == 0 || StringUtils.isBlank(fechaPeso1) || "null".equals(fechaPeso1)) {
			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
		}

		Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
				.orElseThrow(() -> new ApiException(ApiError.VEHICULO_NOT_FOUND.getCode(), ApiError.VEHICULO_NOT_FOUND.getMessage()));

		Chofer chofer = choferRepository.findById(idChofer)
				.orElseThrow(() -> new ApiException(ApiError.CHOFER_NOT_FOUND.getCode(), ApiError.CHOFER_NOT_FOUND.getMessage()));

		try {
			Ticket ticket = new Ticket();
			ticket.setIdTicket(id);
			ticket.setVehiculo(vehiculo);
			ticket.setChofer(chofer);
			ticket.setPeso1(peso1);
			ticket.setPeso2(peso2);
			ticket.setFechaPeso1(DateUtil.of(fechaPeso1));
			ticket.setFechaPeso2(DateUtil.of(fechaPeso2));

			responseTicket = ticketRepository.save(ticket);
			logger.debug("Ticket guardado");
		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTicket);
	}

	//	public ApiResponse update(String request) throws ApiException {
	//		Ticket responseTicket;
	//
	//		JsonNode root;
	//		Long id = null;
	//		Long idVehiculo = null;
	//		Long idChofer = null;
	//		Double peso1 = null;
	//		Double peso2 = null;
	//		String fechaPeso1 = null;
	//		String fechaPeso2 = null;
	//		try {
	//			root = new ObjectMapper().readTree(request);
	//
	//			id = root.path("id").asLong();
	//			logger.debug("id: {}", id);
	//
	//			idVehiculo = root.path("idVehiculo").asLong();
	//			logger.debug("idVehiculo: {}", idVehiculo);
	//
	//			idChofer = root.path("idChofer").asLong();
	//			logger.debug("idChofer: {}", idChofer);
	//
	//			peso1 = root.path("peso1").asDouble();
	//			logger.debug("peso1: {}", peso1);
	//
	//			peso2 = root.path("peso2").asDouble();
	//			logger.debug("peso2: {}", peso2);
	//
	//			fechaPeso1 = root.path("fechaPeso1").asText();
	//			logger.debug("fechaPeso1: {}", fechaPeso1);
	//
	//			fechaPeso2 = root.path("fechaPeso2").asText();
	//			logger.debug("fechaPeso2: {}", fechaPeso2);
	//
	//		} catch (JsonProcessingException e) {
	//			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
	//		}
	//
	//		if (id == 0 || idVehiculo == 0 || idChofer == 0 || peso1 == 0 || StringUtils.isBlank(fechaPeso1) || "null".equals(fechaPeso1)) {
	//			throw new ApiException(ApiError.EMPTY_OR_NULL_PARAMETER.getCode(), ApiError.EMPTY_OR_NULL_PARAMETER.getMessage());
	//		}
	//
	//		Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
	//				.orElseThrow(() -> new ApiException(ApiError.VEHICULO_NOT_FOUND.getCode(), ApiError.VEHICULO_NOT_FOUND.getMessage()));
	//
	//		Chofer chofer = choferRepository.findById(idChofer)
	//				.orElseThrow(() -> new ApiException(ApiError.CHOFER_NOT_FOUND.getCode(), ApiError.CHOFER_NOT_FOUND.getMessage()));
	//
	//		try {
	//			Ticket ticket = new Ticket();
	//			ticket.setIdTicket(id);
	//			ticket.setVehiculo(vehiculo);
	//			ticket.setChofer(chofer);
	//			ticket.setPeso1(peso1);
	//			ticket.setPeso2(peso2);
	//			ticket.setFechaPeso1(DateUtil.of(fechaPeso1));
	//			ticket.setFechaPeso2(DateUtil.of(fechaPeso2));
	//
	//			responseTicket = ticketRepository.save(ticket);
	//			logger.debug("Ticket guardado");
	//		} catch (Exception e) {
	//			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(), ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
	//		}
	//		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTicket);
	//	}*/
	
}
