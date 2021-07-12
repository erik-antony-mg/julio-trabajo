package pe.com.fabrica.aldesa.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.ComprobanteDetalle;
import pe.com.fabrica.aldesa.repository.ComprobanteDetalleRepository;

@Service
public class ComprobanteDetalleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ComprobanteDetalleRepository comprobanteDetalleRepository;

	public ApiResponse findByComprobante(Integer id) {
		List<ComprobanteDetalle> comprobanteDetalles = comprobanteDetalleRepository.findByComprobante(id);
		logger.debug("ComprobanteDetalle: {}", comprobanteDetalles);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), comprobanteDetalles,
				comprobanteDetalles.size());
	}
}
