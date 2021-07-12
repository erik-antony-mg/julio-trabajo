package pe.com.fabrica.aldesa.dto;

import java.util.List;

public class RolContext {

	private Integer id;
	private String nombre;
	private List<SubmenuContext> submenus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<SubmenuContext> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SubmenuContext> submenus) {
		this.submenus = submenus;
	}

}
