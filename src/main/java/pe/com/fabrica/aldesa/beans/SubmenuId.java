package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SubmenuId implements Serializable {

	private static final long serialVersionUID = -7316505659455891300L;

	@Column(name = "id_menu")
	private Integer idMenu;

	@Column(name = "id_submenu")
	private Integer idSubmenu;

	public SubmenuId() {
	}

	public SubmenuId(Integer idMenu, Integer idSubmenu) {
		this.idMenu = idMenu;
		this.idSubmenu = idSubmenu;
	}

	public Integer getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	public Integer getIdSubmenu() {
		return idSubmenu;
	}

	public void setIdSubmenu(Integer idSubmenu) {
		this.idSubmenu = idSubmenu;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SubmenuId submenuid = (SubmenuId) o;
		return Objects.equals(idMenu, submenuid.idMenu) && Objects.equals(idSubmenu, submenuid.idSubmenu);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idMenu, idSubmenu);
	}

	@Override
	public String toString() {
		return "SubmenuId [idMenu=" + idMenu + ", idSubmenu=" + idSubmenu + "]";
	}

}
