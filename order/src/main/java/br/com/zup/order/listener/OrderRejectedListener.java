package br.com.zup.order.listener;

import java.io.IOException;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.order.entity.Order;
import br.com.zup.order.event.OrderRejectedEvent;
import br.com.zup.order.repository.OrderRepository;

@Component
public class OrderRejectedListener {

    private ObjectMapper objectMapper;
    private OrderRepository orderRepository;

    public OrderRejectedListener(ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "rejected-orders", groupId = "order-group-id")
    public void listen(String message) throws IOException {
        OrderRejectedEvent event = this.objectMapper.readValue(message, OrderRejectedEvent.class);
        System.out.println("Received rejected event: " + event.getOrderId() + " - " + event.getReason());

        final Optional<Order> order = orderRepository.findById(event.getOrderId());

        order.ifPresent(a -> {
            a.setStatus("REJECTED");
            orderRepository.save(a);
        });
    }
}
