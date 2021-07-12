package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moneda")
public class Moneda extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -2721970112899281871L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_moneda")
	private Integer idMoneda;

	@Column(name = "nombre", nullable = false, length = 20)
	private String nombre;

	@Column(name = "simbolo", nullable = false)
	private String simbolo;

	@Column(name = "abrev", nullable = false)
	private String abreviatura;

	@Column(name = "cod_divisa", nullable = false, length = 3)
	private String codigoDivisa;

	public Integer getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getCodigoDivisa() {
		return codigoDivisa;
	}

	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}

	@Override
	public String toString() {
		return "Moneda [idMoneda=" + idMoneda + ", nombre=" + nombre + ", simbolo=" + simbolo + ", abreviatura="
				+ abreviatura + ", codigoDivisa=" + codigoDivisa + "]";
	}

}
