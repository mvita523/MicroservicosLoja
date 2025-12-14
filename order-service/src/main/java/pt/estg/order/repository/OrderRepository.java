package pt.estg.order.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.order.entity.OrderEntity;
public interface OrderRepository extends JpaRepository<OrderEntity,Long>{}
