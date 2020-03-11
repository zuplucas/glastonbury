package br.com.zup.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ticket_inventory")
public class TicketInventory {

    @Id
    private String ticketId;
    private Integer available;

    public TicketInventory() {}

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
