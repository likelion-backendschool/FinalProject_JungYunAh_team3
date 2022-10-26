package com.ll.exam.final__2022_10_08.app.order.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.order.dto.OrderDto;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.entity.OrderStatus;
import com.ll.exam.final__2022_10_08.app.order.exception.OrderIdNotMatchedException;
import com.ll.exam.final__2022_10_08.app.order.repository.OrderRepository;
import com.ll.exam.final__2022_10_08.app.order.service.OrderService;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;
  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper;
  private final OrderRepository orderRepository;

  @PostMapping("/create")
  @PreAuthorize("isAuthenticated()")
  public String createOrder() {

    Order order = orderService.createOrder();
    return Rq.redirectWithMsg("/order/" + order.getId(),
        "%d번 주문이 생성되었습니다.".formatted(order.getId()));
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public String showOrder(@PathVariable Long id, Model model) {
    OrderDto order = orderService.findById(id);
    model.addAttribute("order", order);
    return "order/detail";
  }

  @GetMapping("/list")
  @PreAuthorize("isAuthenticated()")
  public String showList(Model model) {
    List<Order> orderList = orderService.findAll();
    model.addAttribute("orderList", orderList);
    return "order/list";
  }

  @PostMapping("/{id}/cancel")
  @PreAuthorize("isAuthenticated()")
  public String cancelOrder(@PathVariable Long id) {
    orderService.cancelOrder(id);
    return "order/list";
  }

  @PostConstruct
  private void init() {
    restTemplate.setErrorHandler(new ResponseErrorHandler() {
      @Override
      public boolean hasError(ClientHttpResponse response) {
        return false;
      }

      @Override
      public void handleError(ClientHttpResponse response) {
      }
    });
  }

  private final String SECRET_KEY = "test_sk_YZ1aOwX7K8mLGDD20L9ryQxzvNPG";

  @RequestMapping("/{id}/success")
  public String confirmPayment(@PathVariable long id, @RequestParam String paymentKey,
      @RequestParam String orderId, @RequestParam Long amount, Model model) throws Exception {

    Order order = orderService.findOrder(id);

    long orderIdInputed = Long.parseLong(orderId.split("__")[1]);

    if (id != orderIdInputed) {
      throw new OrderIdNotMatchedException();
    }

    HttpHeaders headers = new HttpHeaders();
    // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
    headers.set("Authorization",
        "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, String> payloadMap = new HashMap<>();
    payloadMap.put("orderId", orderId);
    payloadMap.put("amount", String.valueOf(order.calculatePayPrice()));

    HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap),
        headers);

    ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
        "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      JsonNode successNode = responseEntity.getBody();
      model.addAttribute("orderId", successNode.get("orderId").asText());
      String secret = successNode.get("secret").asText();
      order.setReadyStatus(OrderStatus.DONE);
      order.setPaymentDone();
      orderRepository.save(order);
      return "order/success";
    } else {
      JsonNode failNode = responseEntity.getBody();
      model.addAttribute("message", failNode.get("message").asText());
      model.addAttribute("code", failNode.get("code").asText());
      return "order/fail";
    }
  }

  @RequestMapping("/{id}/fail")
  public String failPayment(@RequestParam String message, @RequestParam String code, Model model) {
    model.addAttribute("message", message);
    model.addAttribute("code", code);
    return "order/fail";
  }
}
