package pe.com.fabrica.aldesa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Empresa;
import pe.com.fabrica.aldesa.beans.Persona;
import pe.com.fabrica.aldesa.beans.Proveedor;
import pe.com.fabrica.aldesa.beans.TipoPersona;


@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

	@Query(value = "select * from proveedor c inner join persona p on c.id_persona = p.id_persona "
			+ "where p.nombres like lower(concat('%', :nombre, '%')) or p.ap_paterno like lower(concat('%', :nombre, '%')) or "
			+ "p.ap_materno like lower(concat('%', :nombre, '%'))", nativeQuery = true)
	List<Proveedor> findByNombre(String nombre);

	@Query(value = "select * from proveedor where id_tipersona = :id_tipersona", nativeQuery = true)
	Page<Proveedor> findByTipoPersona(Integer id_tipersona , Pageable pageable);

	@Query(value = "select * from proveedor c inner join persona p on c.id_persona = p.id_persona "
			+ "where p.nu_doc = :numeroDocumento", nativeQuery = true)
	Optional<Proveedor> findByNumeroDocumento(String numeroDocumento);

	@Query(value = "select * from proveedor c inner join empresa e on c.id_empresa = e.id_empresa "
			+ "where e.ra_social like lower(concat('%', :razonSocial, '%')) or e.no_comercial like lower(concat('%', :razonSocial, '%'))", nativeQuery = true)
	List<Proveedor> findByRazonSocial(String razonSocial);

	@Query(value = "select * from proveedor c inner join empresa e on c.id_empresa = e.id_empresa "
			+ "where e.nu_ruc = :ruc", nativeQuery = true)
	Optional<Proveedor> findByRuc(String ruc);

	@Query("select count(c)>0 from Proveedor c where tipoPersona = :tipoPersona and persona = :persona")
	boolean existsProveedor(TipoPersona tipoPersona, Persona persona);

	@Query("select count(c)>0 from Proveedor c where c.tipoPersona = :tipoPersona and c.empresa = :empresa")
	boolean existsProveedor(TipoPersona tipoPersona, Empresa empresa);

	@Override
	Page<Proveedor> findAll(Pageable pageable);

}
