package br.com.zup.inventory.event;

public class PaymentRejectedEvent {
    private OrderCreatedEvent order;
    private String reason;

    public PaymentRejectedEvent() {
    }

    public OrderCreatedEvent getOrder() {
        return order;
    }

    public void setOrder(OrderCreatedEvent order) {
        this.order = order;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
