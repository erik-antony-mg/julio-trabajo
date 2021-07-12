package pe.com.fabrica.aldesa.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

	@CreatedBy
	@Column(name = "us_crea", nullable = false, updatable = false)
	protected T usuarioCreador;

	@LastModifiedBy
	@Column(name = "us_modi", nullable = false)
	protected T usuarioModificador;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "fe_crea", nullable = false, updatable = false)
	protected Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column(name = "fe_modi", nullable = false)
	protected Date fechaModificacion;

	public T getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(T usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public T getUsuarioModificador() {
		return usuarioModificador;
	}

	public void setUsuarioModificador(T usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

}
