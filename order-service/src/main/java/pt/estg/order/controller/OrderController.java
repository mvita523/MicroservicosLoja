package pt.estg.order.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pt.estg.order.entity.OrderEntity;
import pt.estg.order.entity.OrderItem;
import pt.estg.order.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import java.util.Map;
import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {
  private final WebClient webClient;
  private final OrderRepository repo;
  private final ModelMapper mapper = new ModelMapper();
  public OrderController(WebClient webClient, OrderRepository repo){ this.webClient=webClient; this.repo=repo; }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String,Object> body){

        Long customerId = Long.valueOf(body.get("customerId").toString());

        // 1. VALIDAR CUSTOMER
        try {
            webClient.get()
                    .uri("http://identity-service:8080/identity/customers/{id}", customerId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return ResponseEntity.status(400).body("invalid customer");
        }

        List<Map<String,Object>> items = (List<Map<String,Object>>) body.get("items");

        // 2. RESERVAR STOCK
        for (Map<String,Object> it : items) {
            Long productId = Long.valueOf(it.get("productId").toString());
            Integer qty = Integer.valueOf(it.get("qty").toString());

            try {
                webClient.post()
                        .uri("http://catalog-service:8081/inventory/reserve?productId={p}&qty={q}", productId, qty)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

            } catch (Exception e) {

                // 2b. ROLLBACK
                for (Map<String,Object> it2 : items) {

                    Long p2 = Long.valueOf(it2.get("productId").toString());
                    Integer q2 = Integer.valueOf(it2.get("qty").toString());

                    try {
                        webClient.post()
                                .uri("http://catalog-service:8081/inventory/release?productId={p}&qty={q}", p2, q2)
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();
                    } catch (Exception ignored) {}
                }

                return ResponseEntity.status(409).body("stock not available for product " + productId);
            }
        }

        // 3. CRIAR ORDEM
        OrderEntity order = OrderEntity.builder()
                .customerId(customerId)
                .status("PAID")
                .build();

        List<OrderItem> its = items.stream()
                .map(m -> OrderItem.builder()
                        .productId(Long.valueOf(m.get("productId").toString()))
                        .qty(Integer.valueOf(m.get("qty").toString()))
                        .build())
                .toList();

        order.setItems(its);

        var saved = repo.save(order);

        // 4. NOTIFICAR SHIPMENT
        Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("orderId", saved.getId());
        payload.put("address", body.getOrDefault("address", "N/A"));

        webClient.post()
                .uri("http://notification-service:8083/shipments")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return ResponseEntity.ok(saved);
    }
}

