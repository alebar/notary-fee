package com.acme.notary;

public class SubjectValue {

    private final Integer value;

    public static SubjectValue of(final Integer value) {
        return new SubjectValue(value);
    }

    private SubjectValue(final Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SubjectValue{" + value + '}';
    }
}
