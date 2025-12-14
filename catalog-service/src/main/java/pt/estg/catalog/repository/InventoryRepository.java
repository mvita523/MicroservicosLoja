package pt.estg.catalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.catalog.entity.Inventory;
import java.util.Optional;
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
  Optional<Inventory> findByProductId(Long productId);
}
