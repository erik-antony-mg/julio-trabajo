package pe.com.fabrica.aldesa.dto;

import java.util.List;

import pe.com.fabrica.aldesa.beans.Contenedor;
import pe.com.fabrica.aldesa.beans.ContenedorDetalle;
import pe.com.fabrica.aldesa.beans.Mercancia;
import pe.com.fabrica.aldesa.beans.Movimiento;
import pe.com.fabrica.aldesa.beans.MercanciaDetalle;
import pe.com.fabrica.aldesa.beans.Tarjeta;

public class TarjetaContext {

	private Tarjeta tarjeta;

	private Mercancia mercancia;

	private List<MercanciaDetalle> mercanciaDetalle;

	private Contenedor contenedor;

	private List<ContenedorDetalle> ContenedorDetalle;

	private List<Movimiento> listmovimientos;

	public TarjetaContext(Tarjeta tarjeta, Mercancia mercancia, List<MercanciaDetalle> mercanciaDetalle,
			List<Movimiento> listmovimientos, Contenedor contenedor, List<ContenedorDetalle> ContenedorDetalle) {
		super();
		this.tarjeta = tarjeta;
		this.mercancia = mercancia;
		this.mercanciaDetalle = mercanciaDetalle;
		this.listmovimientos = listmovimientos;
		this.contenedor = contenedor;
		this.ContenedorDetalle = ContenedorDetalle;
	}

	public TarjetaContext(Tarjeta tarjeta, Mercancia mercancia, List<MercanciaDetalle> mercanciaDetalle,
			List<Movimiento> listmovimientos) {
		super();
		this.tarjeta = tarjeta;
		this.mercancia = mercancia;
		this.mercanciaDetalle = mercanciaDetalle;
		this.listmovimientos = listmovimientos;
	}

	public Contenedor getContenedor() {
		return this.contenedor;
	}

	public List<ContenedorDetalle> getContenedorDetalle() {
		return this.ContenedorDetalle;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public Mercancia getMercancia() {
		return mercancia;
	}

	public List<MercanciaDetalle> getMercanciaDetalle() {
		return mercanciaDetalle;
	}

	public List<Movimiento> getListmovimientos() {
		return this.listmovimientos;
	}

}
