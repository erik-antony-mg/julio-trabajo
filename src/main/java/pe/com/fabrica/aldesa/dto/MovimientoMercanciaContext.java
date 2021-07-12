package pe.com.fabrica.aldesa.dto;

import pe.com.fabrica.aldesa.beans.Movimiento;
import pe.com.fabrica.aldesa.beans.MercanciaDetalle;

public class MovimientoMercanciaContext {

	private MercanciaDetalle mercanciaDetalle;

	private Movimiento movimientos;

	public MovimientoMercanciaContext(MercanciaDetalle mercanciaDetalle, Movimiento movimientos) {
		super();
		this.mercanciaDetalle = mercanciaDetalle;
		this.movimientos = movimientos;
	}

	public MercanciaDetalle getMercanciaDetalle() {
		return this.mercanciaDetalle;
	}

	public void setMercanciaDetalle(MercanciaDetalle mercanciaDetalle) {
		this.mercanciaDetalle = mercanciaDetalle;
	}

	public Movimiento getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(Movimiento movimientos) {
		this.movimientos = movimientos;
	}



}
