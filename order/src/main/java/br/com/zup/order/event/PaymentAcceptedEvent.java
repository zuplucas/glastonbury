package br.com.zup.order.event;

public class PaymentAcceptedEvent {
    private OrderCreatedEvent orderCreatedEvent;

    public OrderCreatedEvent getOrderCreatedEvent() {
        return orderCreatedEvent;
    }

    public void setOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        this.orderCreatedEvent = orderCreatedEvent;
    }
}
