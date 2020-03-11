package br.com.zup.inventory.listener;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.inventory.event.PaymentRejectedEvent;
import br.com.zup.inventory.service.TicketReserveService;

@Component
public class PaymentListener {

    private ObjectMapper objectMapper;
    private TicketReserveService ticketReserveService;

    public PaymentListener(ObjectMapper objectMapper,
            TicketReserveService ticketReserveService) {
        this.objectMapper = objectMapper;
        this.ticketReserveService = ticketReserveService;
    }

    @KafkaListener(topics = "rejected-payments", groupId = "inventory-group-id")
    public void listen(String message) throws IOException {
        PaymentRejectedEvent event = this.objectMapper.readValue(message, PaymentRejectedEvent.class);
        System.out.println("Received payment rejected event: " + event.getOrder().getOrderId() + " - " + event.getReason());

        ticketReserveService.rollbackBookedTickets(event.getOrder().getOrderEntry());
    }
}
