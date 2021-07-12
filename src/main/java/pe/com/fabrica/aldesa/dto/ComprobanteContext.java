package pe.com.fabrica.aldesa.dto;

import java.util.List;

import pe.com.fabrica.aldesa.beans.Comprobante;
import pe.com.fabrica.aldesa.beans.ComprobanteDetalle;

public class ComprobanteContext {

	private Comprobante comprobante;

	private List<ComprobanteDetalle> lineas;

	public ComprobanteContext(Comprobante comprobante, List<ComprobanteDetalle> lineas) {
		this.comprobante = comprobante;
		this.lineas = lineas;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public List<ComprobanteDetalle> getLineas() {
		return lineas;
	}

	public void setLineas(List<ComprobanteDetalle> lineas) {
		this.lineas = lineas;
	}

}
