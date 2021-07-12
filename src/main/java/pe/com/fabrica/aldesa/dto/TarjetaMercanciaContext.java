package pe.com.fabrica.aldesa.dto;

public class TarjetaMercanciaContext {

	private Integer idMercancia;

	private Integer idTipoMercancia;

	private Integer idTipoBulto;

	private Integer levanteMercaderia;

	private String flete;

	private String seguro;

	private String ajuste;

	private String nombreGrupoServicio;

	public Integer getIdMercancia() {
		return this.idMercancia;
	}

	public void setIdMercancia(Integer idMercancia) {
		this.idMercancia = idMercancia;
	}

	public Integer getIdTipoMercancia() {
		return this.idTipoMercancia;
	}

	public void setIdTipoMercancia(Integer idTipoMercancia) {
		this.idTipoMercancia = idTipoMercancia;
	}

	public Integer getIdTipoBulto() {
		return this.idTipoBulto;
	}

	public void setIdTipoBulto(Integer idTipoBulto) {
		this.idTipoBulto = idTipoBulto;
	}

	public Integer getLevanteMercaderia() {
		return this.levanteMercaderia;
	}

	public void setLevanteMercaderia(Integer levanteMercaderia) {
		this.levanteMercaderia = levanteMercaderia;
	}

	public String getFlete() {
		return this.flete;
	}

	public void setFlete(String flete) {
		this.flete = flete;
	}

	public String getSeguro() {
		return this.seguro;
	}

	public void setSeguro(String seguro) {
		this.seguro = seguro;
	}

	public String getAjuste() {
		return this.ajuste;
	}

	public void setAjuste(String ajuste) {
		this.ajuste = ajuste;
	}

	public String getNombreGrupoServicio() {
		return this.nombreGrupoServicio;
	}

	public void setNombreGrupoServicio(String nombreGrupoServicio) {
		this.nombreGrupoServicio = nombreGrupoServicio;
	}

}
