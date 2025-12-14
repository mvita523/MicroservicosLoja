package pt.estg.catalog.dto;
import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryDTO { private Long id; private Long productId; private Integer quantity; }
