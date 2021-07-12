package pe.com.fabrica.aldesa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmenuContext {

	private Integer id;
	private String nombre;
	private Integer orden;
	private String ruta;
	private MenuContext menu;

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

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public MenuContext getMenu() {
		return menu;
	}

	public void setMenu(MenuContext menu) {
		this.menu = menu;
	}

}
