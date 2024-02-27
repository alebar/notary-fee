package com.acme.notary;

import com.acme.notary.test.TestConstants;
import com.ruletalk.ChoiceQuestion;
import com.ruletalk.test.Expectation;
import com.ruletalk.test.TalkingBot;

import org.junit.Test;

import static com.acme.notary.test.Expectations.calculatedFeeMaxRate;
import static com.acme.notary.test.Expectations.fee;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.noValueQuestion;
import static com.acme.notary.test.TestConstants.IF_IN_FAMILY;
import static com.acme.notary.test.TestConstants.NO;
import static com.acme.notary.test.TestConstants.YES;
import static com.ruletalk.test.ConclusionExpectation.conclusionOfType;
import static com.ruletalk.test.NoConclusionExpectation.noConclusionOfType;
import static com.ruletalk.test.NoQuestionExpectation.noQuestionWithCode;
import static com.ruletalk.test.QuestionExpectation.questionWithCode;
import static com.ruletalk.test.TalkingBot.startTalk;
import static org.assertj.core.api.Assertions.assertThat;

public class RateCalculationTest {

    private final static String NON_CONSTANT_ACT_TYPE = "inny";

    @Test
    public void testMaxRateCalculation() {
        forSubjectWithValue(3000).expect(calculatedFeeMaxRate(100d), feeMessage(100d));
        forSubjectWithValue(3100).expect(calculatedFeeMaxRate(103d), feeMessage(103d));
        forSubjectWithValue(30001).expect(calculatedFeeMaxRate(710.01), feeMessage(710.01d));
        forSubjectWithValue(60001).expect(calculatedFeeMaxRate(1010d), feeMessage(1010d));
    }

    @Test
    public void whenCalculatedMaxRateExceedsLimitAskIfInFamily() {
        final int minimalValueForWhichFeeExceedsLimit = 2000000 + 292000 + 2;
        forSubjectWithValue(minimalValueForWhichFeeExceedsLimit).expect(
                questionWithCode(IF_IN_FAMILY).ofType(ChoiceQuestion.class),
                noConclusionOfType(Fee.class)
        )
                .chooseOption(IF_IN_FAMILY, YES)
                .expect(fee(7500d), feeMessage(7500d));

        forSubjectWithValue(minimalValueForWhichFeeExceedsLimit - 1).expect(
                noQuestionWithCode(IF_IN_FAMILY),
                fee(7500d)
        );
    }

    @Test
    public void whenCalculatedMaxRateExceedsLimitCutIt() {
        final int valueThatExceedsLimits = 2000000 + 1292000 + 6;

        forSubjectWithValue(valueThatExceedsLimits)
                .chooseOption(IF_IN_FAMILY, YES)
                .expect(calculatedFeeMaxRate(7500d), feeMessage(7500d));

        forSubjectWithValue(valueThatExceedsLimits)
                .chooseOption(IF_IN_FAMILY, NO)
                .expect(calculatedFeeMaxRate(10000d), feeMessage(10000d));
    }

    @Test
    public void discountShouldBeAppliedWhenRateFactorExists() {
        startTalk(
                NotarialActType.from(NON_CONSTANT_ACT_TYPE),
                SubjectValue.of(3100),
                FeeMaxRateFactor.of(0.5)
        ).expect(calculatedFeeMaxRate(51.5d), feeMessage(51.5d));
    }

    @Test
    public void assertThatRateCalculationParamsAreSetCorrectly() {
        Expectation expectedParams = calculationParams(100, 0d, 0);
        forSubjectWithValue(3000).expect(expectedParams);

        expectedParams = calculationParams(100, 3.0, 3000);
        forSubjectWithValue(3001).expect(expectedParams);
        forSubjectWithValue(10000).expect(expectedParams);

        expectedParams = calculationParams(310, 2.0, 10000);
        forSubjectWithValue(10001).expect(expectedParams);
        forSubjectWithValue(30000).expect(expectedParams);

        expectedParams = calculationParams(710, 1.0, 30000);
        forSubjectWithValue(30001).expect(expectedParams);
        forSubjectWithValue(60000).expect(expectedParams);

        expectedParams = calculationParams(1010, 0.4, 60000);
        forSubjectWithValue(60001).expect(expectedParams);
        forSubjectWithValue(1000000).expect(expectedParams);

        expectedParams = calculationParams(4770, 0.2, 1000000);
        forSubjectWithValue(1000001).expect(expectedParams);
        forSubjectWithValue(2000000).expect(expectedParams);

        expectedParams = calculationParams(6770, 0.25, 2000000);
        forSubjectWithValue(2000001).expect(expectedParams);
    }

    private TalkingBot forSubjectWithValue(final int value) {
        return startTalk(
                NotarialActType.from(NON_CONSTANT_ACT_TYPE),
                SubjectValue.of(value)
        ).expect(
                noValueQuestion(),
                noQuestionWithCode(TestConstants.CATEGORY),
                noQuestionWithCode(TestConstants.TYPE)
        );
    }

    private Expectation calculationParams(final Integer base, final Double percent, final Integer lowerBound) {
        return conclusionOfType(FeeMaxRateCalculationParams.class)
                .matchingCondition(params -> {
                    assertThat(params.getBase()).isEqualTo(base);
                    assertThat(params.getPercent()).isEqualTo(percent);
                    assertThat(params.getLowerBound()).isEqualTo(lowerBound);
                });
    }

}
