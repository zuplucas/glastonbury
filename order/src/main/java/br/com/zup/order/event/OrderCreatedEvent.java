package br.com.zup.order.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderCreatedEvent {

    private String orderId;
    private String customerId;
    private BigDecimal amount;
    private Map<String, Integer> orderEntry;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String customerId, BigDecimal amount, Map<String, Integer> orderEntry) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.orderEntry = orderEntry;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Map<String, Integer> getOrderEntry() {
        return orderEntry;
    }

    public void setOrderEntry(Map<String, Integer> orderEntry) {
        this.orderEntry = orderEntry;
    }
}
