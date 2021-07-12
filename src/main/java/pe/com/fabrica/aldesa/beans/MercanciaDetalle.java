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
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "mercancia_detalle")
public class MercanciaDetalle implements Serializable {

	private static final long serialVersionUID = -4115263633408002233L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iddetalle_mercancia", nullable = false)
	private Long idDetalleMercancia;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idmercancia", nullable = false)
	private Mercancia mercancia;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;

	@Column(name = "valorUS$", nullable = false)
	private Double valorUS$;

	@Column(name = "bueno", nullable = false)
	private Integer bueno;

	@Column(name = "diferencia", nullable = false)
	private Integer diferencia;

	public Integer getDiferencia() {
		return this.diferencia;
	}

	public void setDiferencia(Integer diferencia) {
		this.diferencia = diferencia;
	}

	@Column(name = "motivo", nullable = false)
	private String motivo;

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Long getIdDetalleMercancia() {
		return this.idDetalleMercancia;
	}

	public void setIdDetalleMercancia(Long idDetalleMercancia) {
		this.idDetalleMercancia = idDetalleMercancia;
	}

	public Mercancia getMercancia() {
		return this.mercancia;
	}

	public void setMercancia(Mercancia mercancia) {
		this.mercancia = mercancia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getValorUS$() {
		return this.valorUS$;
	}

	public void setValorUS$(Double valorUS$) {
		this.valorUS$ = valorUS$;
	}

	public Integer getBueno() {
		return this.bueno;
	}

	public void setBueno(Integer bueno) {
		this.bueno = bueno;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mercaderia_detalle", cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)

	private List<Movimiento> listmovimiento;

	public List<Movimiento> getListmovimiento() {
		return this.listmovimiento;
	}

	public void setListmovimiento(List<Movimiento> listmovimiento) {
		this.listmovimiento = listmovimiento;
	}

}
