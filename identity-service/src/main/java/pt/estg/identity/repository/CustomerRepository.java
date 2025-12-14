package pt.estg.identity.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.identity.entity.Customer;
import java.util.Optional;
public interface CustomerRepository extends JpaRepository<Customer,Long> {
  Optional<Customer> findByUsername(String username);
}
