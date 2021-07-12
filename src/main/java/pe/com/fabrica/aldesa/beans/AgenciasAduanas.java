package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "agencia_aduanas", uniqueConstraints = { @UniqueConstraint(columnNames = { "cod_aduana" }) })
public class AgenciasAduanas implements Serializable {

	private static final long serialVersionUID = 3011163609945665326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_agencia_aduanas")
	private Long idAgencia;

	@Column(name = "cod_aduana", nullable = false)
	private Integer codigoAduana;

	@OneToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	public Integer getCodigoAduana() {
		return codigoAduana;
	}

	public void setCodigoAduana(Integer codigoAduana) {
		this.codigoAduana = codigoAduana;
	}

	public Long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
