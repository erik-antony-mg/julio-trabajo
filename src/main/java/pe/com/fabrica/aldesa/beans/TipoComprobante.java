package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tipo_comprobante", uniqueConstraints = { @UniqueConstraint(columnNames = "abrev") })
public class TipoComprobante extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -2140479638863467734L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ticomprobante")
	private Integer idTipoComprobante;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "abrev", nullable = false)
	private String abreviatura;

	@Column(name = "cod_sunat")
	private String codSunat;

	public String getCodSunat() {
		return this.codSunat;
	}

	public void setCodSunat(String codSunat) {
		this.codSunat = codSunat;
	}

	public Integer getIdTipoComprobante() {
		return idTipoComprobante;
	}

	public void setIdTipoComprobante(Integer idTipoComprobante) {
		this.idTipoComprobante = idTipoComprobante;
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

	@Override
	public String toString() {
		return "TipoComprobante [idTipoComprobante=" + idTipoComprobante + ", nombre=" + nombre + ", abreviatura="
				+ abreviatura + ", codSunat=" + codSunat + "]";
	}

}
