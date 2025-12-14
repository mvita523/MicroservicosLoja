package pt.estg.identity.service;
import org.springframework.stereotype.Service;
import pt.estg.identity.repository.CustomerRepository;
import pt.estg.identity.entity.Customer;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class CustomerService {
  private final CustomerRepository repo;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  public CustomerService(CustomerRepository repo){this.repo=repo;}
  public Customer create(Customer c){ c.setPassword(encoder.encode(c.getPassword())); return repo.save(c); }
  public Optional<Customer> findById(Long id){ return repo.findById(id); }
  public Optional<Customer> findByUsername(String username){ return repo.findByUsername(username); }
  public boolean checkPassword(Customer c, String raw){ return encoder.matches(raw, c.getPassword()); }
}
