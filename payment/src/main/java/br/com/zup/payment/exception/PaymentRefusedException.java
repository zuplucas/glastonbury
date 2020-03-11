package br.com.zup.payment.exception;

import br.com.zup.payment.event.OrderCreatedEvent;

public class PaymentRefusedException extends RuntimeException {
    private String reason;

    public PaymentRefusedException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}