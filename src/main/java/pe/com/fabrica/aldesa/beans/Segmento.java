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
@Table(name = "segmento", uniqueConstraints = { @UniqueConstraint(columnNames = "codigo") })
public class Segmento implements Serializable {

	private static final long serialVersionUID = -1013094946214699133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_segmento")
	private Integer idSegmento;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	public Integer getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(Integer idSegmento) {
		this.idSegmento = idSegmento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
