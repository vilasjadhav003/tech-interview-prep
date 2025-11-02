package lld.strategypattern.payment.strategies;

import lld.strategypattern.payment.PaymentException;
import lld.strategypattern.payment.PaymentMethod;
import lld.strategypattern.payment.PaymentStrategy;

import java.util.UUID;

public class UpiPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentMethod supports() {
        return PaymentMethod.UPI;
    }

    @Override
    public String pay(long amount, String upiId) {
        if (amount <= 0) throw new PaymentException("Amount must be positive");
        if (upiId == null || !upiId.contains("@")) throw new PaymentException("Invalid UPI ID");
        return "UPI-" + UUID.randomUUID();
    }
}
