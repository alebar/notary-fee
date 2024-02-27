package com.acme.notary;

public class IsNotarialActDoneInFamily {

    private final Boolean value;

    public static IsNotarialActDoneInFamily of(final Boolean value) {
        return new IsNotarialActDoneInFamily(value);
    }

    private IsNotarialActDoneInFamily(final boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IsNotarialActDoneInFamily{" + value + '}';
    }

}
