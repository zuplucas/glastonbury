package br.com.zup.payment.service;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.stereotype.Service;

import br.com.zup.payment.exception.PaymentRefusedException;

@Service
public class PaymentService {

    private Random random = new Random();

    public void pay(String customerId, BigDecimal amount) {
        final boolean hasCredit = random.nextBoolean();
        System.out.println("Requiring payment - " + customerId + " - " + amount + " - " + hasCredit);

        if(!hasCredit) {
            throw new PaymentRefusedException("INSUFFICIENT CREDIT");
        }
    }
}
