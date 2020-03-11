package br.com.zup.payment.listener;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.payment.configuration.KafkaConfiguration;
import br.com.zup.payment.event.OrderBookedEvent;
import br.com.zup.payment.event.PaymentAcceptedEvent;
import br.com.zup.payment.event.PaymentRejectedEvent;
import br.com.zup.payment.exception.PaymentRefusedException;
import br.com.zup.payment.service.PaymentService;

@Component
public class OrderBookedListener {

    private ObjectMapper objectMapper;
    private PaymentService paymentService;
    private KafkaTemplate<String, PaymentRejectedEvent> kafkaRejectedTemplate;
    private KafkaTemplate<String, PaymentAcceptedEvent> kafkaPaidTemplate;

    public OrderBookedListener(ObjectMapper objectMapper,
            PaymentService paymentService,
            KafkaTemplate<String, PaymentRejectedEvent> kafkaRejectedTemplate,
            KafkaTemplate<String, PaymentAcceptedEvent> kafkaPaidTemplate) {
        this.objectMapper = objectMapper;
        this.paymentService = paymentService;
        this.kafkaRejectedTemplate = kafkaRejectedTemplate;
        this.kafkaPaidTemplate = kafkaPaidTemplate;
    }

    @KafkaListener(topics = "booked-orders", groupId = KafkaConfiguration.CONSUMER_GROUP)
    public void listen(String message) throws IOException {
        OrderBookedEvent event = this.objectMapper.readValue(message, OrderBookedEvent.class);
        System.out.println("Received booked event: " + event.getOrderCreatedEvent().getOrderId());

        try {
            paymentService.pay(event.getOrderCreatedEvent().getCustomerId(), event.getOrderCreatedEvent().getAmount());
            publishOrderPaid(event);
        } catch (PaymentRefusedException pRejEx) {
            publishPaymentRejected(event, pRejEx);
        }
    }

    private void publishOrderPaid(OrderBookedEvent event) {
        System.out.println("PAID - " + event.getOrderCreatedEvent().getOrderId());
        PaymentAcceptedEvent paymentAcceptedEvent = new PaymentAcceptedEvent();
        paymentAcceptedEvent.setOrderCreatedEvent(event.getOrderCreatedEvent());
        kafkaPaidTemplate.send(KafkaConfiguration.TOPIC_PAID, paymentAcceptedEvent);
    }

    private void publishPaymentRejected(OrderBookedEvent event, PaymentRefusedException pRejEx) {
        System.out.println("REJECTED - " + event.getOrderCreatedEvent().getOrderId() + " - " + pRejEx.getReason());
        PaymentRejectedEvent paymentRejectedEvent = new PaymentRejectedEvent();
        paymentRejectedEvent.setOrder(event.getOrderCreatedEvent());
        paymentRejectedEvent.setReason(pRejEx.getReason());
        kafkaRejectedTemplate.send(KafkaConfiguration.TOPIC_REJECTED, paymentRejectedEvent);
    }

}
