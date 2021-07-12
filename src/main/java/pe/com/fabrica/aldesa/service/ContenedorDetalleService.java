package pe.com.fabrica.aldesa.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.ContenedorDetalle;
import pe.com.fabrica.aldesa.repository.ContenedorDetalleRepository;

@Service
public class ContenedorDetalleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ContenedorDetalleRepository ContenedorDetalleRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public ContenedorDetalleService(ContenedorDetalleRepository ContenedorDetalleRepository) {
		this.ContenedorDetalleRepository = ContenedorDetalleRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<ContenedorDetalle> ContenedorPage = ContenedorDetalleRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, ContenedorPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), ContenedorPage.getContent(),
				Math.toIntExact(ContenedorPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		ContenedorDetalle tmpContenedor = ContenedorDetalleRepository.findById(id).orElse(null);
		logger.debug("Contenedor: {}", tmpContenedor);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpContenedor);
	}

	public ApiResponse findByIdContenedor(Long id) {
		List<ContenedorDetalle> tmpContenedor = ContenedorDetalleRepository.findByIdMercaderia(id);
		logger.debug("Contenedor: {}", tmpContenedor);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpContenedor);
	}

	public ApiResponse findByTarjeta(Long id) {
		List<ContenedorDetalle> listMercaderia = ContenedorDetalleRepository.findByTarjeta(id);
		logger.debug("listMercaderia: {}", listMercaderia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listMercaderia,
				listMercaderia.size());
	}

}
