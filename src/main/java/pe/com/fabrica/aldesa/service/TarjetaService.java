package pe.com.fabrica.aldesa.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import pe.com.fabrica.aldesa.beans.*;
import pe.com.fabrica.aldesa.constant.ApiError;
import pe.com.fabrica.aldesa.dto.ApiResponse;
import pe.com.fabrica.aldesa.dto.TarjetaContext;
import pe.com.fabrica.aldesa.exception.ApiException;
import pe.com.fabrica.aldesa.repository.AgenciaAduanasRepository;
import pe.com.fabrica.aldesa.repository.ClienteRepository;
import pe.com.fabrica.aldesa.repository.CotizacionRepository;
import pe.com.fabrica.aldesa.repository.DepositoTemporalRepository;
import pe.com.fabrica.aldesa.repository.RegimenRepository;
import pe.com.fabrica.aldesa.repository.TarjetaRepository;
import pe.com.fabrica.aldesa.repository.TipoBultoRepository;
import pe.com.fabrica.aldesa.repository.TipoMercanciaRepository;
import pe.com.fabrica.aldesa.repository.UbicacionRepository;
import pe.com.fabrica.aldesa.util.DateUtil;

@Service
public class TarjetaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TarjetaRepository tarjetaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private RegimenRepository regimenRepository;

	@Autowired
	private AgenciaAduanasRepository agenciaAduanasRepository;

	@Autowired
	private DepositoTemporalRepository depositoTemporalRepository;

	@Autowired
	private TipoBultoRepository tipoBultoRepository;

	@Autowired
	private CotizacionRepository cotizacionRepository;

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	private TipoMercanciaRepository tipoMercanciaRepository;

	private static final int PAGE_LIMIT = 10;

	public ApiResponse findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_LIMIT);
		Page<Tarjeta> tarjetasPage = tarjetaRepository.findAll(pageable);
		logger.debug("Página {} de: {}", pageNumber, tarjetasPage.getTotalPages());
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tarjetasPage.getContent(),
				Math.toIntExact(tarjetasPage.getTotalElements()));
	}

	public ApiResponse findById(Long id) {
		Tarjeta tmpTarjeta = tarjetaRepository.findByIdTarjeta(id).orElse(null);
		logger.debug("Tarjeta: {}", tmpTarjeta);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTarjeta);
	}

	public ApiResponse findTarjetaClienteByRuc(String ruc) {
		List<Tarjeta> tmpTarjeta = tarjetaRepository.findTarjetaClienteByRuc(ruc);
		logger.debug("Tarjeta: {}", tmpTarjeta);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTarjeta, tmpTarjeta.size());
	}

	public ApiResponse findTarjetaCliente(Integer id) {
		List<Tarjeta> tmpTarjeta = tarjetaRepository.findTarjetaClien(id);
		logger.debug("Tarjeta: {}", tmpTarjeta);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTarjeta, tmpTarjeta.size());
	}

	public ApiResponse findTarjetaCienteByRazonSocial(String razonSocial) {
		List<Tarjeta> tmpTarjeta = tarjetaRepository.findTarjetaClienteByRazonSocial(razonSocial);
		logger.debug("Tarjeta: {}", tmpTarjeta);
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), tmpTarjeta, tmpTarjeta.size());
	}

	public ApiResponse save(String request) throws ApiException {
		TarjetaContext responseTarjeta;
		JsonNode root;
		Long idCliente = null;
		Long idCotizacion = null;
		Long idUbicacion = null;
		Long idAgenciaAduana = null;
		Integer idRegimen = null;
		Integer idDepositoTemporal = null;
		String docTransporte = null;
		String observaciones = null;
		String nroDeclaracion = null;
		String fecha = null;
		String nro_tarjeta = null;
		String fechaNumeracion = null;
		String manifiesto = null;
		String fechaVencimiento = null;
		String dam = null;
		String subPartidaNacional = null;
		String fechaFacComercial = null;
		String facturaComercial = null;
		String vapAvi = null;
		Integer nuCont20 = null;
		Integer nuCont40 = null;
		Integer voBo = null;
		Integer facDolares = null;
		Integer tipo = null;

		ArrayNode mercancia = null;
		ArrayNode mercanciaDetalle = null;

		try {
			root = new ObjectMapper().readTree(request);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idRegimen = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", idRegimen);

			idAgenciaAduana = root.path("idAgenciaAduana").asLong();
			logger.debug("idAgenciaAduana: {}", idAgenciaAduana);

			idCotizacion = root.path("idCotizacion").asLong();
			logger.debug("idCotizacion: {}", idCotizacion);

			idUbicacion = root.path("idUbicacion").asLong();
			logger.debug("idUbicacion: {}", idUbicacion);

			idDepositoTemporal = root.path("idDepositoTemporal").asInt();
			logger.debug("idDepositoTemporal: {}", idDepositoTemporal);

			fecha = root.path("fecha").asText();
			logger.debug("fecha: {}", fecha);

			docTransporte = root.path("docTransporte").asText();
			logger.debug("docTransporte: {}", docTransporte);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			nroDeclaracion = root.path("nroDeclaracion").asText();
			logger.debug("nroDeclaracion: {}", nroDeclaracion);

			nro_tarjeta = root.path("nro_tarjeta").asText();
			logger.debug("nro_tarjeta: {}", nro_tarjeta);

			fechaNumeracion = root.path("fechaNumeracion").asText();
			logger.debug("fechaNumeracion: {}", fechaNumeracion);

			manifiesto = root.path("manifiesto").asText();
			logger.debug("manifiesto: {}", manifiesto);

			fechaVencimiento = root.path("fechaVencimiento").asText();
			logger.debug("fechaVencimiento: {}", fechaVencimiento);

			dam = root.path("dam").asText();
			logger.debug("dam: {}", dam);

			subPartidaNacional = root.path("subPartidaNacional").asText();
			logger.debug("subPartidaNacional: {}", subPartidaNacional);

			fechaFacComercial = root.path("fechaFacComercial").asText();
			logger.debug("fechaFacComercial: {}", fechaFacComercial);

			facturaComercial = root.path("facturaComercial").asText();
			logger.debug("facturaComercial: {}", facturaComercial);

			vapAvi = root.path("vapAvi").asText();
			logger.debug("vapAvi: {}", vapAvi);

			nuCont20 = root.path("nuCont20").asInt();
			logger.debug("nuCont20: {}", nuCont20);

			nuCont40 = root.path("nuCont40").asInt();
			logger.debug("nuCont40: {}", nuCont40);

			tipo = root.path("tipo").asInt();
			logger.debug("tipo: {}", tipo);

			voBo = root.path("voBo").asInt();
			logger.debug("voBo: {}", voBo);

			facDolares = root.path("facDolares").asInt();
			logger.debug("facDolares: {}", facDolares);

			mercancia = (ArrayNode) root.path("mercancia");
			logger.debug("Total líneas: {}", mercancia.size());

			mercanciaDetalle = (ArrayNode) root.path("mercanciaDetalle");
			logger.debug("Total mercanciaDetalle: {}", mercanciaDetalle.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
				.orElseThrow(() -> new ApiException(ApiError.COTIZACION_NOT_FOUND.getCode(),
						ApiError.COTIZACION_NOT_FOUND.getMessage()));

		Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
				.orElseThrow(() -> new ApiException(ApiError.UBICACION_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		DepositoTemporal depositoTemporal = depositoTemporalRepository.findById(idDepositoTemporal)
				.orElseThrow(() -> new ApiException(ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		Regimen regimen = regimenRepository.findById(idRegimen)
				.orElseThrow(() -> new ApiException(ApiError.REGIMEN_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		AgenciasAduanas agenciasAduanas = agenciaAduanasRepository.findById(idAgenciaAduana)
				.orElseThrow(() -> new ApiException(ApiError.AGENCIA_ADUANA_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));
		try {
			Tarjeta tarjeta = new Tarjeta();
			Mercancia mercaderia = new Mercancia();
			List<MercanciaDetalle> listMercancia = new ArrayList<>();
			List<Movimiento> listMovimiento = new ArrayList<>();
			MercanciaDetalle detalles = new MercanciaDetalle();

			for (JsonNode node : mercanciaDetalle) {

				String descripcion = node.path("descripcion").asText();
				logger.debug("descripcion: {}", descripcion);

				double valorUS$ = node.path("valorUS$").asDouble();
				logger.debug("valorUS$: {}", valorUS$);

				int bueno = node.path("bueno").asInt();
				logger.debug("bueno: {}", bueno);

				int cantidad = node.path("cantidad").asInt();
				logger.debug("cantidad: {}", cantidad);

				int diferencia = node.path("diferenacia").asInt();
				logger.debug("diferencia: {}", diferencia);

				String motivo = node.path("motivo").asText();
				logger.debug("motivo: {}", motivo);

				MercanciaDetalle detalle = new MercanciaDetalle();
				Movimiento movimiento = new Movimiento();

				movimiento.setFechaMovimiento(new Date());
				movimiento.setHoraMovimiento(DateUtil.getCurrentHour());
				movimiento.setTipo("I");
				movimiento.setAnulado("SA");
				movimiento.setMercaderia_detalle(detalle);
				movimiento.setCantidad(cantidad);
				listMovimiento.add(movimiento);

				detalle.setBueno(bueno);
				detalle.setCantidad(cantidad);
				detalle.setDescripcion(descripcion);
				detalle.setValorUS$(valorUS$);
				detalle.setMercancia(mercaderia);
				detalle.setDiferencia(diferencia);
				detalle.setMotivo(motivo);
				detalle.setListmovimiento(listMovimiento);
				listMercancia.add(detalle);

			}

			for (JsonNode node : mercancia) {

				int idtipomercancia = node.path("idtipomercancia").asInt();
				logger.debug("idtipomercancia: {}", idtipomercancia);

				int idTipoBulto = node.path("idTipoBulto").asInt();
				logger.debug("idTipoBulto: {}", idTipoBulto);

				int ajuste = node.path("ajuste").asInt();
				logger.debug("ajuste: {}", ajuste);

				int flete = node.path("flete").asInt();
				logger.debug("flete: {}", flete);

				int seguro = node.path("seguro").asInt();
				logger.debug("seguro: {}", seguro);

				int fob = node.path("fob").asInt();
				logger.debug("fob: {}", fob);

				Integer levanteMercaderia = node.path("levanteMercaderia").asInt();
				logger.debug("levanteMercaderia: {}", levanteMercaderia);

				mercaderia.setAjuste(ajuste);
				mercaderia.setFlete(flete);
				mercaderia.setTipoMercancia(tipoMercanciaRepository.findById(idtipomercancia)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_MERCANCIA_NOT_FOUND.getCode(),
								ApiError.UBICACION_NOT_FOUND.getMessage())));
				mercaderia.setLevanteMercancia(levanteMercaderia);
				mercaderia.setSeguro(seguro);
				mercaderia.setFob(fob);
				mercaderia.setTipoBulto(tipoBultoRepository.findById(idTipoBulto)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_BULTO_NOT_FOUND.getCode(),
								ApiError.UBICACION_NOT_FOUND.getMessage())));
				mercaderia.setTarjeta(tarjeta);
				mercaderia.setMercanciaDetalle(listMercancia);
			}

			tarjeta.setCliente(cliente);
			tarjeta.setCotizacion(cotizacion);
			tarjeta.setUbicacion(ubicacion);
			tarjeta.setDepositoTemporal(depositoTemporal);
			tarjeta.setNroDeclaracion(nroDeclaracion);
			tarjeta.setFecha(DateUtil.of(fecha));
			tarjeta.setFecha_numeracióN(DateUtil.of(fechaNumeracion));
			tarjeta.setFecha_vencimiento(DateUtil.of(fechaVencimiento));
			tarjeta.setFechaFacComercial(DateUtil.of(fechaFacComercial));
			tarjeta.setVapAvi(vapAvi);
			tarjeta.setNro_tarjeta(nro_tarjeta);
			tarjeta.setManifiesto(manifiesto);
			tarjeta.setDam(dam);
			tarjeta.setSubPartidaNacional(subPartidaNacional);
			tarjeta.setFacturaComercial(facturaComercial);
			tarjeta.setDocTransporte(docTransporte);
			tarjeta.setIsFacturacionDolares(facDolares);
			tarjeta.setObservacion(observaciones);
			tarjeta.setIsvobo(voBo);
			tarjeta.setNuCont20(nuCont20);
			tarjeta.setNuCont40(nuCont40);
			tarjeta.setMercancia(mercaderia);
			tarjeta.setRegimen(regimen);
			tarjeta.setAgenciasAduanas(agenciasAduanas);
			tarjeta.setTipo(tipo);
			tarjeta.setContenedor(null);

			tarjeta.setFechaLlegada(null);
			tarjeta.setNave(null);
			tarjeta.setNaviera(null);
			tarjeta.setBlMaster(null);
			tarjeta.setAgenteMaritimo(null);
			tarjeta.setBlsHijos(null);
			tarjeta.setImportador(null);
			tarjeta.setTipoCarga(null);
			tarjeta.setNumeroPaletas(null);
			tarjeta.setTipoDeposito(null);

			tarjeta = tarjetaRepository.save(tarjeta);
			logger.debug("Se guardó Tarjeta");

			responseTarjeta = new TarjetaContext(tarjeta, tarjeta.getMercancia(), mercaderia.getMercanciaDetalle(),
					detalles.getListmovimiento());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTarjeta);
	}

	public ApiResponse saveWithDepositTemp(String request) throws ApiException {
		TarjetaContext responseTarjeta;
		JsonNode root;
		Long idCliente = null;
		Long idCotizacion = null;
		Long idUbicacion = null;
		Long idAgenciaAduana = null;
		Integer idRegimen = null;
		Integer idDepositoTemporal = null;
		String docTransporte = null;
		String observaciones = null;
		String nroDeclaracion = null;
		String fecha = null;
		String nro_tarjeta = null;
		String fechaNumeracion = null;
		String manifiesto = null;
		String fechaVencimiento = null;
		String dam = null;
		String subPartidaNacional = null;
		String fechaFacComercial = null;
		String facturaComercial = null;
		String vapAvi = null;
		Integer nuCont20 = null;
		Integer nuCont40 = null;
		Integer voBo = null;
		Integer facDolares = null;
		Integer tipo = null;

		// new fields
		String fechaLlegada = null;
		String nave = null;
		String naviera = null;
		String blMaster = null;
		String agenteMaritimo = null;
		String blsHijos = null;
		Long idImportador = null;
		Integer tipoCarga = null;
		Integer numeroPaletas = null;
		Integer tipoDeposito = null;

		ArrayNode mercancia = null;
		ArrayNode mercanciaDetalle = null;
		ArrayNode contenedor = null;
		ArrayNode contenedorDetalle = null;
		try {
			root = new ObjectMapper().readTree(request);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idRegimen = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", idRegimen);

			idAgenciaAduana = root.path("idAgenciaAduana").asLong();
			logger.debug("idAgenciaAduana: {}", idAgenciaAduana);

			idCotizacion = root.path("idCotizacion").asLong();
			logger.debug("idCotizacion: {}", idCotizacion);

			idUbicacion = root.path("idUbicacion").asLong();
			logger.debug("idUbicacion: {}", idUbicacion);

			idDepositoTemporal = root.path("idDepositoTemporal").asInt();
			logger.debug("idDepositoTemporal: {}", idDepositoTemporal);

			fecha = root.path("fecha").asText();
			logger.debug("fecha: {}", fecha);

			docTransporte = root.path("docTransporte").asText();
			logger.debug("docTransporte: {}", docTransporte);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			nroDeclaracion = root.path("nroDeclaracion").asText();
			logger.debug("nroDeclaracion: {}", nroDeclaracion);

			nro_tarjeta = root.path("nro_tarjeta").asText();
			logger.debug("nro_tarjeta: {}", nro_tarjeta);

			fechaNumeracion = root.path("fechaNumeracion").asText();
			logger.debug("fechaNumeracion: {}", fechaNumeracion);

			manifiesto = root.path("manifiesto").asText();
			logger.debug("manifiesto: {}", manifiesto);

			fechaVencimiento = root.path("fechaVencimiento").asText();
			logger.debug("fechaVencimiento: {}", fechaVencimiento);

			dam = root.path("dam").asText();
			logger.debug("dam: {}", dam);

			subPartidaNacional = root.path("subPartidaNacional").asText();
			logger.debug("subPartidaNacional: {}", subPartidaNacional);

			fechaFacComercial = root.path("fechaFacComercial").asText();
			logger.debug("fechaFacComercial: {}", fechaFacComercial);

			facturaComercial = root.path("facturaComercial").asText();
			logger.debug("facturaComercial: {}", facturaComercial);

			vapAvi = root.path("vapAvi").asText();
			logger.debug("vapAvi: {}", vapAvi);

			nuCont20 = root.path("nuCont20").asInt();
			logger.debug("nuCont20: {}", nuCont20);

			nuCont40 = root.path("nuCont40").asInt();
			logger.debug("nuCont40: {}", nuCont40);

			tipo = root.path("tipo").asInt();
			logger.debug("tipo: {}", tipo);

			voBo = root.path("voBo").asInt();
			logger.debug("voBo: {}", voBo);

			facDolares = root.path("facDolares").asInt();
			logger.debug("facDolares: {}", facDolares);

			// new fields

			fechaLlegada = root.path("fechaLlegada").asText();
			logger.debug("fechaLlegada: {}", fechaLlegada);

			nave = root.path("nave").asText();
			logger.debug("nave: {}", nave);

			naviera = root.path("naviera").asText();
			logger.debug("naviera: {}", naviera);

			blMaster = root.path("blMaster").asText();
			logger.debug("blMaster: {}", blMaster);

			agenteMaritimo = root.path("agenteMaritimo").asText();
			logger.debug("agenteMaritimo: {}", agenteMaritimo);

			blsHijos = root.path("blsHijos").asText();
			logger.debug("blsHijos: {}", blsHijos);

			idImportador = root.path("idImportador").asLong();
			logger.debug("idImportador: {}", idImportador);

			tipoCarga = root.path("tipoCarga").asInt();
			logger.debug("tipoCarga: {}", tipoCarga);

			numeroPaletas = root.path("numeroPaletas").asInt();
			logger.debug("numeroPaletas: {}", numeroPaletas);

			tipoDeposito = root.path("tipoDeposito").asInt();
			logger.debug("tipoDeposito: {}", tipoDeposito);

			mercancia = (ArrayNode) root.path("mercancia");
			logger.debug("Total líneas: {}", mercancia.size());

			mercanciaDetalle = (ArrayNode) root.path("mercanciaDetalle");
			logger.debug("Total mercanciaDetalle: {}", mercanciaDetalle.size());

			contenedor = (ArrayNode) root.path("contenedor");
			logger.debug("Total contenedor: {}", contenedor.size());

			contenedorDetalle = (ArrayNode) root.path("contenedorDetalle");
			logger.debug("Total contenedorDetalle: {}", contenedorDetalle.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Cliente importador = clienteRepository.findById(idImportador).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
				.orElseThrow(() -> new ApiException(ApiError.COTIZACION_NOT_FOUND.getCode(),
						ApiError.COTIZACION_NOT_FOUND.getMessage()));

		Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
				.orElseThrow(() -> new ApiException(ApiError.UBICACION_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		DepositoTemporal depositoTemporal = depositoTemporalRepository.findById(idDepositoTemporal)
				.orElseThrow(() -> new ApiException(ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		Regimen regimen = regimenRepository.findById(idRegimen)
				.orElseThrow(() -> new ApiException(ApiError.REGIMEN_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		AgenciasAduanas agenciasAduanas = agenciaAduanasRepository.findById(idAgenciaAduana)
				.orElseThrow(() -> new ApiException(ApiError.AGENCIA_ADUANA_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));
		try {
			Tarjeta tarjeta = new Tarjeta();
			Mercancia mercaderia = new Mercancia();
			List<MercanciaDetalle> listMercancia = new ArrayList<>();
			List<Movimiento> listMovimiento = new ArrayList<>();
			MercanciaDetalle detalles = new MercanciaDetalle();

			Contenedor contenedorBeans = new Contenedor();
			List<ContenedorDetalle> listContenedor = new ArrayList<>();

			for (JsonNode node : contenedorDetalle) {

				String numero_precintos = node.path("numero_precintos").asText();
				logger.debug("numero_precintos: {}", numero_precintos);

				ContenedorDetalle detalleC = new ContenedorDetalle();

				detalleC.setNumero_precintos(numero_precintos);
				detalleC.setContenedor(contenedorBeans);
				listContenedor.add(detalleC);

			}

			for (JsonNode node : contenedor) {

				String camion = node.path("camion").asText();
				logger.debug("camion: {}", camion);

				String contenedorArray = node.path("contenedor").asText();
				logger.debug("contenedor: {}", contenedorArray);

				String documento_sini = node.path("documento_sini").asText();
				logger.debug("documento_sini: {}", documento_sini);

				String doc_traslado = node.path("doc_traslado").asText();
				logger.debug("doc_traslado: {}", doc_traslado);

				String ticket_puerto = node.path("ticket_puerto").asText();
				logger.debug("ticket_puerto: {}", ticket_puerto);

				contenedorBeans.setCamion(camion);
				contenedorBeans.setContenedor(contenedorArray);
				contenedorBeans.setDoc_traslado(doc_traslado);
				contenedorBeans.setTicket_puerto(ticket_puerto);
				contenedorBeans.setDocumento_sini(documento_sini);
				contenedorBeans.setTarjeta(tarjeta);
				contenedorBeans.setListaDetalleContenedor(listContenedor);
			}

			for (JsonNode node : mercanciaDetalle) {

				String descripcion = node.path("descripcion").asText();
				logger.debug("descripcion: {}", descripcion);

				double valorUS$ = node.path("valorUS$").asDouble();
				logger.debug("valorUS$: {}", valorUS$);

				int bueno = node.path("bueno").asInt();
				logger.debug("bueno: {}", bueno);

				int cantidad = node.path("cantidad").asInt();
				logger.debug("cantidad: {}", cantidad);

				int diferencia = node.path("diferenacia").asInt();
				logger.debug("diferencia: {}", diferencia);

				String motivo = node.path("motivo").asText();
				logger.debug("motivo: {}", motivo);

				MercanciaDetalle detalle = new MercanciaDetalle();
				Movimiento movimiento = new Movimiento();

				movimiento.setFechaMovimiento(new Date());
				movimiento.setHoraMovimiento(DateUtil.getCurrentHour());
				movimiento.setTipo("I");
				movimiento.setAnulado("SA");
				movimiento.setMercaderia_detalle(detalle);
				movimiento.setCantidad(cantidad);
				listMovimiento.add(movimiento);

				detalle.setBueno(bueno);
				detalle.setCantidad(cantidad);
				detalle.setDescripcion(descripcion);
				detalle.setValorUS$(valorUS$);
				detalle.setMercancia(mercaderia);
				detalle.setDiferencia(diferencia);
				detalle.setMotivo(motivo);
				detalle.setListmovimiento(listMovimiento);
				listMercancia.add(detalle);

			}

			for (JsonNode node : mercancia) {

				int idtipomercancia = node.path("idtipomercancia").asInt();
				logger.debug("idtipomercancia: {}", idtipomercancia);

				int idTipoBulto = node.path("idTipoBulto").asInt();
				logger.debug("idTipoBulto: {}", idTipoBulto);

				int ajuste = node.path("ajuste").asInt();
				logger.debug("ajuste: {}", ajuste);

				int flete = node.path("flete").asInt();
				logger.debug("flete: {}", flete);

				int seguro = node.path("seguro").asInt();
				logger.debug("seguro: {}", seguro);

				int fob = node.path("fob").asInt();
				logger.debug("fob: {}", fob);

				Integer levanteMercaderia = node.path("levanteMercaderia").asInt();
				logger.debug("levanteMercaderia: {}", levanteMercaderia);

				mercaderia.setAjuste(ajuste);
				mercaderia.setFlete(flete);
				mercaderia.setTipoMercancia(tipoMercanciaRepository.findById(idtipomercancia)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_MERCANCIA_NOT_FOUND.getCode(),
								ApiError.UBICACION_NOT_FOUND.getMessage())));
				mercaderia.setLevanteMercancia(levanteMercaderia);
				mercaderia.setSeguro(seguro);
				mercaderia.setFob(fob);
				mercaderia.setTipoBulto(tipoBultoRepository.findById(idTipoBulto)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_BULTO_NOT_FOUND.getCode(),
								ApiError.UBICACION_NOT_FOUND.getMessage())));
				mercaderia.setTarjeta(tarjeta);
				mercaderia.setMercanciaDetalle(listMercancia);
			}

			tarjeta.setCliente(cliente);
			tarjeta.setCotizacion(cotizacion);
			tarjeta.setUbicacion(ubicacion);
			tarjeta.setDepositoTemporal(depositoTemporal);
			tarjeta.setNroDeclaracion(nroDeclaracion);
			tarjeta.setFecha(DateUtil.of(fecha));
			tarjeta.setFecha_numeracióN(DateUtil.of(fechaNumeracion));
			tarjeta.setFecha_vencimiento(DateUtil.of(fechaVencimiento));
			tarjeta.setFechaFacComercial(DateUtil.of(fechaFacComercial));
			tarjeta.setVapAvi(vapAvi);
			tarjeta.setNro_tarjeta(nro_tarjeta);
			tarjeta.setManifiesto(manifiesto);
			tarjeta.setDam(dam);
			tarjeta.setSubPartidaNacional(subPartidaNacional);
			tarjeta.setFacturaComercial(facturaComercial);
			tarjeta.setDocTransporte(docTransporte);
			tarjeta.setIsFacturacionDolares(facDolares);
			tarjeta.setObservacion(observaciones);
			tarjeta.setIsvobo(voBo);
			tarjeta.setNuCont20(nuCont20);
			tarjeta.setNuCont40(nuCont40);
			tarjeta.setMercancia(mercaderia);
			tarjeta.setContenedor(contenedorBeans);
			tarjeta.setRegimen(regimen);
			tarjeta.setAgenciasAduanas(agenciasAduanas);
			tarjeta.setTipo(tipo);

			// new asign
			tarjeta.setFechaLlegada(LocalDateTime.parse(fechaLlegada));
			tarjeta.setNave(nave);
			tarjeta.setNaviera(naviera);
			tarjeta.setBlMaster(blMaster);
			tarjeta.setAgenteMaritimo(agenteMaritimo);
			tarjeta.setBlsHijos(blsHijos);
			tarjeta.setImportador(importador);
			tarjeta.setTipoCarga(tipoCarga);
			tarjeta.setNumeroPaletas(numeroPaletas);
			tarjeta.setTipoDeposito(tipoDeposito);

			tarjeta = tarjetaRepository.save(tarjeta);
			logger.debug("Se guardó Tarjeta");

			responseTarjeta = new TarjetaContext(tarjeta, tarjeta.getMercancia(), mercaderia.getMercanciaDetalle(),
					detalles.getListmovimiento(), tarjeta.getContenedor(), contenedorBeans.getListaDetalleContenedor());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTarjeta);
	}

	public ApiResponse update(String request) throws ApiException {
		TarjetaContext responseTarjeta;
		JsonNode root;
		Long idTarjeta = null;
		Long idCliente = null;
		Long idCotizacion = null;
		Long idUbicacion = null;
		Long idAgenciaAduana = null;
		Integer idRegimen = null;
		Integer idDepositoTemporal = null;
		String docTransporte = null;
		String observaciones = null;
		String nroDeclaracion = null;
		String fecha = null;
		String nro_tarjeta = null;
		String fechaNumeracion = null;
		String manifiesto = null;
		String fechaVencimiento = null;
		String dam = null;
		String subPartidaNacional = null;
		String fechaFacComercial = null;
		String facturaComercial = null;
		String vapAvi = null;
		Integer nuCont20 = null;
		Integer nuCont40 = null;
		Integer voBo = null;
		Integer facDolares = null;
		Integer tipo = null;

		ArrayNode mercancia = null;
		ArrayNode mercanciaDetalle = null;

		try {
			root = new ObjectMapper().readTree(request);

			idTarjeta = root.path("idTarjeta").asLong();
			logger.debug("idTarjeta: {}", idTarjeta);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idRegimen = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", idRegimen);

			idAgenciaAduana = root.path("idAgenciaAduana").asLong();
			logger.debug("idAgenciaAduana: {}", idAgenciaAduana);

			idCotizacion = root.path("idCotizacion").asLong();
			logger.debug("idCotizacion: {}", idCotizacion);

			idUbicacion = root.path("idUbicacion").asLong();
			logger.debug("idUbicacion: {}", idUbicacion);

			idDepositoTemporal = root.path("idDepositoTemporal").asInt();
			logger.debug("idDepositoTemporal: {}", idDepositoTemporal);

			fecha = root.path("fecha").asText();
			logger.debug("fecha: {}", fecha);

			docTransporte = root.path("docTransporte").asText();
			logger.debug("docTransporte: {}", docTransporte);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			nroDeclaracion = root.path("nroDeclaracion").asText();
			logger.debug("nroDeclaracion: {}", nroDeclaracion);

			nro_tarjeta = root.path("nro_tarjeta").asText();
			logger.debug("nro_tarjeta: {}", nro_tarjeta);

			fechaNumeracion = root.path("fechaNumeracion").asText();
			logger.debug("fechaNumeracion: {}", fechaNumeracion);

			manifiesto = root.path("manifiesto").asText();
			logger.debug("manifiesto: {}", manifiesto);

			fechaVencimiento = root.path("fechaVencimiento").asText();
			logger.debug("fechaVencimiento: {}", fechaVencimiento);

			dam = root.path("dam").asText();
			logger.debug("dam: {}", dam);

			subPartidaNacional = root.path("subPartidaNacional").asText();
			logger.debug("subPartidaNacional: {}", subPartidaNacional);

			fechaFacComercial = root.path("fechaFacComercial").asText();
			logger.debug("fechaFacComercial: {}", fechaFacComercial);

			facturaComercial = root.path("facturaComercial").asText();
			logger.debug("facturaComercial: {}", facturaComercial);

			vapAvi = root.path("vapAvi").asText();
			logger.debug("vapAvi: {}", vapAvi);

			nuCont20 = root.path("nuCont20").asInt();
			logger.debug("nuCont20: {}", nuCont20);

			nuCont40 = root.path("nuCont40").asInt();
			logger.debug("nuCont40: {}", nuCont40);

			tipo = root.path("tipo").asInt();
			logger.debug("tipo: {}", tipo);

			voBo = root.path("voBo").asInt();
			logger.debug("voBo: {}", voBo);

			facDolares = root.path("facDolares").asInt();
			logger.debug("facDolares: {}", facDolares);

			mercancia = (ArrayNode) root.path("mercancia");
			logger.debug("Total líneas: {}", mercancia.size());

			mercanciaDetalle = (ArrayNode) root.path("mercanciaDetalle");
			logger.debug("Total mercanciaDetalle: {}", mercanciaDetalle.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
				.orElseThrow(() -> new ApiException(ApiError.COTIZACION_NOT_FOUND.getCode(),
						ApiError.COTIZACION_NOT_FOUND.getMessage()));

		Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
				.orElseThrow(() -> new ApiException(ApiError.UBICACION_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		DepositoTemporal depositoTemporal = depositoTemporalRepository.findById(idDepositoTemporal)
				.orElseThrow(() -> new ApiException(ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		Regimen regimen = regimenRepository.findById(idRegimen)
				.orElseThrow(() -> new ApiException(ApiError.REGIMEN_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		AgenciasAduanas agenciasAduanas = agenciaAduanasRepository.findById(idAgenciaAduana)
				.orElseThrow(() -> new ApiException(ApiError.AGENCIA_ADUANA_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));
		try {
			Tarjeta tarjeta = new Tarjeta();
			Mercancia mercaderia = new Mercancia();
			MercanciaDetalle detalles = new MercanciaDetalle();

			for (JsonNode node : mercancia) {

				Long idMercancia = node.path("idMercancia").asLong();
				logger.debug("idMercancia: {}", idMercancia);

				int idtipomercancia = node.path("idtipomercancia").asInt();
				logger.debug("idtipomercancia: {}", idtipomercancia);

				int idTipoBulto = node.path("idTipoBulto").asInt();
				logger.debug("idTipoBulto: {}", idTipoBulto);

				int ajuste = node.path("ajuste").asInt();
				logger.debug("ajuste: {}", ajuste);

				int flete = node.path("flete").asInt();
				logger.debug("flete: {}", flete);

				int seguro = node.path("seguro").asInt();
				logger.debug("seguro: {}", seguro);

				int fob = node.path("fob").asInt();
				logger.debug("fob: {}", fob);

				Integer levanteMercaderia = node.path("levanteMercaderia").asInt();
				logger.debug("levanteMercaderia: {}", levanteMercaderia);
				mercaderia.setIdMercancia(idMercancia);
				mercaderia.setAjuste(ajuste);
				mercaderia.setFlete(flete);
				mercaderia.setFob(fob);
				mercaderia.setTipoMercancia(tipoMercanciaRepository.findById(idtipomercancia)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_MERCANCIA_NOT_FOUND.getCode(),
								ApiError.TIPO_MERCANCIA_NOT_FOUND.getMessage())));
				mercaderia.setLevanteMercancia(levanteMercaderia);
				mercaderia.setSeguro(seguro);
				mercaderia.setTipoBulto(tipoBultoRepository.findById(idTipoBulto)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_BULTO_NOT_FOUND.getCode(),
								ApiError.TIPO_BULTO_NOT_FOUND.getMessage())));
				mercaderia.setTarjeta(tarjeta);
			}

			tarjeta.setIdTarjeta(idTarjeta);
			tarjeta.setCliente(cliente);
			tarjeta.setCotizacion(cotizacion);
			tarjeta.setUbicacion(ubicacion);
			tarjeta.setDepositoTemporal(depositoTemporal);
			tarjeta.setNroDeclaracion(nroDeclaracion);
			tarjeta.setFecha(DateUtil.of(fecha));
			tarjeta.setFecha_numeracióN(DateUtil.of(fechaNumeracion));
			tarjeta.setFecha_vencimiento(DateUtil.of(fechaVencimiento));
			tarjeta.setFechaFacComercial(DateUtil.of(fechaFacComercial));
			tarjeta.setVapAvi(vapAvi);
			tarjeta.setNro_tarjeta(nro_tarjeta);
			tarjeta.setManifiesto(manifiesto);
			tarjeta.setDam(dam);
			tarjeta.setSubPartidaNacional(subPartidaNacional);
			tarjeta.setFacturaComercial(facturaComercial);
			tarjeta.setDocTransporte(docTransporte);
			tarjeta.setIsFacturacionDolares(facDolares);
			tarjeta.setObservacion(observaciones);
			tarjeta.setIsvobo(voBo);
			tarjeta.setNuCont20(nuCont20);
			tarjeta.setNuCont40(nuCont40);
			tarjeta.setMercancia(mercaderia);
			tarjeta.setRegimen(regimen);
			tarjeta.setAgenciasAduanas(agenciasAduanas);
			tarjeta.setTipo(tipo);
			tarjeta.setContenedor(null);

			tarjeta.setFechaLlegada(null);
			tarjeta.setNave(null);
			tarjeta.setNaviera(null);
			tarjeta.setBlMaster(null);
			tarjeta.setAgenteMaritimo(null);
			tarjeta.setBlsHijos(null);
			tarjeta.setImportador(null);
			tarjeta.setTipoCarga(null);
			tarjeta.setNumeroPaletas(null);
			tarjeta.setTipoDeposito(null);

			tarjeta = tarjetaRepository.save(tarjeta);
			logger.debug("Se guardó Tarjeta");

			responseTarjeta = new TarjetaContext(tarjeta, tarjeta.getMercancia(), mercaderia.getMercanciaDetalle(),
					detalles.getListmovimiento());

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTarjeta);
	}

	public ApiResponse updateWithDepTemp(String request) throws ApiException {
		TarjetaContext responseTarjeta;
		JsonNode root;
		Long idTarjeta = null;
		Long idCliente = null;
		Long idCotizacion = null;
		Long idUbicacion = null;
		Long idAgenciaAduana = null;
		Integer idRegimen = null;
		String nro_tarjeta = null;
		Integer idDepositoTemporal = null;
		String docTransporte = null;
		String observaciones = null;
		String nroDeclaracion = null;
		String fecha = null;
		String fechaNumeracion = null;
		String manifiesto = null;
		String fechaVencimiento = null;
		String dam = null;
		String subPartidaNacional = null;
		String fechaFacComercial = null;
		String facturaComercial = null;
		String vapAvi = null;
		Integer nuCont20 = null;
		Integer nuCont40 = null;
		Integer voBo = null;
		Integer facDolares = null;
		Integer tipo = null;

		// new fields
		String fechaLlegada = null;
		String nave = null;
		String naviera = null;
		String blMaster = null;
		String agenteMaritimo = null;
		String blsHijos = null;
		Long idImportador = null;
		Integer tipoCarga = null;
		Integer numeroPaletas = null;
		Integer tipoDeposito = null;

		ArrayNode mercancia = null;
		ArrayNode mercanciaDetalle = null;
		ArrayNode contenedor = null;
		ArrayNode contenedorDetalle = null;
		try {
			root = new ObjectMapper().readTree(request);

			idTarjeta = root.path("idTarjeta").asLong();
			logger.debug("idTarjeta: {}", idTarjeta);

			idCliente = root.path("idCliente").asLong();
			logger.debug("idCliente: {}", idCliente);

			idRegimen = root.path("idRegimen").asInt();
			logger.debug("idRegimen: {}", idRegimen);

			idAgenciaAduana = root.path("idAgenciaAduana").asLong();
			logger.debug("idAgenciaAduana: {}", idAgenciaAduana);

			idCotizacion = root.path("idCotizacion").asLong();
			logger.debug("idCotizacion: {}", idCotizacion);

			idUbicacion = root.path("idUbicacion").asLong();
			logger.debug("idUbicacion: {}", idUbicacion);

			idDepositoTemporal = root.path("idDepositoTemporal").asInt();
			logger.debug("idDepositoTemporal: {}", idDepositoTemporal);

			fecha = root.path("fecha").asText();
			logger.debug("fecha: {}", fecha);

			docTransporte = root.path("docTransporte").asText();
			logger.debug("docTransporte: {}", docTransporte);

			observaciones = root.path("observaciones").asText();
			logger.debug("observaciones: {}", observaciones);

			nroDeclaracion = root.path("nroDeclaracion").asText();
			logger.debug("nroDeclaracion: {}", nroDeclaracion);

			nro_tarjeta = root.path("nro_tarjeta").asText();
			logger.debug("nro_tarjeta: {}", nro_tarjeta);

			fechaNumeracion = root.path("fechaNumeracion").asText();
			logger.debug("fechaNumeracion: {}", fechaNumeracion);

			manifiesto = root.path("manifiesto").asText();
			logger.debug("manifiesto: {}", manifiesto);

			fechaVencimiento = root.path("fechaVencimiento").asText();
			logger.debug("fechaVencimiento: {}", fechaVencimiento);

			dam = root.path("dam").asText();
			logger.debug("dam: {}", dam);

			subPartidaNacional = root.path("subPartidaNacional").asText();
			logger.debug("subPartidaNacional: {}", subPartidaNacional);

			fechaFacComercial = root.path("fechaFacComercial").asText();
			logger.debug("fechaFacComercial: {}", fechaFacComercial);

			facturaComercial = root.path("facturaComercial").asText();
			logger.debug("facturaComercial: {}", facturaComercial);

			vapAvi = root.path("vapAvi").asText();
			logger.debug("vapAvi: {}", vapAvi);

			nuCont20 = root.path("nuCont20").asInt();
			logger.debug("nuCont20: {}", nuCont20);

			nuCont40 = root.path("nuCont40").asInt();
			logger.debug("nuCont40: {}", nuCont40);

			tipo = root.path("tipo").asInt();
			logger.debug("tipo: {}", tipo);

			voBo = root.path("voBo").asInt();
			logger.debug("voBo: {}", voBo);

			// new fields
			tipoDeposito = root.path("tipoDeposito").asInt();
			logger.debug("tipoDeposito: {}", tipoDeposito);

			fechaLlegada = root.path("fechaLlegada").asText();
			logger.debug("fechaLlegada: {}", fechaLlegada);

			nave = root.path("nave").asText();
			logger.debug("nave: {}", nave);

			naviera = root.path("naviera").asText();
			logger.debug("naviera: {}", naviera);

			blMaster = root.path("blMaster").asText();
			logger.debug("blMaster: {}", blMaster);

			agenteMaritimo = root.path("agenteMaritimo").asText();
			logger.debug("agenteMaritimo: {}", agenteMaritimo);

			blsHijos = root.path("blsHijos").asText();
			logger.debug("blsHijos: {}", blsHijos);

			idImportador = root.path("idImportador").asLong();
			logger.debug("idImportador: {}", idImportador);

			tipoCarga = root.path("tipoCarga").asInt();
			logger.debug("tipoCarga: {}", tipoCarga);

			numeroPaletas = root.path("numeroPaletas").asInt();
			logger.debug("numeroPaletas: {}", numeroPaletas);

			facDolares = root.path("facDolares").asInt();
			logger.debug("facDolares: {}", facDolares);

			mercancia = (ArrayNode) root.path("mercancia");
			logger.debug("Total líneas: {}", mercancia.size());

			mercanciaDetalle = (ArrayNode) root.path("mercanciaDetalle");
			logger.debug("Total mercanciaDetalle: {}", mercanciaDetalle.size());

			contenedor = (ArrayNode) root.path("contenedor");
			logger.debug("Total contenedor: {}", contenedor.size());

			contenedorDetalle = (ArrayNode) root.path("contenedorDetalle");
			logger.debug("Total contenedorDetalle: {}", contenedorDetalle.size());

		} catch (JsonProcessingException e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}

		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Cliente importador = clienteRepository.findById(idImportador).orElseThrow(
				() -> new ApiException(ApiError.CLIENTE_NOT_FOUND.getCode(), ApiError.CLIENTE_NOT_FOUND.getMessage()));

		Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
				.orElseThrow(() -> new ApiException(ApiError.COTIZACION_NOT_FOUND.getCode(),
						ApiError.COTIZACION_NOT_FOUND.getMessage()));

		Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
				.orElseThrow(() -> new ApiException(ApiError.UBICACION_NOT_FOUND.getCode(),
						ApiError.UBICACION_NOT_FOUND.getMessage()));

		DepositoTemporal depositoTemporal = depositoTemporalRepository.findById(idDepositoTemporal)
				.orElseThrow(() -> new ApiException(ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getCode(),
						ApiError.DEPOSITO_TEMPORAL_NOT_FOUND.getMessage()));

		Regimen regimen = regimenRepository.findById(idRegimen).orElseThrow(
				() -> new ApiException(ApiError.REGIMEN_NOT_FOUND.getCode(), ApiError.REGIMEN_NOT_FOUND.getMessage()));

		AgenciasAduanas agenciasAduanas = agenciaAduanasRepository.findById(idAgenciaAduana)
				.orElseThrow(() -> new ApiException(ApiError.AGENCIA_ADUANA_NOT_FOUND.getCode(),
						ApiError.AGENCIA_ADUANA_NOT_FOUND.getMessage()));
		try {
			Tarjeta tarjeta = new Tarjeta();
			Mercancia mercaderia = new Mercancia();
			// List<MercanciaDetalle> listMercancia = new ArrayList<>();

			Contenedor contenedorBeans = new Contenedor();
			List<ContenedorDetalle> listContenedor = new ArrayList<>();

			for (JsonNode node : contenedorDetalle) {

				Integer idDetalle = node.path("idDetalle").asInt();
				logger.debug("idDetalle: {}", idDetalle);

				String numero_precintos = node.path("numero_precintos").asText();
				logger.debug("numero_precintos: {}", numero_precintos);

				ContenedorDetalle detalleC = new ContenedorDetalle();
				if(idDetalle != null) {
					detalleC.setIdDetalle(idDetalle);
				}
				
				detalleC.setNumero_precintos(numero_precintos);
				detalleC.setContenedor(contenedorBeans);
				listContenedor.add(detalleC);

			}

			for (JsonNode node : contenedor) {

				int idContenedor = node.path("idContenedor").asInt();
				logger.debug("camion: {}", idContenedor);

				String camion = node.path("camion").asText();
				logger.debug("camion: {}", camion);

				String contenedorArray = node.path("contenedor").asText();
				logger.debug("contenedor: {}", contenedorArray);

				String documento_sini = node.path("documento_sini").asText();
				logger.debug("documento_sini: {}", documento_sini);

				String doc_traslado = node.path("doc_traslado").asText();
				logger.debug("doc_traslado: {}", doc_traslado);

				String ticket_puerto = node.path("ticket_puerto").asText();
				logger.debug("ticket_puerto: {}", ticket_puerto);

				contenedorBeans.setIdContenedor(idContenedor);
				contenedorBeans.setCamion(camion);
				contenedorBeans.setContenedor(contenedorArray);
				contenedorBeans.setDoc_traslado(doc_traslado);
				contenedorBeans.setTicket_puerto(ticket_puerto);
				contenedorBeans.setDocumento_sini(documento_sini);
				contenedorBeans.setTarjeta(tarjeta);
				contenedorBeans.setListaDetalleContenedor(listContenedor);
			}

			for (JsonNode node : mercancia) {

				Long idMercancia = node.path("idMercancia").asLong();
				logger.debug("idMercancia: {}", idMercancia);

				int idtipomercancia = node.path("idtipomercancia").asInt();
				logger.debug("idtipomercancia: {}", idtipomercancia);

				int idTipoBulto = node.path("idTipoBulto").asInt();
				logger.debug("idTipoBulto: {}", idTipoBulto);

				int ajuste = node.path("ajuste").asInt();
				logger.debug("ajuste: {}", ajuste);

				int flete = node.path("flete").asInt();
				logger.debug("flete: {}", flete);

				int seguro = node.path("seguro").asInt();
				logger.debug("seguro: {}", seguro);

				int fob = node.path("fob").asInt();
				logger.debug("fob: {}", fob);

				Integer levanteMercaderia = node.path("levanteMercaderia").asInt();
				logger.debug("levanteMercaderia: {}", levanteMercaderia);
				mercaderia.setIdMercancia(idMercancia);
				mercaderia.setAjuste(ajuste);
				mercaderia.setFlete(flete);
				mercaderia.setFob(fob);
				mercaderia.setTipoMercancia(tipoMercanciaRepository.findById(idtipomercancia)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_MERCANCIA_NOT_FOUND.getCode(),
								ApiError.TIPO_MERCANCIA_NOT_FOUND.getMessage())));
				mercaderia.setLevanteMercancia(levanteMercaderia);
				mercaderia.setSeguro(seguro);
				mercaderia.setTipoBulto(tipoBultoRepository.findById(idTipoBulto)
						.orElseThrow(() -> new ApiException(ApiError.TIPO_BULTO_NOT_FOUND.getCode(),
								ApiError.TIPO_BULTO_NOT_FOUND.getMessage())));
				mercaderia.setTarjeta(tarjeta);
			}

			tarjeta.setIdTarjeta(idTarjeta);
			tarjeta.setCliente(cliente);
			tarjeta.setCotizacion(cotizacion);
			tarjeta.setUbicacion(ubicacion);
			tarjeta.setDepositoTemporal(depositoTemporal);
			tarjeta.setNroDeclaracion(nroDeclaracion);
			tarjeta.setFecha(DateUtil.of(fecha));
			tarjeta.setFecha_numeracióN(DateUtil.of(fechaNumeracion));
			tarjeta.setFecha_vencimiento(DateUtil.of(fechaVencimiento));
			tarjeta.setFechaFacComercial(DateUtil.of(fechaFacComercial));
			tarjeta.setVapAvi(vapAvi);
			tarjeta.setNro_tarjeta(nro_tarjeta);
			tarjeta.setManifiesto(manifiesto);
			tarjeta.setDam(dam);
			tarjeta.setSubPartidaNacional(subPartidaNacional);
			tarjeta.setFacturaComercial(facturaComercial);
			tarjeta.setDocTransporte(docTransporte);
			tarjeta.setIsFacturacionDolares(facDolares);
			tarjeta.setObservacion(observaciones);
			tarjeta.setIsvobo(voBo);
			tarjeta.setNuCont20(nuCont20);
			tarjeta.setNuCont40(nuCont40);
			tarjeta.setMercancia(mercaderia);
			tarjeta.setContenedor(contenedorBeans);
			tarjeta.setRegimen(regimen);
			tarjeta.setAgenciasAduanas(agenciasAduanas);
			tarjeta.setTipo(tipo);

			// new asign
			tarjeta.setTipoDeposito(tipoDeposito);
			tarjeta.setFechaLlegada(LocalDateTime.parse(fechaLlegada));
			tarjeta.setNave(nave);
			tarjeta.setNaviera(naviera);
			tarjeta.setBlMaster(blMaster);
			tarjeta.setAgenteMaritimo(agenteMaritimo);
			tarjeta.setBlsHijos(blsHijos);
			tarjeta.setImportador(importador);
			tarjeta.setTipoCarga(tipoCarga);
			tarjeta.setNumeroPaletas(numeroPaletas);

			tarjeta = tarjetaRepository.save(tarjeta);
			logger.debug("Se guardó Tarjeta");

			responseTarjeta = new TarjetaContext(tarjeta, tarjeta.getMercancia(), mercaderia.getMercanciaDetalle(),
					null, null, null);

		} catch (Exception e) {
			throw new ApiException(ApiError.NO_APPLICATION_PROCESSED.getCode(),
					ApiError.NO_APPLICATION_PROCESSED.getMessage(), e.getMessage());
		}
		return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(), responseTarjeta);
	}

	public ApiResponse delete(Long id) throws ApiException {
		Tarjeta tmpTarjeta = tarjetaRepository.findById(id).orElse(null);
		if (null != tmpTarjeta) {
			tarjetaRepository.deleteById(id);
			logger.debug("Tarjeta eliminada");
			return ApiResponse.of(ApiError.SUCCESS.getCode(), ApiError.SUCCESS.getMessage(),
					"Tarjeta " + id + " eliminado");
		}
		throw new ApiException(ApiError.RESOURCE_NOT_FOUND.getCode(), ApiError.RESOURCE_NOT_FOUND.getMessage());
	}

}
