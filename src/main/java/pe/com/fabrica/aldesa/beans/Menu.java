package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "menu")
public class Menu extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 3991937666080311121L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_menu")
	private Integer idMenu;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "nu_orden")
	private int numeroOrden;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
	private Set<SubMenu> submenus;

	@Column(name = "icono")
	private String icono;

	public Integer getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(int numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public Set<SubMenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(Set<SubMenu> submenus) {
		this.submenus = submenus;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	@Override
	public String toString() {
		return "Menu [idMenu=" + idMenu + ", nombre=" + nombre + ", numeroOrden=" + numeroOrden + ", icono=" + icono
				+ "]";
	}

}
