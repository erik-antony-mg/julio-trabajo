package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comprobante_detalle")
public class ComprobanteDetalle extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 327493638234165842L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle", nullable = false)
	private String idDetalle;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_comprobante", nullable = false)
	private Comprobante comprobante;

	@ManyToOne
	@JoinColumn(name = "id_servicio", nullable = false)
	private Servicio servicio;

	@Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
	private Double precioUnitario;

	@Column(name = "cantidad", nullable = false, precision = 12, scale = 2)
	private Double cantidad;

	@Column(name = "valor_unitario", nullable = false, precision = 12, scale = 2)
	private Double valorUnitario;

	@Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
	private Double valorTotal;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "unidad_medida", nullable = false)
	private String unidadMedida;

	@Column(name = "pedido")
	private String pedido;

	@Column(name = "registro")
	private Long registro;

	@Column(name = "fe_ini_periodo")
	private Date fechaInicialPeriodo;

	@Column(name = "fe_fin_periodo")
	private Date fechaFinalPeriodo;

	@Column(name = "dias_periodo")
	private Integer diasPeriodo;

	@Column(name = "descripcion_adic")
	private String descripcionAdicional;

	@Column(name = "cantidad_adic")
	private Double cantidadAdicional;

	@Column(name = "bultos")
	private String bultos;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "tarifa")
	private Double tarifa;

	public String getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(String idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public Long getRegistro() {
		return registro;
	}

	public void setRegistro(Long registro) {
		this.registro = registro;
	}

	public Date getFechaInicialPeriodo() {
		return fechaInicialPeriodo;
	}

	public void setFechaInicialPeriodo(Date fechaInicialPeriodo) {
		this.fechaInicialPeriodo = fechaInicialPeriodo;
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

	public String getDescripcionAdicional() {
		return descripcionAdicional;
	}

	public void setDescripcionAdicional(String descripcionAdicional) {
		this.descripcionAdicional = descripcionAdicional;
	}

	public Double getCantidadAdicional() {
		return cantidadAdicional;
	}

	public void setCantidadAdicional(Double cantidadAdicional) {
		this.cantidadAdicional = cantidadAdicional;
	}

	public String getBultos() {
		return bultos;
	}

	public void setBultos(String bultos) {
		this.bultos = bultos;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

}
