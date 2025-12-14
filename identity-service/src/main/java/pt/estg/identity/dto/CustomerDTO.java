package pt.estg.identity.dto;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO{
  private Long id;
  private String username;
  private String email;
  private boolean active;
}
