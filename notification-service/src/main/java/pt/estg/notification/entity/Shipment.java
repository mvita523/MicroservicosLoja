package pt.estg.notification.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Shipment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long orderId;
  private String address;
  @Builder.Default private String status = "CREATED";
}
