package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comprobante")
public class Comprobante extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 5453324594440345652L;

	@Id
	@Column(name = "id_comprobante", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idComprobante;

	@ManyToOne
	@JoinColumn(name = "id_serie", nullable = false)
	private SerieComprobante serie;

	@Column(name = "tipo_cambio", nullable = false)
	private Double tipoCambio;

	@Column(name = "nombre_cliente", nullable = false)
	private String nombreCliente;

	@Column(name = "email_cliente", nullable = false)
	private String emailCliente;

	@Column(name = "nu_documento_cliente")
	private String numeroDocumentoCliente;

	@Column(name = "direccion_cliente")
	private String direccionCliente;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_tarjeta")
	private Tarjeta tarjeta;

	@ManyToOne
	@JoinColumn(name = "id_moneda", nullable = false)
	private Moneda moneda;

	@ManyToOne
	@JoinColumn(name = "id_forma_pago", nullable = false)
	private FormaPago formaPago;

	@Column(name = "comprobante", nullable = false, length = 13)
	private String comprobanteFull;

	@Column(name = "fe_emision", nullable = false)
	private Date fechaEmision;

	@Column(name = "hr_emision", nullable = false)
	private String horaEmision;

	@Column(name = "fe_vencimiento", nullable = false)
	private Date fechaVencimiento;

	@Column(name = "referencia")
	private String referencia;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "op_gravadas", precision = 12, scale = 2, nullable = false)
	private Double opGravadas;

	@Column(name = "pct_igv", precision = 6, scale = 4, nullable = false)
	private Double pctIGV;

	@Column(name = "to_igv", precision = 12, scale = 2, nullable = false)
	private Double totalIGV;

	@Column(name = "to_monto", precision = 12, scale = 2, nullable = false)
	private Double totalMonto;

	@Column(name = "fe_ini_periodo")
	private Date fechaInicioPeriodo;

	@Column(name = "fe_fin_periodo")
	private Date fechaFinalPeriodo;

	@Column(name = "dias_periodo")
	private Integer diasPeriodo;

	@Column(name = "fg_anulado", length = 1, nullable = false)
	private String fgAnulado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fe_hr_anulacion")
	private Date fechaHoraAnulacion;

	@Column(name = "pct_detraccion", precision = 6, scale = 4)
	private Double pctDetraccion;

	@Column(name = "fg_detraccion", length = 1, nullable = false)
	private String fgDetraccion;

	@Column(name = "to_detraccion", precision = 12, scale = 2)
	private Double totalDetraccion;


	@Column(name = "tipo_servicio", nullable = false)
	private Integer tipoServicio;

	public Integer getTipoServicio() {
		return this.tipoServicio;
	}

	public void setTipoServicio(Integer tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "id_tidoc_ref")
	private TipoComprobante tipoComprobanteRef;

	@Column(name = "sustento", length = 100)
	private String sustento;

	@Column(name = "us_anula", length = 20)
	private String usuarioAnula;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "comprobante", cascade = CascadeType.ALL)
	private List<ComprobanteDetalle> lineas;

	public Integer getIdComprobante() {
		return idComprobante;
	}

	public void setIdComprobante(Integer idComprobante) {
		this.idComprobante = idComprobante;
	}

	public SerieComprobante getSerie() {
		return serie;
	}

	public void setSerie(SerieComprobante serie) {
		this.serie = serie;
	}

	public Double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getNumeroDocumentoCliente() {
		return numeroDocumentoCliente;
	}

	public void setNumeroDocumentoCliente(String numeroDocumentoCliente) {
		this.numeroDocumentoCliente = numeroDocumentoCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public String getComprobanteFull() {
		return comprobanteFull;
	}

	public void setComprobanteFull(String comprobanteFull) {
		this.comprobanteFull = comprobanteFull;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Double getOpGravadas() {
		return opGravadas;
	}

	public void setOpGravadas(Double opGravadas) {
		this.opGravadas = opGravadas;
	}

	public Double getPctIGV() {
		return pctIGV;
	}

	public void setPctIGV(Double pctIGV) {
		this.pctIGV = pctIGV;
	}

	public Double getTotalIGV() {
		return totalIGV;
	}

	public void setTotalIGV(Double totalIGV) {
		this.totalIGV = totalIGV;
	}

	public Double getTotalMonto() {
		return totalMonto;
	}

	public void setTotalMonto(Double totalMonto) {
		this.totalMonto = totalMonto;
	}

	public Date getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}

	public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}

	public Date getFechaFinalPeriodo() {
		return fechaFinalPeriodo;
	}

	public void setFechaFinalPeriodo(Date fechaFinalPeriodo) {
		this.fechaFinalPeriodo = fechaFinalPeriodo;
	}

	public Integer getDiasPeriodo() {
		return diasPeriodo;
	}

	public void setDiasPeriodo(Integer diasPeriodo) {
		this.diasPeriodo = diasPeriodo;
	}

	public String getFgAnulado() {
		return fgAnulado;
	}

	public void setFgAnulado(String fgAnulado) {
		this.fgAnulado = fgAnulado;
	}

	public Date getFechaHoraAnulacion() {
		return fechaHoraAnulacion;
	}

	public void setFechaHoraAnulacion(Date fechaHoraAnulacion) {
		this.fechaHoraAnulacion = fechaHoraAnulacion;
	}

	public Double getPctDetraccion() {
		return pctDetraccion;
	}

	public void setPctDetraccion(Double pctDetraccion) {
		this.pctDetraccion = pctDetraccion;
	}

	public String getFgDetraccion() {
		return fgDetraccion;
	}

	public void setFgDetraccion(String fgDetraccion) {
		this.fgDetraccion = fgDetraccion;
	}

	public Double getTotalDetraccion() {
		return totalDetraccion;
	}

	public void setTotalDetraccion(Double totalDetraccion) {
		this.totalDetraccion = totalDetraccion;
	}

	public TipoComprobante getTipoComprobanteRef() {
		return tipoComprobanteRef;
	}

	public void setTipoComprobanteRef(TipoComprobante tipoComprobanteRef) {
		this.tipoComprobanteRef = tipoComprobanteRef;
	}

	public String getSustento() {
		return sustento;
	}

	public void setSustento(String sustento) {
		this.sustento = sustento;
	}

	public String getUsuarioAnula() {
		return usuarioAnula;
	}

	public void setUsuarioAnula(String usuarioAnula) {
		this.usuarioAnula = usuarioAnula;
	}

	public String getHoraEmision() {
		return horaEmision;
	}

	public void setHoraEmision(String horaEmision) {
		this.horaEmision = horaEmision;
	}

	public List<ComprobanteDetalle> getLineas() {
		return lineas;
	}

	public void setLineas(List<ComprobanteDetalle> lineas) {
		this.lineas = lineas;
	}

}
