package lld.strategypattern.payment;

public class PaymentException extends RuntimeException {
    public PaymentException(String msg) {
        super(msg);
    }
}