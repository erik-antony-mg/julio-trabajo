package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dam")
public class Dam extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 4972665568684172554L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dam")
	private Long idDam;

	@OneToOne
	@JoinColumn(name = "id_aduana", nullable = false)
	private Aduana idAduana;

	@OneToOne
	@JoinColumn(name = "id_ag_aduana", nullable = false)
	private AgenciasAduanas idAgenciaAduanas;

	@OneToOne
	@JoinColumn(name = "id_deposito", nullable = false)
	private DepositoTemporal idDepositoTemporal;

	@OneToOne
	@JoinColumn(name = "id_regimen", nullable = false)
	private Regimen idRegimen;

	@OneToOne
	@JoinColumn(name = "id_tibulto", nullable = false)
	private TipoBulto idTipoBulto;

	@Column(name = "nu_declaracion", nullable = false, length = 45)
	private String numeroDeclaracion;

	@Column(name = "nu_manifiesto", nullable = false, length = 20)
	private String numeroManifiesto;

	@Column(name = "fe_numeracion", nullable = false)
	private Date fechaNumeracion;

	@Column(name = "doc_transporte", length = 45)
	private String docTransporte;

	@Column(name = "nu_fac_comercial", nullable = false, length = 100)
	private String numeroFacComercial;

	@Column(name = "fe_fac_comercial", nullable = false)
	private Date fechaFacComercial;

	@Column(name = "id_clase", nullable = false)
	private Integer idClase;

	@Column(name = "subpartida_nacional", nullable = false, length = 45)
	private String subpartidaNacional;

	@Column(name = "fob", nullable = false, precision = 12, scale = 2)
	private Double fob;

	@Column(name = "flete", nullable = false, precision = 12, scale = 2)
	private Double flete;

	@Column(name = "seguro", nullable = false, precision = 20, scale = 3)
	private Double seguro;

	@Column(name = "ajuste", nullable = false, precision = 20, scale = 3)
	private Double ajuste;

	@Column(name = "total", nullable = false, precision = 20, scale = 3)
	private Double total;

	public Long getIdDam() {
		return idDam;
	}

	public void setIdDam(Long idDam) {
		this.idDam = idDam;
	}

	public Aduana getIdAduana() {
		return idAduana;
	}

	public void setIdAduana(Aduana idAduana) {
		this.idAduana = idAduana;
	}

	public AgenciasAduanas getIdAgenciaAduanas() {
		return idAgenciaAduanas;
	}

	public void setIdAgenciaAduanas(AgenciasAduanas idAgenciaAduanas) {
		this.idAgenciaAduanas = idAgenciaAduanas;
	}

	public DepositoTemporal getIdDepositoTemporal() {
		return idDepositoTemporal;
	}

	public void setIdDepositoTemporal(DepositoTemporal idDepositoTemporal) {
		this.idDepositoTemporal = idDepositoTemporal;
	}

	public Regimen getIdRegimen() {
		return idRegimen;
	}

	public void setIdRegimen(Regimen idRegimen) {
		this.idRegimen = idRegimen;
	}

	public TipoBulto getIdTipoBulto() {
		return idTipoBulto;
	}

	public void setIdTipoBulto(TipoBulto idTipoBulto) {
		this.idTipoBulto = idTipoBulto;
	}

	public String getNumeroDeclaracion() {
		return numeroDeclaracion;
	}

	public void setNumeroDeclaracion(String numeroDeclaracion) {
		this.numeroDeclaracion = numeroDeclaracion;
	}

	public String getNumeroManifiesto() {
		return numeroManifiesto;
	}

	public void setNumeroManifiesto(String numeroManifiesto) {
		this.numeroManifiesto = numeroManifiesto;
	}

	public Date getFechaNumeracion() {
		return fechaNumeracion;
	}

	public void setFechaNumeracion(Date fechaNumeracion) {
		this.fechaNumeracion = fechaNumeracion;
	}

	public String getDocTransporte() {
		return docTransporte;
	}

	public void setDocTransporte(String docTransporte) {
		this.docTransporte = docTransporte;
	}

	public String getNumeroFacComercial() {
		return numeroFacComercial;
	}

	public void setNumeroFacComercial(String numeroFacComercial) {
		this.numeroFacComercial = numeroFacComercial;
	}

	public Date getFechaFacComercial() {
		return fechaFacComercial;
	}

	public void setFechaFacComercial(Date fechaFacComercial) {
		this.fechaFacComercial = fechaFacComercial;
	}

	public Integer getIdClase() {
		return idClase;
	}

	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}

	public String getSubpartidaNacional() {
		return subpartidaNacional;
	}

	public void setSubpartidaNacional(String subpartidaNacional) {
		this.subpartidaNacional = subpartidaNacional;
	}

	public Double getFob() {
		return fob;
	}

	public void setFob(Double fob) {
		this.fob = fob;
	}

	public Double getFlete() {
		return flete;
	}

	public void setFlete(Double flete) {
		this.flete = flete;
	}

	public Double getSeguro() {
		return seguro;
	}

	public void setSeguro(Double seguro) {
		this.seguro = seguro;
	}

	public Double getAjuste() {
		return ajuste;
	}

	public void setAjuste(Double ajuste) {
		this.ajuste = ajuste;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
