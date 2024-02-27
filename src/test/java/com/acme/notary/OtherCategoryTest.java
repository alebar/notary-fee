package com.acme.notary;

import com.ruletalk.test.TalkingBot;

import org.junit.Test;

import static com.acme.notary.test.Expectations.fee;
import static com.acme.notary.test.Expectations.feeMaxRateFactor;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.noValueQuestion;
import static com.acme.notary.test.Expectations.valueQuestion;
import static com.acme.notary.test.TestConstants.AUTHORIZED_ACTS_COUNT;
import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.FACTOR_HALF;
import static com.acme.notary.test.TestConstants.FACTOR_QUARTER;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.ruletalk.test.ChoiceQuestionExpectation.choiceQuestionWithCode;
import static com.ruletalk.test.TalkingBot.startTalk;

public class OtherCategoryTest {

    private final static String OTHER_CATEGORY = "inne";
    private final static String STORAGE_FEE_UNIT = "za każdy dokument, za każdy rozpoczęty miesiąc";


    @Test
    public void testTypeQuestionForRealEstateCategory() {
        startTalk()
                .chooseOption(CATEGORY, OTHER_CATEGORY)
                .expect(
                        choiceQuestionWithCode(TYPE)
                                .withNOptions(12)
                                .havingOption("inne.licytacja", "przebieg licytacji lub przetargu")
                                .havingOption("inne.przechowanie-pieniedzy", "przyjęcie na przechowanie " +
                                        "pieniędzy w walucie polskiej lub obcej")
                                .havingOption("inne.losowanie-nagrody", "losowanie nagrody")
                                .havingOption("inne.umowa-zobow", "umowa zobowiązująca, " +
                                        "zawarta pod warunkiem lub z zastrzeżeniem terminu,")
                                .havingOption("inne.potw-oswiad-woli", "potwierdzenie oświadczenia woli " +
                                        "zawartego w innym akcie notarialnym")
                                .havingOption("inne.umowa-majatk-malzenska", "umowa majątkowa małżeńska")
                                .havingOption("inne.pelnomocnictwo", "pełnomocnictwo")
                                .havingOption("inne.protokol-niedojscia-licytacji-do-skutku", "sporządzenie protokołu " +
                                        "niedojścia do skutku licytacji, przetargu lub losowania nagród")
                                .havingOption("inne.przechowanie-dokumentu-protokol", "sporządzenie protokołu " +
                                        "przyjęcia dokumentu na przechowanie")
                                .havingOption("inne.przechowanie-dokumentu", "przechowanie dokumentu")
                                .havingOption("inne.protokol-inny", "sporządzenie innego protokołu")
                                .havingOption("inne.doreczenie-oswiadczenia", "doręczenie stronie przeciwnej " +
                                        "oświadczenia wniesionego ustnie do protokołu albo wręczonego lub przesłanego notariuszowi")
                );
    }

    @Test
    public void expectProperConstantFeeMaxRate() {
        chooseType("inne.umowa-majatk-malzenska").expect(fee(400d), noValueQuestion(), feeMessage(400d));
        chooseType("inne.protokol-niedojscia-licytacji-do-skutku").expect(fee(100d), noValueQuestion(), feeMessage(100d));
        chooseType("inne.przechowanie-dokumentu-protokol").expect(fee(50d), noValueQuestion(), feeMessage(50d));
        chooseType("inne.przechowanie-dokumentu").expect(
                fee(20d, STORAGE_FEE_UNIT), noValueQuestion(), feeMessage(20d, STORAGE_FEE_UNIT)
        );
        chooseType("inne.protokol-inny").expect(fee(200d), noValueQuestion(), feeMessage(200d));
        chooseType("inne.doreczenie-oswiadczenia").expect(fee(40d), noValueQuestion(), feeMessage(40d));
    }

    @Test
    public void expectProperMaxFeeRateFactors() {
        chooseType("inne.licytacja").expect(feeMaxRateFactor(FACTOR_HALF), valueQuestion());
        chooseType("inne.przechowanie-pieniedzy").expect(feeMaxRateFactor(FACTOR_HALF), valueQuestion());
        chooseType("inne.losowanie-nagrody").expect(feeMaxRateFactor(FACTOR_QUARTER), valueQuestion());
        chooseType("inne.umowa-zobow").expect(feeMaxRateFactor(FACTOR_HALF), valueQuestion());
        chooseType("inne.potw-oswiad-woli").expect(feeMaxRateFactor(FACTOR_QUARTER), valueQuestion());
    }

    @Test
    public void expectQuestionAboutNumberOfAuthorizedActs() {
        chooseType("inne.pelnomocnictwo").expect(
                choiceQuestionWithCode(AUTHORIZED_ACTS_COUNT)
                        .withNOptions(2)
                        .havingOption("inne.pelnomocnictwo.jedno", "Do dokonania jednej czynności")
                        .havingOption("inne.pelnomocnictwo.wiele", "Do dokonania więcej niż jednej czynności")
        );
    }

    @Test
    public void expectProperFeeForPowerOfAttorneyType() {
        chooseType("inne.pelnomocnictwo")
                .chooseOption(AUTHORIZED_ACTS_COUNT, "inne.pelnomocnictwo.jedno")
                .expect(fee(30d), noValueQuestion(), feeMessage(30d));

        chooseType("inne.pelnomocnictwo")
                .chooseOption(AUTHORIZED_ACTS_COUNT, "inne.pelnomocnictwo.wiele")
                .expect(fee(100d), noValueQuestion(), feeMessage(100d));
    }

    private TalkingBot chooseType(final String chosenType) {
        return startTalk()
                .chooseOption(CATEGORY, OTHER_CATEGORY)
                .chooseOption(TYPE, chosenType);
    }

}
