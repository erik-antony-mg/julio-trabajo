package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "proveedor")
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 3011163609945665326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idproveedor")
	private Long idProveedor;

	@ManyToOne
	@JoinColumn(name = "id_tipersona", nullable = false)
	private TipoPersona tipoPersona;

	@OneToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Proveedor [idproveedor=" + idProveedor + ", tipoPersona=" + tipoPersona + ", persona=" + persona
				+ ", empresa=" + empresa + "]";
	}

}
