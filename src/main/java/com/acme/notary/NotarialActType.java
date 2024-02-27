package com.acme.notary;

public class NotarialActType {

    private final String value;

    public static NotarialActType from(final String value) {
        return new NotarialActType(value);
    }

    private NotarialActType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NotarialActType{" + value + '}';
    }

}
