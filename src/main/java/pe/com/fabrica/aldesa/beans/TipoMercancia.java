package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_mercancia")
public class TipoMercancia extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -1837620125943880980L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_timercancia")
	private Integer idTipoMercancia;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	public Integer getIdTipoMercancia() {
		return idTipoMercancia;
	}

	public void setIdTipoMercancia(Integer idTipoMercancia) {
		this.idTipoMercancia = idTipoMercancia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "TipoMercancia [idTipoMercancia=" + idTipoMercancia + ", nombre=" + nombre + "]";
	}

}
