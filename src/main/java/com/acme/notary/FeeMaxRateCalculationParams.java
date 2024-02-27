package com.acme.notary;

public class FeeMaxRateCalculationParams {

    private Integer lowerBound = 0;
    private Integer base;
    private Double percent = 0d;
    private Double limit;

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(final Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Integer getBase() {
        return base;
    }

    public void setBase(final Integer base) {
        this.base = base;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(final Double percent) {
        this.percent = percent;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(final Double limit) {
        this.limit = limit;
    }
}
