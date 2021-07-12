package pe.com.fabrica.aldesa.beans;
import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = "username") })


public class Usuario extends Persona  {

	
	@PrimaryKeyJoinColumn(name = "id_persona")

	private static final long serialVersionUID = -5566869717098648176L;	

	@ManyToOne
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "passwd", nullable = false)
	private String password;

	@Column(name = "imagen")
	private String imagen;

	@Column(name = "fg_acti", nullable = false, length = 1)
	private String activo;

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	// @Override
	// public String getUsuarioCreador() {
	// 	return usuarioCreador;
	// }

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Usuario [rol=" + rol + ", username=" + username + ", password=" + password + ", imagen=" + imagen
				+ ", activo=" + activo + "]";
	}

	

}
