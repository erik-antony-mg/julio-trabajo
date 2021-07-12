package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

public class TipoCambioPromedio extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 187378428064186040L;



	private Double cambioCompra;

	private Double cambioVenta;


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

}
