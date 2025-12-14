package pt.estg.catalog.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.estg.catalog.entity.Product;
import pt.estg.catalog.entity.Inventory;
import pt.estg.catalog.dto.ProductDTO;
import pt.estg.catalog.dto.InventoryDTO;
import pt.estg.catalog.repository.ProductRepository;
import pt.estg.catalog.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import java.util.List;
@RestController
public class CatalogController {
  private final ProductRepository productRepo;
  private final InventoryRepository inventoryRepo;
  private final ModelMapper mapper = new ModelMapper();
  public CatalogController(ProductRepository p, InventoryRepository i){
      this.productRepo = p; this.inventoryRepo = i; }
  @GetMapping("/products") public List<ProductDTO> list(){
      return productRepo.findAll().stream().map
              (p->mapper.map(p,ProductDTO.class)).toList(); }
  @PostMapping("/products") public ProductDTO create(@RequestBody Product p)
  { var saved = productRepo.save(p); return mapper.map(saved, ProductDTO.class); }
  @GetMapping("/products/{id}") public ResponseEntity<ProductDTO> get(@PathVariable Long id){
      return productRepo.findById(id).map(p->ResponseEntity.ok(mapper.map(p,ProductDTO.class)))
              .orElse(ResponseEntity.notFound().build()); }
  @PostMapping("/inventory") public ResponseEntity<InventoryDTO>
  createInventory(@RequestBody InventoryDTO dto){ Inventory inv = Inventory.builder()
          .productId(dto.getProductId()).quantity(dto.getQuantity()==null?0:dto.getQuantity()).build();
      var saved = inventoryRepo.save(inv); return ResponseEntity.ok(mapper.map(saved, InventoryDTO.class)); }
  @GetMapping("/inventory/{productId}") public ResponseEntity<InventoryDTO>
  inventory(@PathVariable Long productId){ return inventoryRepo.findByProductId(productId)
          .map(i->ResponseEntity.ok(mapper.map(i,InventoryDTO.class))).orElse(ResponseEntity
                  .notFound().build()); }
  @PostMapping("/inventory/reserve") public ResponseEntity<String>
  reserve(@RequestParam Long productId, @RequestParam Integer qty){ var opt = inventoryRepo
          .findByProductId(productId); if(opt.isEmpty()) return ResponseEntity.status(404).body("no inventory"); Inventory inv = opt.get(); if(inv.getQuantity() < qty) return ResponseEntity.status(409).body("insufficient stock"); inv.setQuantity(inv.getQuantity() - qty); inventoryRepo.save(inv); return ResponseEntity.ok("reserved"); }
  @PostMapping("/inventory/release") public ResponseEntity<String>
  release(@RequestParam Long productId, @RequestParam Integer qty){ var opt = inventoryRepo
          .findByProductId(productId); if(opt.isEmpty()) return ResponseEntity.status(404)
          .body("no inventory"); Inventory inv = opt.get(); inv.setQuantity(inv.getQuantity() + qty);
          inventoryRepo.save(inv); return ResponseEntity.ok("released"); }
}
