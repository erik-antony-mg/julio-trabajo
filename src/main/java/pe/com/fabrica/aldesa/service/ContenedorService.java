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
import pe.com.fabrica.aldesa.beans.Contenedor;
import pe.com.fabrica.aldesa.repository.ContenedorRepository;

@Service
public class ContenedorService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ContenedorRepository ContenedorRepository;

	private static final int PAGE_LIMIT = 10;

	@Autowired
	public ContenedorService(ContenedorRepository ContenedorRepository) {
		this.ContenedorRepository = ContenedorRepository;
	}

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Contenedor> ContenedorPage = ContenedorRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, ContenedorPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), ContenedorPage.getContent(),
				Math.toIntExact(ContenedorPage.getTotalElements()));
	}

	public ApiResponse findById(Integer id) {
		Contenedor tmpContenedor = ContenedorRepository.findById(id).orElse(null);
		logger.debug("Contenedor: {}", tmpContenedor);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpContenedor);
	}

	public ApiResponse findByIdTarjeta(Long id) {
		Contenedor tmpContenedor = ContenedorRepository.findByIdTarjeta(id).orElse(null);
		logger.debug("Contenedor: {}", tmpContenedor);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpContenedor);
	}

}
