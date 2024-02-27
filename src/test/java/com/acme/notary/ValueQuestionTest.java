package com.acme.notary;

import org.junit.Test;

import static com.acme.notary.test.Expectations.calculatedFeeMaxRate;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.valueQuestion;
import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.acme.notary.test.TestConstants.VALUE;
import static com.ruletalk.test.TalkingBot.startTalk;

public class ValueQuestionTest {

    private final static String NON_CONSTANT_ACT_TYPE = "nieruch.przenies-wlasn-umowa-zobow";

    @Test
    public void shouldAskValueQuestionWhenContextHasNotarialActWithoutValue() {
        startTalk(NotarialActType.from(NON_CONSTANT_ACT_TYPE))
                .expect(valueQuestion())
                .reply(VALUE, "3000")
                .expect(calculatedFeeMaxRate(50d), feeMessage(50d));
    }

    @Test
    public void shouldAskValueQuestionWhenNotarialActWithoutValue() {
        startTalk()
                .chooseOption(CATEGORY, "nieruch")
                .chooseOption(TYPE, NON_CONSTANT_ACT_TYPE)
                .expect(valueQuestion())
                .reply(VALUE, "3000")
                .expect(calculatedFeeMaxRate(50d), feeMessage(50d));
    }

}
