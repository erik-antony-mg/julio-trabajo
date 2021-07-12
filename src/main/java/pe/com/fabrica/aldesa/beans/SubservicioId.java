package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SubservicioId implements Serializable {

	private static final long serialVersionUID = 7729633041350560713L;

	@Column(name = "id_servicio")
	private Integer idServicio;

	@Column(name = "id_subservicio")
	private Integer idSubservicio;

	public SubservicioId() {

	}

	public SubservicioId(Integer idServicio, Integer idSubservicio) {
		this.idServicio = idServicio;
		this.idSubservicio = idSubservicio;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public Integer getIdSubservicio() {
		return idSubservicio;
	}

	public void setIdSubservicio(Integer idSubservicio) {
		this.idSubservicio = idSubservicio;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SubservicioId subservicioId = (SubservicioId) o;
		return Objects.equals(idServicio, subservicioId.idServicio)
				&& Objects.equals(idSubservicio, subservicioId.idSubservicio);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idServicio, idSubservicio);
	}

	@Override
	public String toString() {
		return "SubservicioId [idServicio=" + idServicio + ", idSubservicio=" + idSubservicio + "]";
	}

}
