package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tipo_vehiculo", uniqueConstraints = { @UniqueConstraint(columnNames = "abrev") })
public class TipoVehiculo extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 187378428064186040L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tivehiculo")
	private Integer idTipoVehiculo;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "abrev", length = 10)
	private String abreviatura;

	public Integer getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(Integer idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Override
	public String toString() {
		return "TipoVehiculo [idTipoVehiculo=" + idTipoVehiculo + ", nombre=" + nombre + ", abreviatura=" + abreviatura
				+ "]";
	}

}
