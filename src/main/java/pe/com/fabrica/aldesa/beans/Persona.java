package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "persona", uniqueConstraints = { @UniqueConstraint(columnNames = {"nu_doc", "email"}) })
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 4955788495130287463L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona", nullable = false)
	private Long idPersona;

	@OneToOne
	@JoinColumn(name = "id_direccion")
	private Direccion direccion;

	@Column(name = "nu_doc", nullable = false)
	private String numeroDocumento;

	@Column(name = "nombres", nullable = false)
	private String nombres;

	@Column(name = "ap_paterno", nullable = false)
	private String apellidoPaterno;

	@Column(name = "ap_materno")
	private String apellidoMaterno;

	@Column(name = "sexo")
	private Character sexo;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nac")
	private Date fechaNacimiento;

	@Column(name = "email")
	private String email;


	@Column(name = "telefono", nullable = false)
	private String telefono;

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@ManyToOne
	@JoinColumn(name = "id_tidoc")
	private TipoDocumento tipoDocumento;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", idDireccion=" + direccion + ", numeroDocumento=" + numeroDocumento
				+ ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
				+ apellidoMaterno + ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email
				+ ", tipoDocumento=" + tipoDocumento + "]";
	}

}
