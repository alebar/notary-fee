package com.acme.notary;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class NotarialActTypesNotRelatedToValue {

    private final Collection<String> types;

    public NotarialActTypesNotRelatedToValue(final String ... types) {
        this.types = new HashSet<>(Arrays.asList(types));
    }

    public Collection<String> getTypes() {
        return types;
    }
}
