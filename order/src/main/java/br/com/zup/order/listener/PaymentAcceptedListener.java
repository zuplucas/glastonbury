package br.com.zup.order.listener;

import java.io.IOException;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.order.entity.Order;
import br.com.zup.order.event.OrderCreatedEvent;
import br.com.zup.order.event.PaymentAcceptedEvent;
import br.com.zup.order.repository.OrderRepository;

@Component
public class PaymentAcceptedListener {

    private ObjectMapper objectMapper;
    private OrderRepository orderRepository;

    public PaymentAcceptedListener(ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "paid-orders", groupId = "order-group-id")
    public void listen(String message) throws IOException {
        PaymentAcceptedEvent event = this.objectMapper.readValue(message, PaymentAcceptedEvent.class);
        OrderCreatedEvent orderCreatedEvent = event.getOrderCreatedEvent();
        System.out.println("Received paid event: " + orderCreatedEvent.getOrderId());

        final Optional<Order> order = orderRepository.findById(orderCreatedEvent.getOrderId());

        order.ifPresent(a -> {
            a.setStatus("COMPLETED");
            orderRepository.save(a);
        });
    }
}
