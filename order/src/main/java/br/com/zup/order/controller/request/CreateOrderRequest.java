package br.com.zup.order.controller.request;

import br.com.zup.order.entity.Order;
import br.com.zup.order.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateOrderRequest {

    private String customerId;

    private BigDecimal amount;

    private List<OrderItemPart> items;

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

    public List<OrderItemPart> getItems() {
        return items;
    }

    public void setItems(List<OrderItemPart> items) {
        this.items = items;
    }

    public Order toEntity() {
        return new Order(
                UUID.randomUUID().toString(),
                this.customerId,
                this.amount,
                this.items.stream()
                        .map(OrderItemPart::toEntity)
                        .collect(Collectors.toList()),
                "pending"
        );
    }

    public static class OrderItemPart {

        private String id;

        private String name;

        private BigDecimal amount;

        private Integer quantity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public OrderItem toEntity() {
            return new OrderItem(
                    this.id,
                    this.name,
                    this.amount,
                    this.quantity
            );
        }
    }
}
