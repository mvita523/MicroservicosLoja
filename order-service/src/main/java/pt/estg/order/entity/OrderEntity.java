package pt.estg.order.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Table(name = "orders") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long customerId;
  private String status;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<OrderItem> items;
}
