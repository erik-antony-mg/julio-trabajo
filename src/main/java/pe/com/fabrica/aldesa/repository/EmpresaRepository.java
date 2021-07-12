package pe.com.fabrica.aldesa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Direccion;
import pe.com.fabrica.aldesa.beans.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	Empresa findByRuc(String ruc);

	@Query("select e from Empresa e where e.razonSocial like %:razonSocial% or "
			+ "e.nombreComercial like %:razonSocial%")
	List<Empresa> findByRazonSocial(String razonSocial);

	@Query("select e from Empresa e where e.razonSocial like %:razonSocial% or "
			+ "e.nombreComercial like %:razonSocial%")
	Page<Empresa> findByRazonSocialPag(String razonSocial, Pageable pageable);

	@Override
	Page<Empresa> findAll(Pageable pageable);

	@Query(value = "SELECT COUNT(c.id_empresa) from cliente c where c.id_empresa =:id",nativeQuery = true)
	Long findEliminarClienteById(Long id);

	@Query(value = "SELECT COUNT(a.id_empresa) from agencia_aduanas a where id_empresa =:id",nativeQuery = true)
	Long findEliminarAgencuiaAduanasById(Long id);

	@Query(value = "SELECT COUNT(d.id_empresa) from deposito_temporal d where id_empresa =:id",nativeQuery = true)
	Long findEliminarDepositoTemporalById(Long id);

	@Query(value = "SELECT COUNT(p.id_empresa) from proveedor p where id_empresa =:id",nativeQuery = true)
	Long findEliminarProveedorById(Long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Empresa e set e.ruc = :ruc, e.razonSocial = :razonSocial, e.nombreComercial = :nombreComercial, e.direccion = :direccion , e.contacto = :contacto , e.telefono = :telefono , e.correo = :correo"
			+ " where e.idEmpresa = :idEmpresa")
	void updateEmpresa(Long idEmpresa, String ruc, String razonSocial, String nombreComercial, Direccion direccion,
			String contacto, String telefono, String correo);

}
