package lld.strategypattern.payment;

public record PaymentResponse(String transactionId, PaymentMethod method, long amountMinor) {
}