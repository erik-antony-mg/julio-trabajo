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
@Table(name = "serie_comprobante", uniqueConstraints = { @UniqueConstraint(columnNames = "serie") })
public class SerieComprobante implements Serializable {

	private static final long serialVersionUID = -8231161635338902508L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_serie")
	private Integer idSerieComprobante;

	public Integer getIdSerieComprobante() {
		return this.idSerieComprobante;
	}

	public void setIdSerieComprobante(Integer idSerieComprobante) {
		this.idSerieComprobante = idSerieComprobante;
	}

	@ManyToOne
	@JoinColumn(name = "id_ticomprobante", nullable = false)
	private TipoComprobante tipoComprobante;

	public TipoComprobante getTipoComprobante() {
		return this.tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	@Column(name = "serie", nullable = false, length = 5)
	private String serie;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	@Column(name = "numero", nullable = false)
	private String numero;

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
