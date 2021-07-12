package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "movimiento")
public class Movimiento extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -6869079058371769385L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimiento")
	private Long idMovimiento;

	@ManyToOne
	@JoinColumn(name = "iddetalle_mercancia", nullable = false)
	private MercanciaDetalle mercaderia_detalle;

	public MercanciaDetalle getMercaderia_detalle() {
		return this.mercaderia_detalle;
	}

	public void setMercaderia_detalle(MercanciaDetalle mercaderia_detalle) {
		this.mercaderia_detalle = mercaderia_detalle;
	}

	@Column(name = "tipo", nullable = false)
	private String tipo;

	@Column(name = "anulado", nullable = false)
	private String anulado;

	public String getAnulado() {
		return this.anulado;
	}

	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "fe_movimiento", nullable = false)
	private Date fechaMovimiento;

	@Column(name = "hr_movimiento", nullable = false)
	private String horaMovimiento;

	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getHoraMovimiento(){
		return horaMovimiento;
	}

	public void setHoraMovimiento(String horaMovimiento){
		this.horaMovimiento = horaMovimiento;
	}
}
