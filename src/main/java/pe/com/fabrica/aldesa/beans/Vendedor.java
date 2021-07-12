package pe.com.fabrica.aldesa.beans;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "vendedor")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Vendedor extends Persona  {

	private static final long serialVersionUID = -128819351022172178L;


}
