package lld.strategypattern.payment;

public record PaymentRequest(PaymentMethod method, long amountMinor, String reference) {
}