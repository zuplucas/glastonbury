package br.com.zup.order.controller.response;

import br.com.zup.order.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {

    private String id;

    private String customerId;

    private BigDecimal amount;

    private List<OrderItemResponse> items;

    private String status;

    public OrderResponse() {
    }

    public OrderResponse(String id, String customerId, BigDecimal amount, List<OrderItemResponse> items, String status) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.items = items;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getAmount(),
                order.getItems().stream().map(OrderItemResponse::fromEntity).collect(Collectors.toList()),
                order.getStatus()
        );
    }
}
