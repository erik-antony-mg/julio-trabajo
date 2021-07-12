package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tipo_cambio", uniqueConstraints = { @UniqueConstraint(columnNames = "fe_ticambio") })
public class TipoCambio extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 187378428064186040L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ticambio")
	private Integer idTipoCambio;

	@Column(name = "fe_ticambio", nullable = false)
	private Date fechaTipoCambio;

	@Column(name = "cambio_compra", nullable = false)
	private Double cambioCompra;

	@Column(name = "cambio_venta", nullable = false)
	private Double cambioVenta;

	public Date getFechaTipoCambio() {
		return this.fechaTipoCambio;
	}

	public void setFechaTipoCambio(Date fechaTipoCambio) {
		this.fechaTipoCambio = fechaTipoCambio;
	}

	public Double getCambioVenta() {
		return this.cambioVenta;
	}

	public void setCambioVenta(Double cambioVenta) {
		this.cambioVenta = cambioVenta;
	}

	public Double getCambioCompra() {
		return this.cambioCompra;
	}

	public void setCambioCompra(Double cambioCompra) {
		this.cambioCompra = cambioCompra;
	}

	public Integer getIdTipoCambio() {
		return this.idTipoCambio;
	}

	public void setIdTipoCambio(Integer idTipoCambio) {
		this.idTipoCambio = idTipoCambio;
	}

}
