package pt.estg.notification.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.notification.entity.Shipment;
public interface ShipmentRepository extends JpaRepository<Shipment,Long>{}
