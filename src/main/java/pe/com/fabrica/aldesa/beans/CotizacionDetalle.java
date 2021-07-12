package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

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
@Table(name = "cotizacion_detalle")
public class CotizacionDetalle extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -6224104135349703307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle", nullable = false)
	private Long idDetalle;

	@ManyToOne
	@JoinColumn(name = "id_servicio", nullable = false)
	private Servicio servicio;

	@Column(name = "precio", nullable = false, precision = 12, scale = 2)
	private Double precio;

	@Column(name = "tipocantidad", nullable = false)
	private String tipoCantidad;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cotizacion", nullable = false)
	private Cotizacion cotizacion;

	public Long getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}


	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Cotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}


	public String getTipoCantidad() {
		return this.tipoCantidad;
	}

	public void setTipoCantidad(String tipoCantidad) {
		this.tipoCantidad = tipoCantidad;
	}
}
