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
import pe.com.fabrica.aldesa.beans.MercanciaDetalle;
import pe.com.fabrica.aldesa.repository.MercanciaDetalleRepository;

@Service
public class MercanciaDetalleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MercanciaDetalleRepository MercanciaDetalleRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public MercanciaDetalleService(MercanciaDetalleRepository MercanciaDetalleRepository) {
		this.MercanciaDetalleRepository = MercanciaDetalleRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<MercanciaDetalle> MercanciaPage = MercanciaDetalleRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, MercanciaPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), MercanciaPage.getContent(),
				Math.toIntExact(MercanciaPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		MercanciaDetalle tmpMercancia = MercanciaDetalleRepository.findById(id).orElse(null);
		logger.debug("Mercancia: {}", tmpMercancia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpMercancia);
	}

	public ApiResponse findByIdMercancia(Long id) {
		List<MercanciaDetalle> tmpMercancia = MercanciaDetalleRepository.findByIdMercaderia(id);
		logger.debug("Mercancia: {}", tmpMercancia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpMercancia);
	}

	public ApiResponse findByTarjeta(Long id) {
		List<MercanciaDetalle> listMercaderia = MercanciaDetalleRepository.findByTarjeta(id);
		logger.debug("listMercaderia: {}", listMercaderia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), listMercaderia,
				listMercaderia.size());
	}

}
