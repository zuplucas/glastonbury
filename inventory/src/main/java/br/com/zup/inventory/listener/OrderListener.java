package br.com.zup.inventory.listener;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.inventory.event.OrderBookedEvent;
import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.event.OrderRejectedEvent;
import br.com.zup.inventory.exception.TicketNotAvailableException;
import br.com.zup.inventory.exception.TicketNotFoundException;
import br.com.zup.inventory.service.TicketReserveService;

@Component
public class OrderListener {

    private ObjectMapper objectMapper;
    private TicketReserveService ticketReserveService;
    private KafkaTemplate<String, OrderRejectedEvent> kafkaRejectedTemplate;
    private KafkaTemplate<String, OrderBookedEvent> kafkaBookedTemplate;

    public OrderListener(ObjectMapper objectMapper,
            TicketReserveService ticketReserveService,
            KafkaTemplate<String, OrderRejectedEvent> kafkaRejectedTemplate,
            KafkaTemplate<String, OrderBookedEvent> kafkaBookedTemplate) {
        this.objectMapper = objectMapper;
        this.ticketReserveService = ticketReserveService;
        this.kafkaRejectedTemplate = kafkaRejectedTemplate;
        this.kafkaBookedTemplate = kafkaBookedTemplate;
    }

    @KafkaListener(topics = "created-orders", groupId = "inventory-group-id")
    public void listen(String message) throws IOException {
        OrderCreatedEvent event = this.objectMapper.readValue(message, OrderCreatedEvent.class);
        System.out.println("Received event: " + event.getCustomerId());

        try {
            ticketReserveService.reserveTickets(event.getOrderEntry());
            publishOrderBooked(event);
        } catch (TicketNotFoundException tnfe) {
            publishOrderRejected(event, tnfe);
        } catch (TicketNotAvailableException tnae) {
            publishOrderRejected(event, tnae);
        }
    }

    private void publishOrderBooked(OrderCreatedEvent event) {
        System.out.println("BOOKED - " + event.getOrderId());
        OrderBookedEvent orderBookedEvent = new OrderBookedEvent();
        orderBookedEvent.setOrderCreatedEvent(event);
        kafkaBookedTemplate.send("booked-order", orderBookedEvent);
    }

    private void publishOrderRejected(OrderCreatedEvent event, TicketNotFoundException tnfe) {
        OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent();
        orderRejectedEvent.setCustomerId(event.getCustomerId());
        orderRejectedEvent.setOrderId(event.getOrderId());
        orderRejectedEvent.setTicketId(tnfe.getTicketId());
        orderRejectedEvent.setReason("INVALID_TICKET");
        publishOrderRejected(orderRejectedEvent);
    }

    private void publishOrderRejected(OrderCreatedEvent event, TicketNotAvailableException tnae) {
        OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent();
        orderRejectedEvent.setCustomerId(event.getCustomerId());
        orderRejectedEvent.setOrderId(event.getOrderId());
        orderRejectedEvent.setTicketId(tnae.getTicketId());
        orderRejectedEvent.setReason("AMOUNT_NOT_AVAILABLE");
        publishOrderRejected(orderRejectedEvent);
    }

    private void publishOrderRejected(OrderRejectedEvent orderRejectedEvent) {
        System.out.println("ORDER REJECTED - " + orderRejectedEvent.getTicketId()  + " - " + orderRejectedEvent.getReason());

        kafkaRejectedTemplate.send("rejected-orders", orderRejectedEvent);
    }
}
