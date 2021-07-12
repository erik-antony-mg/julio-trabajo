package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subservicio")
public class Subservicio extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 5226827114979294224L;

	@EmbeddedId
	private SubservicioId subservicioId;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "precio_mn", precision = 12, scale = 2)
	private Double precioMN;

	@Column(name = "precio_me", precision = 12, scale = 2)
	private Double precioME;

	public SubservicioId getSubservicioId() {
		return subservicioId;
	}

	public void setSubservicioId(SubservicioId subservicioId) {
		this.subservicioId = subservicioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecioMN() {
		return precioMN;
	}

	public void setPrecioMN(Double precioMN) {
		this.precioMN = precioMN;
	}

	public Double getPrecioME() {
		return precioME;
	}

	public void setPrecioME(Double precioME) {
		this.precioME = precioME;
	}

	@Override
	public String toString() {
		return "Subservicio [subservicioId=" + subservicioId + ", nombre=" + nombre + ", precioMN=" + precioMN
				+ ", precioME=" + precioME + "]";
	}

}
