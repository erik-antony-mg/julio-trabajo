package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Choferes;

@Repository
public interface ChoferRepository extends JpaRepository<Choferes, Long> {

	@Override
	Page<Choferes> findAll(Pageable pageable);

	@Query(value = "SELECT  * from chofer c  inner join persona p ON  c.id_persona = p.id_persona where p.nu_doc  = :documento", nativeQuery = true)
	Choferes findByDocumento(String documento);

	@Query(value = "SELECT  * from chofer c  inner join persona p ON  c.id_persona = p.id_persona where (p.nombres like %:filtro%) or (p.ap_paterno like  %:filtro%) or (p.ap_materno like  %:filtro%)", nativeQuery = true)
	List<Choferes> findByNombreCompleto(String filtro);

	@Query(value = "SELECT COUNT(tp.id_chofer) from ticket_pesaje tp where id_chofer =:id",nativeQuery = true)
	Long findEliminarTicketPesajeById(Long id);
}
