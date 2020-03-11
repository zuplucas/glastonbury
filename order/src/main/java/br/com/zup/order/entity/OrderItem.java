package br.com.zup.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "order_items")
public class OrderItem {

    @Id
    private String id;

    private String name;

    private BigDecimal amount;

    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(String id, String name, BigDecimal amount, Integer quantity) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
    }

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
}
