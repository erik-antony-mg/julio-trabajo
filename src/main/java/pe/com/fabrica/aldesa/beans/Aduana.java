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
@Table(name = "aduana", uniqueConstraints = { @UniqueConstraint(columnNames = "cod_aduana")})
public class Aduana extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -1013094946214699133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aduana")
	private Integer idAduana;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "cod_aduana", nullable = false)
	private Integer codigoAduana;

	public Integer getIdAduana() {
		return idAduana;
	}

	public void setIdAduana(Integer idAduana) {
		this.idAduana = idAduana;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCodigoAduana() {
		return codigoAduana;
	}

	public void setCodigoAduana(Integer codigoAduana) {
		this.codigoAduana = codigoAduana;
	}

	@Override
	public String toString() {
		return "Aduana [idAduana=" + idAduana + ", nombre=" + nombre + ", codigoAduana=" + codigoAduana + "]";
	}

}
