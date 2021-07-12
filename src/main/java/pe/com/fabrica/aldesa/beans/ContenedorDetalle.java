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
@Table(name = "contenedor_detalle")
public class ContenedorDetalle extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -6224104135349703307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contenedor_detalle", nullable = false)
	private Integer idDetalle;

	public Integer getIdDetalle() {
		return this.idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	@ManyToOne
	@JoinColumn(name = "idContenedor", nullable = false)
	private Contenedor contenedor;

	public Contenedor getContenedor() {
		return this.contenedor;
	}

	public void setContenedor(Contenedor contenedor) {
		this.contenedor = contenedor;
	}

	@Column(name = "numero_precintos", nullable = false)
	private String numero_precintos;

	public String getNumero_precintos() {
		return this.numero_precintos;
	}

	public void setNumero_precintos(String numero_precintos) {
		this.numero_precintos = numero_precintos;
	}

}
