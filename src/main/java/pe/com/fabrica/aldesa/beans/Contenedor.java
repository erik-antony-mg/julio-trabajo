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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "contenedor")
public class Contenedor extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 693727428879404524L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contenedor")
	private Integer idContenedor;

	public Integer getIdContenedor() {
		return this.idContenedor;
	}

	public void setIdContenedor(Integer idContenedor) {
		this.idContenedor = idContenedor;
	}

	@Column(name = "camion", nullable = false)
	private String camion;

	@Column(name = "contenedor", nullable = false)
	private String contenedor;

	@Column(name = "documento_sini", nullable = false)
	private String documento_sini;

	@Column(name = "doc_traslado")
	private String doc_traslado;

	@Column(name = "ticket_puerto")
	private String ticket_puerto;

	@OneToOne
	@JoinColumn(name = "id_tarjeta", nullable = false)
	private Tarjeta tarjeta;

	public Tarjeta getTarjeta() {
		return this.tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getCamion() {
		return this.camion;
	}

	public void setCamion(String camion) {
		this.camion = camion;
	}

	public String getContenedor() {
		return this.contenedor;
	}

	public void setContenedor(String contenedor) {
		this.contenedor = contenedor;
	}

	public String getDocumento_sini() {
		return this.documento_sini;
	}

	public void setDocumento_sini(String documento_sini) {
		this.documento_sini = documento_sini;
	}

	public String getDoc_traslado() {
		return this.doc_traslado;
	}

	public void setDoc_traslado(String doc_traslado) {
		this.doc_traslado = doc_traslado;
	}

	public String getTicket_puerto() {
		return this.ticket_puerto;
	}

	public void setTicket_puerto(String ticket_puerto) {
		this.ticket_puerto = ticket_puerto;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contenedor", cascade = CascadeType.ALL)
	// @Fetch(value = FetchMode.SUBSELECT)
	private List<ContenedorDetalle> listaDetalleContenedor;

	public List<ContenedorDetalle> getListaDetalleContenedor() {
		return this.listaDetalleContenedor;
	}

	public void setListaDetalleContenedor(List<ContenedorDetalle> listaDetalleContenedor) {
		this.listaDetalleContenedor = listaDetalleContenedor;
	}

}
