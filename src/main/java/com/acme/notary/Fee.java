package com.acme.notary;

public class Fee {

    private final Double maxRate;
    private final String measurementUnit;

    public static Fee.Builder builder() {
        return new Fee.Builder();
    }

    private Fee(final Double maxRate, final String measurementUnit) {
        this.maxRate = maxRate;
        this.measurementUnit = measurementUnit;
    }

    public Double getMaxRate() {
        return maxRate;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public static class Builder {
        private Double maxRate;
        private String measurementUnit;

        public Builder maxRate(final Double maxRate) {
            this.maxRate = maxRate;
            return this;
        }

        public Builder measurementUnit(final String measurementUnit) {
            this.measurementUnit = measurementUnit;
            return this;
        }

        public Fee build() {
            return new Fee(this.maxRate, this.measurementUnit);
        }
    }
}
