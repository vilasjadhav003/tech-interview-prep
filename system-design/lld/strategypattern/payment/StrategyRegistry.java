package lld.strategypattern.payment;

import java.util.*;

public class StrategyRegistry {
    private final Map<PaymentMethod, PaymentStrategy> map = new EnumMap<>(PaymentMethod.class);

    public StrategyRegistry(List<PaymentStrategy> s) {
        s.forEach(x -> map.put(x.supports(), x));
    }

    public PaymentStrategy resolve(PaymentMethod m) {
        return map.get(m);
    }
}