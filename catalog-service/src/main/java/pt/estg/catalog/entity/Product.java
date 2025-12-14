package pt.estg.catalog.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Double price;

  @CreatedDate
  @Column(name = "creation_date")
  private Date creationDate;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;
}
