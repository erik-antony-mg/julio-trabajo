package pe.com.fabrica.aldesa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.beans.Mercancia;
import pe.com.fabrica.aldesa.repository.MercanciaRepository;

@Service
public class MercanciaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MercanciaRepository MercanciaRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public MercanciaService(MercanciaRepository MercanciaRepository) {
		this.MercanciaRepository = MercanciaRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Mercancia> MercanciaPage = MercanciaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, MercanciaPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), MercanciaPage.getContent(),
				Math.toIntExact(MercanciaPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Mercancia tmpMercancia = MercanciaRepository.findById(id).orElse(null);
		logger.debug("Mercancia: {}", tmpMercancia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpMercancia);
	}

	public ApiResponse findByIdTarjeta(Long id) {
		Mercancia tmpMercancia = MercanciaRepository.findByIdTarjeta(id).orElse(null);
		logger.debug("Mercancia: {}", tmpMercancia);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpMercancia);
	}

}
