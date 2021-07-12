package pe.com.fabrica.aldesa.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo")
public class Vehiculo extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1213028909642430524L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vehiculo")
	private Long idVehiculo;

	@ManyToOne
	@JoinColumn(name = "id_tivehiculo", nullable = false)
	private TipoVehiculo tipoVehiculo;

	@Column(name = "placa", nullable = false)
	private String placa;

	@Column(name = "marca")
	private String marca;

	@Column(name = "certificado")
	private String certificado;

	@Column(name = "largo_m", precision = 5, scale = 2, nullable = false)
	private Double largo;

	@Column(name = "ancho_m", precision = 5, scale = 2, nullable = false)
	private Double ancho;

	@Column(name = "alto_m", precision = 5, scale = 2, nullable = false)
	private Double alto;

	@Column(name = "peso_tn", precision = 5, scale = 2, nullable = false)
	private Double peso;

	@Column(name = "fg_pesoxeje")
	private String eje;

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public Double getLargo() {
		return largo;
	}

	public void setLargo(Double largo) {
		this.largo = largo;
	}

	public Double getAncho() {
		return ancho;
	}

	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}

	public Double getAlto() {
		return alto;
	}

	public void setAlto(Double alto) {
		this.alto = alto;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getEje() {
		return eje;
	}

	public void setEje(String eje) {
		this.eje = eje;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	@Override
	public String toString() {
		return "Vehiculo [idVehiculo=" + idVehiculo + ", tipoVehiculo=" + tipoVehiculo + ", placa=" + placa + ", marca="
				+ marca + ", certificado=" + certificado + ", largo=" + largo + ", ancho=" + ancho + ", alto=" + alto
				+ ", peso=" + peso + ", eje=" + eje + "]";
	}

}
