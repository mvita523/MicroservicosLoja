package pt.estg.catalog.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.catalog.entity.Product;
public interface ProductRepository extends JpaRepository<Product,Long>{}
