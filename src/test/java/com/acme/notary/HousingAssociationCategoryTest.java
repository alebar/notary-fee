package com.acme.notary;

import com.ruletalk.test.TalkingBot;

import org.junit.Test;

import static com.acme.notary.test.Expectations.fee;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.noValueQuestion;
import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.ruletalk.test.ChoiceQuestionExpectation.choiceQuestionWithCode;
import static com.ruletalk.test.TalkingBot.startTalk;

public class HousingAssociationCategoryTest {

    private final static String HOUSING_ASSOCIATION_CATEGORY = "spoldzielnie";

    @Test
    public void testTypeQuestionForHousingAssociationCategory() {
        startTalk()
                .chooseOption(CATEGORY, HOUSING_ASSOCIATION_CATEGORY)
                .expect(
                        choiceQuestionWithCode(TYPE)
                                .withNOptions(2)
                                .havingOption("spoldzielnie.protokol-walne-zgromadz", "sporządzenie protokołu " +
                                        "walnego zgromadzenia spółdzielni")
                                .havingOption("spoldzielnie.protokol-zebranie-wspoln", "sporządzenie protokołu " +
                                        "zebrania wspólnoty mieszkaniowej")
                );
    }

    @Test
    public void expectProperConstantFeeMaxRate() {
        chooseType("spoldzielnie.protokol-walne-zgromadz").expect(fee(750d), noValueQuestion(), feeMessage(750d));
        chooseType("spoldzielnie.protokol-zebranie-wspoln").expect(fee(300d), noValueQuestion(), feeMessage(300d));
    }

    private TalkingBot chooseType(final String chosenType) {
        return startTalk()
                .chooseOption(CATEGORY, HOUSING_ASSOCIATION_CATEGORY)
                .chooseOption(TYPE, chosenType);
    }

}

