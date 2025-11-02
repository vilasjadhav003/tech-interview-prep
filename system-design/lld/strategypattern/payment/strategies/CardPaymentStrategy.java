package lld.strategypattern.payment.strategies;

import lld.strategypattern.payment.PaymentException;
import lld.strategypattern.payment.PaymentMethod;
import lld.strategypattern.payment.PaymentStrategy;

import java.util.UUID;

public final class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentMethod supports() {
        return PaymentMethod.CARD;
    }

    @Override
    public String pay(long amount, String tokenRef) {
        if (amount <= 0) throw new PaymentException("Amount must be positive");
        if (tokenRef == null || tokenRef.isBlank()) throw new PaymentException("Missing card token");
        return "CC-" + UUID.randomUUID();
    }
}