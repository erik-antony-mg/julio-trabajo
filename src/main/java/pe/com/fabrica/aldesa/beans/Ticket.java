package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_pesaje")
public class Ticket extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 100110884846450837L;

	@Id
	@Column(name = "id_ticket")
	private Long idTicket;

	@ManyToOne
	@JoinColumn(name = "id_vehiculo", nullable = false)
	private Vehiculo vehiculo;

	@ManyToOne
	@JoinColumn(name = "id_chofer", nullable = false, referencedColumnName = "id_persona")
	private Choferes chofer;

	@Column(name = "peso_1", nullable = false, precision = 20, scale = 5)
	private Double peso1;

	@Column(name = "peso_2", precision = 20, scale = 5)
	private Double peso2;

	@Column(name = "fe_peso_1", nullable = false)
	private Date fechaPeso1;

	@Column(name = "fe_peso_2")
	private Date fechaPeso2;

	public Long getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Long idTicket) {
		this.idTicket = idTicket;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Choferes getChofer() {
		return chofer;
	}

	public void setChofer(Choferes chofer) {
		this.chofer = chofer;
	}

	public Double getPeso1() {
		return peso1;
	}

	public void setPeso1(Double peso1) {
		this.peso1 = peso1;
	}

	public Double getPeso2() {
		return peso2;
	}

	public void setPeso2(Double peso2) {
		this.peso2 = peso2;
	}

	public Date getFechaPeso1() {
		return fechaPeso1;
	}

	public void setFechaPeso1(Date fechaPeso1) {
		this.fechaPeso1 = fechaPeso1;
	}

	public Date getFechaPeso2() {
		return fechaPeso2;
	}

	public void setFechaPeso2(Date fechaPeso2) {
		this.fechaPeso2 = fechaPeso2;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", vehiculo=" + vehiculo + ", chofer=" + chofer + ", peso1=" + peso1
				+ ", peso2=" + peso2 + ", fechaPeso1=" + fechaPeso1 + ", fechaPeso2=" + fechaPeso2 + "]";
	}

}
