package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tarjeta")
public class Tarjeta implements Serializable {

	private static final long serialVersionUID = -4115263633408002233L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tarjeta")
	private Long idTarjeta;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_agencia_aduanas", nullable = false)
	private AgenciasAduanas agenciasAduanas;

	public AgenciasAduanas getAgenciasAduanas() {
		return this.agenciasAduanas;
	}

	public void setAgenciasAduanas(AgenciasAduanas agenciasAduanas) {
		this.agenciasAduanas = agenciasAduanas;
	}

	@ManyToOne
	@JoinColumn(name = "id_regimen", nullable = false)
	private Regimen regimen;

	public Regimen getRegimen() {
		return this.regimen;
	}

	public void setRegimen(Regimen regimen) {
		this.regimen = regimen;
	}

	@OneToOne
	@JoinColumn(name = "id_cotizacion", nullable = false)
	private Cotizacion cotizacion;

	@ManyToOne
	@JoinColumn(name = "id_ubicacion", nullable = false)
	private Ubicacion ubicacion;

	@ManyToOne
	@JoinColumn(name = "id_deposito", nullable = false)
	private DepositoTemporal depositoTemporal;

	@Column(name = "fecha", nullable = false)
	private Date fecha;

	@Column(name = "vap_avi", length = 45)
	private String vapAvi;

	@Column(name = "tipo", length = 45)
	private Integer tipo;

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	@Column(name = "nro_tarjeta")
	private String nro_tarjeta;

	public String getNro_tarjeta() {
		return this.nro_tarjeta;
	}

	public void setNro_tarjeta(String nro_tarjeta) {
		this.nro_tarjeta = nro_tarjeta;
	}

	@Column(name = "nro_declaracion")
	private String nroDeclaracion;

	@Column(name = "manifiesto")
	private String manifiesto;

	@Column(name = "fecha_numeración")
	private Date fecha_numeración;

	@Column(name = "fecha_vencimiento")
	private Date fecha_vencimiento;

	@Column(name = "dam")
	private String dam;

	@Column(name = "subpartida_nacional")
	private String subPartidaNacional;

	@Column(name = "fecha_fac_comercial")
	private Date fechaFacComercial;

	@Column(name = "factura_comercial")
	private String facturaComercial;

	@Column(name = "doc_transporte")
	private String docTransporte;

	@Column(name = "observacion")
	private String observacion;

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Column(name = "cont_20")
	private Integer nuCont20;

	public Integer getNuCont20() {
		return this.nuCont20;
	}

	public void setNuCont20(Integer nuCont20) {
		this.nuCont20 = nuCont20;
	}

	@Column(name = "cont_40")
	private Integer nuCont40;

	@Column(name = "isvobo")
	private Integer isvobo;

	@Column(name = "isfacturacion_dolares")
	private Integer isFacturacionDolares;

	public DepositoTemporal getDepositoTemporal() {
		return this.depositoTemporal;
	}

	public void setDepositoTemporal(DepositoTemporal depositoTemporal) {
		this.depositoTemporal = depositoTemporal;
	}

	public String getNroDeclaracion() {
		return this.nroDeclaracion;
	}

	public void setNroDeclaracion(String nroDeclaracion) {
		this.nroDeclaracion = nroDeclaracion;
	}

	public String getManifiesto() {
		return this.manifiesto;
	}

	public void setManifiesto(String manifiesto) {
		this.manifiesto = manifiesto;
	}

	public Date getFecha_numeracióN() {
		return this.fecha_numeración;
	}

	public void setFecha_numeracióN(Date fecha_numeración) {
		this.fecha_numeración = fecha_numeración;
	}

	public Date getFecha_vencimiento() {
		return this.fecha_vencimiento;
	}

	public void setFecha_vencimiento(Date fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public String getDam() {
		return this.dam;
	}

	public void setDam(String dam) {
		this.dam = dam;
	}

	public String getSubPartidaNacional() {
		return this.subPartidaNacional;
	}

	public void setSubPartidaNacional(String subPartidaNacional) {
		this.subPartidaNacional = subPartidaNacional;
	}

	public Date getFechaFacComercial() {
		return this.fechaFacComercial;
	}

	public void setFechaFacComercial(Date fechaFacComercial) {
		this.fechaFacComercial = fechaFacComercial;
	}

	public String getFacturaComercial() {
		return this.facturaComercial;
	}

	public void setFacturaComercial(String facturaComercial) {
		this.facturaComercial = facturaComercial;
	}

	public String getDocTransporte() {
		return this.docTransporte;
	}

	public void setDocTransporte(String docTransporte) {
		this.docTransporte = docTransporte;
	}

	public Integer getNuCont_40() {
		return this.nuCont40;
	}

	public void setNuCont_40(Integer nuCont40) {
		this.nuCont40 = nuCont40;
	}

	public Integer getIsvobo() {
		return this.isvobo;
	}

	public void setIsvobo(Integer isvobo) {
		this.isvobo = isvobo;
	}

	public Integer getIsFacturacionDolares() {
		return this.isFacturacionDolares;
	}

	public void setIsFacturacionDolares(Integer isFacturacionDolares) {
		this.isFacturacionDolares = isFacturacionDolares;
	}

	public Long getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(Long idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getVapAvi() {
		return vapAvi;
	}

	public void setVapAvi(String vapAvi) {
		this.vapAvi = vapAvi;
	}

	public Integer getNuCont40() {
		return nuCont40;
	}

	public void setNuCont40(Integer nuCont40) {
		this.nuCont40 = nuCont40;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "tarjeta", cascade = CascadeType.ALL)
	private Mercancia mercancia;

	public Mercancia getMercancia() {
		return this.mercancia;
	}

	public void setMercancia(Mercancia mercancia) {
		this.mercancia = mercancia;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "tarjeta", cascade = CascadeType.ALL)
	private Contenedor contenedor;

	public Contenedor getContenedor() {
		return this.contenedor;
	}

	public void setContenedor(Contenedor contenedor) {
		this.contenedor = contenedor;
	}

	@Column(name = "fecha_llegada")
	private LocalDateTime fechaLlegada;

	@Column(name = "nave")
	private String nave;

	@Column(name = "naviera")
	private String naviera;

	@Column(name = "bl_master")
	private String blMaster;

	@Column(name = "agente_maritimo")
	private String agenteMaritimo;

	@Column(name = "bls_hijos")
	private String blsHijos;

	@ManyToOne
	@JoinColumn(name = "id_importador")
	private Cliente importador;

	@Column(name = "tipo_carga")
	private Integer tipoCarga;

	@Column(name = "tipo_deposito")
	private Integer tipoDeposito;

	@Column(name = "numero_paletas")
	private Integer numeroPaletas;

	public LocalDateTime getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(LocalDateTime fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getNave() {
		return nave;
	}

	public void setNave(String nave) {
		this.nave = nave;
	}

	public String getNaviera() {
		return naviera;
	}

	public void setNaviera(String naviera) {
		this.naviera = naviera;
	}

	public String getBlMaster() {
		return blMaster;
	}

	public void setBlMaster(String blMaster) {
		this.blMaster = blMaster;
	}

	public String getAgenteMaritimo() {
		return agenteMaritimo;
	}

	public void setAgenteMaritimo(String agenteMaritimo) {
		this.agenteMaritimo = agenteMaritimo;
	}

	public String getBlsHijos() {
		return blsHijos;
	}

	public void setBlsHijos(String blsHijos) {
		this.blsHijos = blsHijos;
	}

	public Cliente getImportador() {
		return importador;
	}

	public void setImportador(Cliente importador) {
		this.importador = importador;
	}

	public Integer getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(Integer tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public Integer getNumeroPaletas() {
		return numeroPaletas;
	}

	public void setNumeroPaletas(Integer numeroPaletas) {
		this.numeroPaletas = numeroPaletas;
	}

	public Integer getTipoDeposito() {
		return tipoDeposito;
	}

	public void setTipoDeposito(Integer tipoDeposito) {
		this.tipoDeposito = tipoDeposito;
	}

}
