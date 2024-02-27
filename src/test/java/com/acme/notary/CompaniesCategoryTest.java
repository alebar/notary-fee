package com.acme.notary;

import com.ruletalk.Phrase;
import com.ruletalk.test.TalkingBot;

import org.junit.Test;

import static com.acme.notary.test.Expectations.fee;
import static com.acme.notary.test.Expectations.feeMaxRateFactor;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.noValueQuestion;
import static com.acme.notary.test.Expectations.valueQuestion;
import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.FACTOR_ONE_TENTH;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.acme.notary.test.TestConstants.VALUE;
import static com.ruletalk.test.ChoiceQuestionExpectation.choiceQuestionWithCode;
import static com.ruletalk.test.QuestionExpectation.questionWithCode;
import static com.ruletalk.test.TalkingBot.startTalk;
import static org.assertj.core.api.Assertions.assertThat;

public class CompaniesCategoryTest {

    private final static String COMPANIES_CATEGORY = "spolki";

    @Test
    public void testTypeQuestionForCompaniesCategory() {
        startTalk()
                .chooseOption(CATEGORY, COMPANIES_CATEGORY)
                .expect(
                        choiceQuestionWithCode(TYPE)
                                .withNOptions(7)
                                .havingOption("spolki.oswiad-przystapienie-zoo", "oświadczenie o przystąpieniu " +
                                        "do spółki z ograniczoną odpowiedzialnością lub objęciu udziałów w tej spółce")
                                .havingOption("spolki.zgoda-nie-w-statucie", "oświadczenie zawierające zgodę " +
                                        "osób przystępujących do spółki akcyjnej na zawiązanie spółki, " +
                                        "na brzmienie statutu i na objęcie akcji, wyrażoną w innym akcie niż akt obejmujący statut")
                                .havingOption("spolki.protokol-zgromadz-zoo", "sporządzenie protokołu " +
                                        "zgromadzenia wspólników spółki z ograniczoną odpowiedzialnością")
                                .havingOption("spolki.protokol-walne-zgromadz-akcjon", "sporządzenie protokołu " +
                                        "walnego zgromadzenia akcjonariuszy")
                                .havingOption("spolki.protokol-zgromadz-innej-spolki", "sporządzenie protokołu " +
                                        "zgromadzenia wspólników innej spółki niż z o.o lub akcyjnej " +
                                        "albo protokołu posiedzenia zarządu, rady nadzorczej " +
                                        "lub komisji rewizyjnej spółki prawa handlowego")
                                .havingOption("spolki.protokol-zmiana-statutu-fund-inw", "sporządzenie protokołu " +
                                        "zawierającego oświadczenie towarzystwa funduszy inwestycyjnych " +
                                        "o zmianie statutu funduszu inwestycyjnego")
                                .havingOption("spolki.protokol-podw-kapitalu-zaklad", "sporządzenie protokołu " +
                                        "dokumentującego podwyższenie kapitału zakładowego spółki")
                );
    }

    @Test
    public void expectProperConstantFeeMaxRate() {
        chooseType("spolki.protokol-zgromadz-zoo").expect(fee(750d), noValueQuestion(), feeMessage(750d));
        chooseType("spolki.protokol-walne-zgromadz-akcjon").expect(fee(1100d), noValueQuestion(), feeMessage(1100d));
        chooseType("spolki.protokol-zgromadz-innej-spolki").expect(fee(500d), noValueQuestion(), feeMessage(500d));
        chooseType("spolki.protokol-zmiana-statutu-fund-inw").expect(fee(1000d), noValueQuestion(), feeMessage(1000d));
    }

    @Test
    public void expectProperFeeMaxRateFactors() {
        chooseType("spolki.oswiad-przystapienie-zoo").expect(feeMaxRateFactor(FACTOR_ONE_TENTH), valueQuestion());
        chooseType("spolki.zgoda-nie-w-statucie").expect(feeMaxRateFactor(FACTOR_ONE_TENTH), valueQuestion());
    }

    @Test
    public void expectChangedValueQuestionDescription() {
        chooseType("spolki.protokol-podw-kapitalu-zaklad").expect(
                questionWithCode(VALUE).matchingCondition(q ->
                    assertThat(q.getPhrase()).
                            isEqualTo(Phrase.of("Wartość o jaką został podwyższony kapitał zakładowy"))
                )
        );
    }

    private TalkingBot chooseType(final String chosenType) {
        return startTalk()
                .chooseOption(CATEGORY, COMPANIES_CATEGORY)
                .chooseOption(TYPE, chosenType);
    }
}
