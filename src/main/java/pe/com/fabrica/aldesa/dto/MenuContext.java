package pe.com.fabrica.aldesa.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuContext {

	private Integer id;
	private String nombre;
	private Integer orden;
	private List<SubmenuContext> submenu;
	private String icono;

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

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<SubmenuContext> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<SubmenuContext> submenu) {
		this.submenu = submenu;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

}
