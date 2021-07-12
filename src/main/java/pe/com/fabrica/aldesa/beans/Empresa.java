package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "empresa", uniqueConstraints = { @UniqueConstraint(columnNames = "nu_ruc") })
@Inheritance(strategy = InheritanceType.JOINED)
public class Empresa extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -8329389334956331955L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long idEmpresa;

	@Column(name = "nu_ruc", nullable = false)
	private String ruc;

	@Column(name = "ra_social", nullable = false)
	private String razonSocial;

	@OneToOne
	@JoinColumn(name = "id_direccion")
	private Direccion direccion;

	@Column(name = "no_comercial")
	private String nombreComercial;

	@Column(name = "contacto", nullable = false)
	private String contacto;

	@Column(name = "telefono", nullable = false)
	private String telefono;

	@Column(name = "correo", nullable = false)
	private String correo;

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	
	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", ruc=" + ruc + ", razonSocial=" + razonSocial + ", direccion="
				+ direccion + ", nombreComercial=" + nombreComercial + "]";
	}

}
