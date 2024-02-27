package com.acme.notary;

public class FeeMaxRateFactor {

    private final Double value;

    public static FeeMaxRateFactor of(final Double value) {
        return new FeeMaxRateFactor(value);
    }

    private FeeMaxRateFactor(final Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
