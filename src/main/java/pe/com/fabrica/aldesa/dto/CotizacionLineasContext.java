package pe.com.fabrica.aldesa.dto;

public class CotizacionLineasContext {

	private Integer idLinea;

	private Integer idServicio;

	private String nombreServicio;

	private String pct;

	private Double precioMonedaNacional;

	private Double precioMonedaExtrangera;

	private String nombreGrupoServicio;

	public Integer getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public String getPct() {
		return pct;
	}

	public void setPct(String pct) {
		this.pct = pct;
	}

	public Double getPrecioMonedaNacional() {
		return precioMonedaNacional;
	}

	public void setPrecioMonedaNacional(Double precioMonedaNacional) {
		this.precioMonedaNacional = precioMonedaNacional;
	}

	public Double getPrecioMonedaExtrangera() {
		return precioMonedaExtrangera;
	}

	public void setPrecioMonedaExtrangera(Double precioMonedaExtrangera) {
		this.precioMonedaExtrangera = precioMonedaExtrangera;
	}

	public String getNombreGrupoServicio() {
		return nombreGrupoServicio;
	}

	public void setNombreGrupoServicio(String nombreGrupoServicio) {
		this.nombreGrupoServicio = nombreGrupoServicio;
	}

}
