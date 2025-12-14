package pt.estg.catalog.dto;
import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDTO { private Long id; private String name; private String description; private Double price; }
