package br.com.zup.inventory.exception;

public class TicketNotAvailableException extends RuntimeException {
    private String ticketId;
    private Integer amount;
    private Integer available;

    public TicketNotAvailableException(String ticketId, Integer amount, Integer available) {
        this.ticketId = ticketId;
        this.amount = amount;
        this.available = available;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAvailable() {
        return available;
    }
}
