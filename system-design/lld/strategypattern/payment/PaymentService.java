package lld.strategypattern.payment;

public class PaymentService {
    private final StrategyRegistry reg;

    public PaymentService(StrategyRegistry r) {
        this.reg = r;
    }

    public PaymentResponse pay(PaymentRequest req) {
        PaymentStrategy s = reg.resolve(req.method());
        String id = s.pay(req.amountMinor(), req.reference());
        return new PaymentResponse(id, req.method(), req.amountMinor());
    }
}