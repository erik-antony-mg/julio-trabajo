package pe.com.fabrica.aldesa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.com.fabrica.aldesa.beans.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {


    @Query(value = "SELECT  * from vehiculo v inner join tipo_vehiculo tv  on v.id_tivehiculo  = tv.id_tivehiculo where (v.marca LIKE %:filtro% OR v.placa LIKE %:filtro%) or tv.nombre  LIKE %:filtro%", nativeQuery = true)
    List<Vehiculo> filtradoVehiculos(String filtro);

    @Query(value = "SELECT COUNT(tp.id_vehiculo) from ticket_pesaje tp where id_vehiculo =:id",nativeQuery = true)
    Long findEliminarTicketPesajeById(Long id);
}
