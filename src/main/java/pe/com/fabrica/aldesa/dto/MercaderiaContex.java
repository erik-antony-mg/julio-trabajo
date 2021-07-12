package pe.com.fabrica.aldesa.dto;

import java.util.List;

import pe.com.fabrica.aldesa.beans.Movimiento;
import pe.com.fabrica.aldesa.beans.MercanciaDetalle;

public class MercaderiaContex {

	private List<MercanciaDetalle> mercanciaDetalle;

	private List<Movimiento> listmovimientos;

	public MercaderiaContex(List<MercanciaDetalle> mercanciaDetalle, List<Movimiento> listmovimientos) {
		super();
		this.mercanciaDetalle = mercanciaDetalle;
		this.listmovimientos = listmovimientos;
	}

	public List<MercanciaDetalle> getMercanciaDetalle() {
		return mercanciaDetalle;
	}

	public List<Movimiento> getListmovimientos() {
		return this.listmovimientos;
	}

}
