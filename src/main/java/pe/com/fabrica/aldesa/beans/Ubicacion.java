package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ubicacion")
public class Ubicacion extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 4412040510334547731L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ubicacion")
	private Long idUbicacion;

	@ManyToOne
	@JoinColumn(name = "id_area", nullable = false)
	private Area area;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "abrev", nullable = false)
	private String abreviatura;

	@Column(name = "nu_rack")
	private Integer numeroRack;

	@Column(name = "fg_acti", nullable = false)
	private String activo;

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Integer getNumeroRack() {
		return numeroRack;
	}

	public void setNumeroRack(Integer numeroRack) {
		this.numeroRack = numeroRack;
	}


	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Ubicacion [idUbicacion=" + idUbicacion + ", area=" + area + ", nombre=" + nombre + ", abreviatura="
				+ abreviatura + ", numeroRack=" + numeroRack + ", activo=" + activo + "]";
	}

}
