package br.com.zup.inventory.exception;

public class TicketNotFoundException extends RuntimeException {
    private String ticketId;

    public TicketNotFoundException(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketId() {
        return ticketId;
    }
}
