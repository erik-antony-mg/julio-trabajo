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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "cotizacion")
public class Cotizacion extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 6937274288794045024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cotizacion")
	private Long idCotizacion;

	@OneToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name = "id_moneda", nullable = false)
	private Moneda moneda;

	@Column(name = "fecha", nullable = false)
	private Date fecha;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "etapa", nullable = false)
	private String etapa;

	@Column(name = "nro_carta_al", nullable = false)
	private String nroCarta;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "referencia")
	private String referencia;

	public String getEtapa() {
		return this.etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	
	public Long getIdCotizacion() {
		return this.idCotizacion;
	}

	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNroCarta() {
		return this.nroCarta;
	}

	public void setNroCarta(String nroCarta) {
		this.nroCarta = nroCarta;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	
	public List<CotizacionDetalle> getLineas() {
		return lineas;
	}

	public void setLineas(List<CotizacionDetalle> lineas) {
		this.lineas = lineas;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cotizacion", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CotizacionDetalle> lineas;

}
