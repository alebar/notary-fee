package com.acme.notary.test;

import com.acme.notary.Fee;
import com.acme.notary.FeeMaxRateFactor;
import com.ruletalk.test.Expectation;

import java.util.Optional;

import static com.acme.notary.test.TestConstants.VALUE;
import static com.ruletalk.test.ConclusionExpectation.conclusionOfType;
import static com.ruletalk.test.MessageExpectation.messageWithCode;
import static com.ruletalk.test.NoQuestionExpectation.noQuestionWithCode;
import static com.ruletalk.test.QuestionExpectation.questionWithCode;
import static org.assertj.core.api.Assertions.assertThat;

public class Expectations {

    public static Expectation feeMaxRateFactor(final double expectedDiscountRate) {
        return conclusionOfType(FeeMaxRateFactor.class).matchingCondition(factor ->
                assertThat(factor.getValue()).isEqualTo(expectedDiscountRate)
        );
    }

    public static Expectation fee(final double expectedValue) {
        return fee(expectedValue, null);
    }

    public static Expectation fee(final double expectedValue, final String measurementUnit) {
        return conclusionOfType(Fee.class).matchingCondition(fee -> {
            assertThat(fee.getMaxRate()).isEqualTo(expectedValue);
            Optional.ofNullable(measurementUnit).ifPresent(u ->
                    assertThat(fee.getMeasurementUnit()).isEqualTo(u)
            );
        });
    }

    public static Expectation valueQuestion() {
        return questionWithCode(VALUE);
    }

    public static Expectation noValueQuestion() {
        return noQuestionWithCode(VALUE);
    }

    public static Expectation feeMessage(final double expectedValue) {
        return feeMessage(expectedValue, null);
    }

    public static Expectation feeMessage(final double expectedValue, final String expectedMeasurementUnit) {
        final String unitString = Optional.ofNullable(expectedMeasurementUnit)
                .map(unit -> " " + unit)
                .orElse("");
        final String expectedMessageContent = String.format(
                "Maksymalna stawka taksy notarialnej wynosi %.2f zł%s.",
                expectedValue, unitString
        );
        return messageWithCode("fee-max-rate-message")
                .containing(expectedMessageContent);
    }

    public static Expectation calculatedFeeMaxRate(final Double expectedMaxRate) {
        return conclusionOfType(Fee.class).matchingCondition(fee ->
                assertThat(fee.getMaxRate()).isEqualTo(expectedMaxRate)
        );
    }

}
