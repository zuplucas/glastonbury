package br.com.zup.payment.event;

public class OrderBookedEvent {
    private OrderCreatedEvent orderCreatedEvent;

    public OrderBookedEvent() {
    }

    public OrderCreatedEvent getOrderCreatedEvent() {
        return orderCreatedEvent;
    }

    public void setOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        this.orderCreatedEvent = orderCreatedEvent;
    }
}
