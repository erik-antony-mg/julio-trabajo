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
@Table(name = "deposito_temporal", uniqueConstraints = { @UniqueConstraint(columnNames = "cod_aduana")})
public class DepositoTemporal extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = -7982469029159361418L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_deposito")
	private Integer idDeposito;

	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	private Empresa empresa;

	@Column(name = "cod_aduana", nullable = false)
	private String codigoAduana;

	public Integer getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(Integer idDeposito) {
		this.idDeposito = idDeposito;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCodigoAduana() {
		return codigoAduana;
	}

	public void setCodigoAduana(String codigoAduana) {
		this.codigoAduana = codigoAduana;
	}

	@Override
	public String toString() {
		return "DepositoTemporal [idDeposito=" + idDeposito + ", empresa=" + empresa + ", codigoAduana=" + codigoAduana
				+ "]";
	}

}
