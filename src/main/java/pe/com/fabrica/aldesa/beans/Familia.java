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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "familia", uniqueConstraints = { @UniqueConstraint(columnNames = "codigo") })
public class Familia implements Serializable {

	private static final long serialVersionUID = -1013094946214699133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_familia")
	private Integer idFamilia;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@ManyToOne
	@JoinColumn(name = "id_segmento")
	private Segmento segmento;
	

	public Segmento getSegmento(){
		return segmento;
	}

	public void setSegemento(Segmento segmento){
		this.segmento = segmento;
	}
	
	public Integer getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(Integer idFamilia) {
		this.idFamilia = idFamilia;
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
