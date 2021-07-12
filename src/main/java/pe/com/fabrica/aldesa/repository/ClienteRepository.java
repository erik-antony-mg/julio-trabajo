package pe.com.fabrica.aldesa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Cliente;
import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.TipoPersona;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query(value = "select * from cliente c inner join persona p on c.id_persona = p.id_persona "
			+ "where p.nombres like lower(concat('%', :nombre, '%')) or p.ap_paterno like lower(concat('%', :nombre, '%')) or "
			+ "p.ap_materno like lower(concat('%', :nombre, '%'))", nativeQuery = true)
	List<Cliente> findByNombre(String nombre);

	@Query(value = "select * from cliente c inner join persona p on c.id_persona = p.id_persona "
			+ "where p.nombres like lower(concat('%', :nombre, '%')) or p.ap_paterno like lower(concat('%', :nombre, '%')) or "
			+ "p.ap_materno like lower(concat('%', :nombre, '%'))", nativeQuery = true)
	Page<Cliente> findByNombrePag(String nombre, Pageable pageable);

	@Query(value = "select * from cliente where id_tipersona = :id_tipersona", nativeQuery = true)
	Page<Cliente> findByTipoPersona(Integer id_tipersona, Pageable pageable);

	@Query(value = "SELECT  * FROM cliente c where codigo  LIKE %:codigo%", nativeQuery = true)
	List<Cliente> findByCodigo(String codigo);

	@Query(value = "select * from cliente c inner join persona p on c.id_persona = p.id_persona "
			+ "where p.nu_doc = :numeroDocumento", nativeQuery = true)
	Optional<Cliente> findByNumeroDocumento(String numeroDocumento);

	@Query(value = "select * from cliente c inner join empresa e on c.id_empresa = e.id_empresa "
			+ "where e.ra_social like lower(concat('%', :razonSocial, '%')) or e.no_comercial like lower(concat('%', :razonSocial, '%'))", nativeQuery = true)
	List<Cliente> findByRazonSocial(String razonSocial);

	@Query(value = "select * from cliente c inner join empresa e on c.id_empresa = e.id_empresa "
			+ "where e.ra_social like lower(concat('%', :razonSocial, '%')) or e.no_comercial like lower(concat('%', :razonSocial, '%'))", nativeQuery = true)
	Page<Cliente> findByRazonSocialPag(String razonSocial, Pageable pageable);

	@Query(value = "select * from cliente c inner join empresa e on c.id_empresa = e.id_empresa "
			+ "where e.nu_ruc = :ruc", nativeQuery = true)
	Optional<Cliente> findByRuc(String ruc);

	@Query("select count(c)>0 from Cliente c where tipoPersona = :tipoPersona and persona = :persona")
	boolean existsCliente(TipoPersona tipoPersona, Persona persona);

	@Query("select count(c)>0 from Cliente c where c.tipoPersona = :tipoPersona and c.empresa = :empresa")
	boolean existsCliente(TipoPersona tipoPersona, Empresa empresa);

	@Override
	Page<Cliente> findAll(Pageable pageable);

	@Query(value = "SELECT COUNT(c.id_cliente) from comprobante c where id_cliente =:id",nativeQuery = true)
	long findEliminarComprobanteById(Long id);

	@Query(value = "SELECT COUNT(t.id_cliente) from tarjeta t where id_cliente =:id",nativeQuery = true)
	long findEliminarTarjetaById(Long id);

	@Query(value = "SELECT COUNT(ct.id_cliente) from cotizacion ct where id_cliente =:id",nativeQuery = true)
	long findEliminarCotizacionById(Long id);


}
