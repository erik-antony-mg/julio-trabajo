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
@Table(name = "tipo_bulto", uniqueConstraints = { @UniqueConstraint(columnNames = "abrev") })
public class TipoBulto extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -5728349030411752564L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tibulto")
	private Integer idTipoBulto;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "abrev", nullable = false)
	private String abreviatura;

	public Integer getIdTipoBulto() {
		return idTipoBulto;
	}

	public void setIdTipoBulto(Integer idTipoBulto) {
		this.idTipoBulto = idTipoBulto;
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
		return "TipoBulto [idTipoBulto=" + idTipoBulto + ", nombre=" + nombre + ", abreviatura=" + abreviatura + "]";
	}

}
