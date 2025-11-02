package lld.strategypattern.payment;

public interface PaymentStrategy {
    PaymentMethod supports();

    String pay(long amt, String ref);
}