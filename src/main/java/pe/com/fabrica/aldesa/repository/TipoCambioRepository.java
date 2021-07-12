package pe.com.fabrica.aldesa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.TipoCambio;

@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Integer> {
    @Query(value ="select * from tipo_cambio order by fe_ticambio desc",nativeQuery = true)
    Page<TipoCambio> findAllFechaCambio(Pageable pageable);

    @Query(value ="select * from tipo_cambio where fe_ticambio between :fechaInicial and :fechaFinal order by fe_ticambio desc",nativeQuery = true)
    Page<TipoCambio> searchByRangoFechas(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal, Pageable pageable);

    @Query(value = "SELECT  * from tipo_cambio tc where fe_ticambio  = :fecha", nativeQuery = true)
    List<TipoCambio> findbyFecha(Date fecha);

    @Query(value = "select * from tipo_cambio tc order by id_ticambio DESC limit 1", nativeQuery = true)
    TipoCambio ultimoRegistro();

    @Query(value = "SELECT  * from tipo_cambio tc where fe_ticambio  BETWEEN :fecha1 AND  :fecha2", nativeQuery = true)
    List<TipoCambio> obteneerRangoFecha(Date fecha1 , Date fecha2);

    @Query(value = "SELECT null as fe_modi , MONTH(fe_ticambio) as us_crea , null as us_modi , SUM(id_ticambio + 1) AS id_ticambio , null as fe_crea, AVG(cambio_venta) AS cambio_venta ,AVG(cambio_compra) AS cambio_compra , null AS fe_ticambio FROM tipo_cambio WHERE YEAR(fe_ticambio)= :año GROUP BY MONTH(fe_ticambio) ORDER BY MONTH(fe_ticambio) ASC", nativeQuery = true)
    List<TipoCambio> obtenerPromedio(Integer año);

}
