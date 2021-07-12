package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "mercancia")
public class Mercancia implements Serializable {

	private static final long serialVersionUID = 3011163609945665326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmercancia")
	private Long idMercancia;

	@ManyToOne
	@JoinColumn(name = "id_timercancia", nullable = false)
	private TipoMercancia tipoMercancia;

	@ManyToOne
	@JoinColumn(name = "id_tibulto", nullable = false)
	private TipoBulto tipoBulto;

	@Column(name = "levante_Mercancia", nullable = false)
	private Integer levanteMercancia;

	@Column(name = "seguro", nullable = false)
	private int seguro;

	public int getSeguro() {
		return this.seguro;
	}

	public void setSeguro(int seguro) {
		this.seguro = seguro;
	}

	@Column(name = "fob", nullable = false)
	private int fob;

	public int getFob() {
		return this.fob;
	}

	public void setFob(int fob) {
		this.fob = fob;
	}

	@Column(name = "flete", nullable = false)
	private int flete;

	public int getFlete() {
		return this.flete;
	}

	public void setFlete(int flete) {
		this.flete = flete;
	}

	@Column(name = "ajuste", nullable = false)
	private int ajuste;

	public int getAjuste() {
		return this.ajuste;
	}

	public void setAjuste(int ajuste) {
		this.ajuste = ajuste;
	}

	@OneToOne
	@JoinColumn(name = "id_tarjeta", nullable = false)
	private Tarjeta tarjeta;

	public Tarjeta getTarjeta() {
		return this.tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Long getIdMercancia() {
		return this.idMercancia;
	}

	public void setIdMercancia(Long idMercancia) {
		this.idMercancia = idMercancia;
	}

	public TipoMercancia getTipoMercancia() {
		return this.tipoMercancia;
	}

	public void setTipoMercancia(TipoMercancia tipoMercancia) {
		this.tipoMercancia = tipoMercancia;
	}

	public TipoBulto getTipoBulto() {
		return this.tipoBulto;
	}

	public void setTipoBulto(TipoBulto tipoBulto) {
		this.tipoBulto = tipoBulto;
	}

	public Integer getLevanteMercancia() {
		return this.levanteMercancia;
	}

	public void setLevanteMercancia(Integer levanteMercancia) {
		this.levanteMercancia = levanteMercancia;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mercancia", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<MercanciaDetalle> mercanciaDetalle;

	public List<MercanciaDetalle> getMercanciaDetalle() {
		return this.mercanciaDetalle;
	}

	public void setMercanciaDetalle(List<MercanciaDetalle> mercanciaDetalle) {
		this.mercanciaDetalle = mercanciaDetalle;
	}

}
