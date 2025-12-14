package pt.estg.identity.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String email;
    @Builder.Default
    private boolean active = true;
}
