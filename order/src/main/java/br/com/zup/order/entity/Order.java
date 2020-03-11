package br.com.zup.order.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "orders")
public class Order {

    @Id
    private String id;

    private String customerId;

    private BigDecimal amount;

    @ManyToMany
    @Cascade(CascadeType.ALL)
    private List<OrderItem> items;

    private String status;

    public Order() {
    }

    public Order(String id, String customerId, BigDecimal amount, List<OrderItem> items, String status) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
