package br.com.zup.order.controller;

import br.com.zup.order.controller.request.CreateOrderRequest;
import br.com.zup.order.controller.response.OrderResponse;
import br.com.zup.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody CreateOrderRequest request) {
        return this.orderService.save(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderResponse> getOrders() {
        return this.orderService.findAll();
    }
}
