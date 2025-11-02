package lld.strategypattern.payment.strategies;

import lld.strategypattern.payment.PaymentException;
import lld.strategypattern.payment.PaymentMethod;
import lld.strategypattern.payment.PaymentStrategy;

import java.util.UUID;

public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentMethod supports() {
        return PaymentMethod.PAYPAL;
    }

    @Override
    public String pay(long amount, String email) {
        if (amount <= 0) throw new PaymentException("Amount must be positive");
        if (email == null || email.isBlank()) throw new PaymentException("Missing PayPal email");
        return "PP-" + UUID.randomUUID();
    }
}