package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "submenu", uniqueConstraints = { @UniqueConstraint(columnNames = "nombre") })
public class SubMenu extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -424674962558155186L;

	@Id
	@Column(name = "id_submenu", nullable = false)
	private Integer submenuId;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "nu_orden")
	private Integer numeroOrden;

	@Column(name = "ruta")
	private String ruta;

	@ManyToOne
	@JoinColumn(name = "id_menu", nullable = false)
	private Menu menu;

	@Override
	public String toString() {
		return "SubMenu [submenuId=" + submenuId + ", nombre=" + nombre + ", numeroOrden=" + numeroOrden + ", ruta="
				+ ruta + "]";
	}

	public Integer getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Integer submenuId) {
		this.submenuId = submenuId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Integer numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
