package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "chofer", uniqueConstraints = { @UniqueConstraint(columnNames = "no_licencia") })
public class Choferes implements Serializable {

	private static final long serialVersionUID = 3011163609945665326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_chofer")
	private Long idChofer;

	@OneToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;

	@Column(name = "no_licencia")
	private String numeroLicencia;

	public String getNumeroLicencia() {
		return numeroLicencia;
	}

	public void setNumeroLicencia(String numeroLicencia) {
		this.numeroLicencia = numeroLicencia;
	}

	public Long getidChofer() {
		return idChofer;
	}

	public void setidChofer(Long idChofer) {
		this.idChofer = idChofer;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}
