package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CotizacionDetalleId implements Serializable {

	private static final long serialVersionUID = 7436689503108804378L;

	@Column(name = "id_cotizacion")
	private Long idCotizacion;

	@Column(name = "item")
	private Integer item;

	public CotizacionDetalleId() {
	}

	public CotizacionDetalleId(Long idCotizacion, Integer item) {
		this.idCotizacion = idCotizacion;
		this.item = item;
	}

	public Long getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	@Override
	public boolean equals(Object dci) {
		if (this == dci)
			return true;
		if (dci == null || getClass() != dci.getClass())
			return false;
		CotizacionDetalleId detCotId = (CotizacionDetalleId) dci;
		return Objects.equals(idCotizacion, detCotId.idCotizacion) && Objects.equals(item, detCotId.item);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCotizacion, item);
	}

	@Override
	public String toString() {
		return "DetalleCotizacionId [idCotizacion=" + idCotizacion + ", item=" + item + "]";
	}

}
