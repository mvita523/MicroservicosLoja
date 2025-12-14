package pt.estg.notification.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.estg.notification.entity.Shipment;
import pt.estg.notification.repository.ShipmentRepository;
import java.util.Map;
@RestController
public class NotificationController {
  private final ShipmentRepository repo;
  public NotificationController(ShipmentRepository repo){ this.repo=repo; }
  @PostMapping("/notifications") public ResponseEntity<String> notify(@RequestBody Map<String,Object> body){ System.out.println("NOTIFICATION: " + body); return ResponseEntity.ok("sent"); }
  @PostMapping("/shipments") public ResponseEntity<?> createShipment(@RequestBody Map<String,Object> body){ Shipment s = new Shipment(); s.setOrderId(Long.valueOf(body.get("orderId").toString())); s.setAddress(body.get("address").toString()); var saved = repo.save(s); System.out.println("SHIPMENT CREATED: " + saved.getId()); return ResponseEntity.ok(saved); }
  @GetMapping("/shipments/{id}") public ResponseEntity<?> get(@PathVariable Long id){ return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
}
