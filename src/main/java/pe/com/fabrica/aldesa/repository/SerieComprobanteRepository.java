package pe.com.fabrica.aldesa.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.SerieComprobante;
import pe.com.fabrica.aldesa.beans.TipoComprobante;

@Repository
public interface SerieComprobanteRepository extends JpaRepository<SerieComprobante, Integer> {

	@Override
	Page<SerieComprobante> findAll(Pageable pageable);

	List<SerieComprobante> findByDescripcionContaining(String descripcion);

	List<SerieComprobante> findByTipoComprobanteNombreContaining(String nombre);

	@Query("select s from SerieComprobante s where s.serie = :serie and s.tipoComprobante = :tipoComprobante")
	Optional<SerieComprobante> searchBySerie(String serie, TipoComprobante tipoComprobante);

	@Query(value = "select * from serie_comprobante sc  where sc.id_ticomprobante = :tipoComprobante order by serie", nativeQuery = true)
	List<SerieComprobante> searchBySerieTipo(Integer tipoComprobante);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE serie_comprobante SET  numero= :numero WHERE id_serie  = :id", nativeQuery = true)
	void updateNumero(Integer id, String numero);

	
	@Query(value = "select * from serie_comprobante where id_ticomprobante = :id_ticomprobante", nativeQuery = true)
	Page<SerieComprobante> findByTipoComprobante(Integer id_ticomprobante, Pageable pageable);


	//nuevo
	@Query(value = "select * from serie_comprobante where serie like lower(concat('%',:serieb,'%')) ", nativeQuery = true)
	List<SerieComprobante> findBySerie(String serieb);
	
	//nuevo de nuevos
	@Query(value = " select * from serie_comprobante where id_ticomprobante=:id_tipocomprobante AND serie like lower(concat('%',:termino,'%'))  ", nativeQuery = true)
	List<SerieComprobante> findBySerieNew(Integer id_tipocomprobante, String termino,Pageable pageable);

	//nuevo descripcion
	@Query(value = " select * from serie_comprobante where id_ticomprobante=:id_tipocomprobante AND  descripcion like lower(concat('%',:termino,'%')) ", nativeQuery = true)
	List<SerieComprobante> findByDes(Integer id_tipocomprobante, String termino,Pageable pageable);
	
	//descripcion solo
	@Query(value = "select * from serie_comprobante where descripcion like lower(concat('%',:termino,'%')) ", nativeQuery = true)
	List<SerieComprobante> findByDesNew(String termino,Pageable pageable);
}
