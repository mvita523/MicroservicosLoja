package pt.estg.identity.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.estg.identity.entity.Customer;
import pt.estg.identity.dto.CustomerDTO;
import pt.estg.identity.service.CustomerService;
import pt.estg.identity.security.JwtUtil;
import java.util.Optional;
@RestController
@RequestMapping("/identity")
public class IdentityController {
  private final CustomerService service;
  private final JwtUtil jwt = new JwtUtil();
  public IdentityController(CustomerService service){ this.service=service; }
  @PostMapping("/register")
  public ResponseEntity<CustomerDTO> register(@RequestBody Customer c){
    Customer saved = service.create(c);
    CustomerDTO dto = CustomerDTO.builder().id(saved.getId()).username(saved.getUsername()).email(saved.getEmail()).active(saved.isActive()).build();
    return ResponseEntity.ok(dto);
  }
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Customer creds){
    Optional<Customer> opt = service.findByUsername(creds.getUsername());
    if(opt.isPresent() && service.checkPassword(opt.get(), creds.getPassword())){
      String token = jwt.generateToken(opt.get().getUsername(), opt.get().getId());
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.status(401).body("invalid credentials");
  }
  @GetMapping("/customers/{id}")
  public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id){
    return service.findById(id).map(c->{ return ResponseEntity.ok(CustomerDTO.builder().id(c.getId()).username(c.getUsername()).email(c.getEmail()).active(c.isActive()).build()); }).orElse(ResponseEntity.notFound().build());
  }
  @PatchMapping("/customers/{id}/status")
  public ResponseEntity<CustomerDTO> setStatus(@PathVariable Long id, @RequestParam boolean active){
    var opt = service.findById(id);
    if(opt.isEmpty()) return ResponseEntity.notFound().build();
    var c = opt.get(); c.setActive(active); service.create(c);
    return ResponseEntity.ok(CustomerDTO.builder().id(c.getId()).username(c.getUsername()).email(c.getEmail()).active(c.isActive()).build());
  }
}
