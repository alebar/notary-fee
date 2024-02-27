package com.acme.notary;

import org.junit.Test;

import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.ruletalk.test.ChoiceQuestionExpectation.choiceQuestionWithCode;
import static com.ruletalk.test.ConclusionExpectation.conclusionOfType;
import static com.ruletalk.test.TalkingBot.startTalk;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralTest {

    @Test
    public void testCategoryQuestion() {
        startTalk().then()
                .expect(
                        choiceQuestionWithCode(CATEGORY)
                                .withNOptions(6)
                                .havingOption("nieruch", "Nieruchomości")
                                .havingOption("testament", "Testamenty, spadki, dziedziczenie")
                                .havingOption("poswiadczenia", "Poświadczenia")
                                .havingOption("spoldzielnie", "Spółdzielnie, wspólnoty mieszkaniowe")
                                .havingOption("spolki", "Spółki, fundusze inwestycyjne")
                                .havingOption("inne", "Inne")
                );
    }

    @Test
    public void interpretShouldSetNotarialActType() {
        final String chosenType = "nieruch.przenies-wlasn-umowa-zobow";
        startTalk()
                .chooseOption(CATEGORY, "nieruch")
                .chooseOption(TYPE, chosenType)
                .expect(
                        conclusionOfType(NotarialActType.class)
                                .matchingCondition(type ->
                                        assertThat(type.getValue()).isEqualTo(chosenType)
                                )
                );
    }

}
