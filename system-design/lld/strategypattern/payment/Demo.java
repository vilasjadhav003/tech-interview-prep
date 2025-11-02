package lld.strategypattern.payment;

import lld.strategypattern.payment.strategies.CardPaymentStrategy;
import lld.strategypattern.payment.strategies.PayPalPaymentStrategy;
import lld.strategypattern.payment.strategies.UpiPaymentStrategy;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        StrategyRegistry reg = new StrategyRegistry(
                List.of(new CardPaymentStrategy(), new UpiPaymentStrategy(), new PayPalPaymentStrategy()));
        PaymentService service = new PaymentService(reg);

        System.out.println(service.pay(new PaymentRequest(PaymentMethod.CARD, 1599, "tok_abc")).transactionId());
        System.out.println(service.pay(new PaymentRequest(PaymentMethod.UPI, 2999, "ak@okicici")).transactionId());
        System.out.println(service.pay(new PaymentRequest(PaymentMethod.PAYPAL, 4999, "user@example.com")).transactionId());
    }
}