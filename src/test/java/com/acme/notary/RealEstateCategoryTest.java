package com.acme.notary;

import com.ruletalk.ChoiceQuestion;
import com.ruletalk.test.Expectation;
import com.ruletalk.test.TalkingBot;

import org.junit.Test;

import static com.acme.notary.test.Expectations.fee;
import static com.acme.notary.test.Expectations.feeMaxRateFactor;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.noValueQuestion;
import static com.acme.notary.test.Expectations.valueQuestion;
import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.DISPOSAL_DETAILED_TYPE;
import static com.acme.notary.test.TestConstants.FACTOR_HALF;
import static com.acme.notary.test.TestConstants.FACTOR_QUARTER;
import static com.acme.notary.test.TestConstants.HOUSING_MORTGAGE;
import static com.acme.notary.test.TestConstants.NO;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.acme.notary.test.TestConstants.YES;
import static com.ruletalk.test.ChoiceQuestionExpectation.choiceQuestionWithCode;
import static com.ruletalk.test.QuestionExpectation.questionWithCode;
import static com.ruletalk.test.TalkingBot.startTalk;

public class RealEstateCategoryTest {

    private final static String REAL_ESTATE_CATEGORY = "nieruch";
    private final static String DISPOSAL_TYPE_ANSWER = "nieruch.zbycie";

    @Test
    public void testTypeQuestionForRealEstateCategory() {
        startTalk()
                .chooseOption(CATEGORY, REAL_ESTATE_CATEGORY)
                .expect(
                        choiceQuestionWithCode(TYPE)
                                .withNOptions(17)
                                .havingOption("nieruch.przenies-wlasn-umowa-zobow", "umowa przenosząca własność lub użytkowanie wieczyste, " +
                                        "w wykonaniu umowy zobowiązującej")
                                .havingOption("nieruch.sprzedaz-zwrsp", "umowa sprzedaży nieruchomości rolnej " +
                                        "z Zasobu Własności Rolnej Skarbu Państwa")
                                .havingOption("nieruch.sprzedaz-mieszk-od-panstwa", "umowa sprzedaży lokalu mieszkalnego " +
                                        "lub nieruchomości gruntowej zabudowanej jednorodzinnym budynkiem mieszkalnym, dokonywanej przez Skarb Państwa, " +
                                        "jednostki samorządu terytorialnego lub inne podmioty na podstawie ustaw przyznających nabywcom tych lokali " +
                                        "lub nieruchomości bonifikaty od ceny")
                                .havingOption("nieruch.zbycie", "umowa zbycia")
                                .havingOption("nieruch.przenies-wlasn-umowa-o-budowe", "umowa ustanowienia odrębnej własności lokalu " +
                                        "lub przeniesienia własności domu jednorodzinnego z prawem do gruntu, zawartych w wykonaniu umów o budowę lokalu " +
                                        "lub domu, w trybie ustawy z dnia 15 grudnia 2000 r. o spółdzielniach mieszkaniowych")
                                .havingOption("nieruch.przenies-wlasn-przez-spoldz", "umowa przeniesienia własności lokalu" +
                                        " lub domu jednorodzinnego z prawem do gruntu, przez spółdzielnię mieszkaniową")
                                .havingOption("nieruch.przenies-wlasn-dzialki-bud-na-spoldz", "umowa przeniesienia własności, " +
                                        "oddania w użytkowanie wieczyste lub przeniesienia prawa użytkowania wieczystego działki budowlanej na " +
                                        "rzecz spółdzielni mieszkaniowej w trybie art. 35 ustawy z dnia 15 grudnia 2000 r. o spółdzielniach mieszkaniowych,")
                                .havingOption("nieruch.hipoteka", "ustanowienie hipoteki")
                                .havingOption("nieruch.umowa-przep-9", "umowa zawierana na podstawie przepisu art. 9 ustawy " +
                                        "z dnia 24 czerwca 1994 r. o własności lokali (Dz. U. z 2000 r. Nr 80, poz. 903 oraz z 2004 r. Nr 141, poz. 1492)")
                                .havingOption("nieruch.darowizna-lokalu-rodzina", "umowa darowizny lokalu stanowiącego odrębną " +
                                        "nieruchomość, jeżeli umowa jest zawierana pomiędzy osobami zaliczanymi do I grupy podatkowej " +
                                        "w rozumieniu przepisów ustawy z dnia 28 lipca 1983 r. o podatku od spadków i darowizn, " +
                                        "a nabywcy są uprawnieni do skorzystania z ulgi, o której mowa w art. 16 ustawy " +
                                        "z dnia 28 lipca 1983 r. o podatku od spadku i darowizn")
                                .havingOption("nieruch.umowa-deweloperska", "umowa deweloperska")
                                .havingOption("nieruch.odrb-wlasn-i-przenies-lokalu", "umowa ustanowienia odrębnej " +
                                        "własności lokalu mieszkalnego i przeniesienia własności tego lokalu na nabywcę, " +
                                        "w tym w wykonaniu umowy deweloperskiej")
                                .havingOption("nieruch.sprzedaz-dzialki-bud", "umowa sprzedaży nieruchomości gruntowej, " +
                                        "stanowiącej działkę budowlaną w rozumieniu art. 2 pkt 12 ustawy z dnia 27 marca 2003 r. " +
                                        "o planowaniu i zagospodarowaniu przestrzennym (Dz. U. z 2012 r. poz. 647, z późn. zm.9)")
                                .havingOption("nieruch.przenies-wlasn-dzialki-z-domem", "umowa przeniesienia na nabywcę " +
                                        "własności nieruchomości zabudowanej domem jednorodzinnym lub prawa użytkowania wieczystego " +
                                        "nieruchomości gruntowej i własności domu jednorodzinnego na niej posadowionego, " +
                                        "w tym w wykonaniu umowy deweloperskiej")
                                .havingOption("nieruch.sprzedaz-lokalu-mieszk", "umowa sprzedaży lokalu mieszkalnego")
                                .havingOption("nieruch.zwolnienie-od-obciazen", "zwolnienie nieruchomości od obciążeń " +
                                        "lub zrzeczenia się prawa, jeżeli wartości przedmiotu nie da się określić")
                                .havingOption("nieruch.zrzeczenie-wlasnosci-lub-prawa-uzytk", "zrzeczenie się własności " +
                                        "nieruchomości lub prawa użytkowania wieczystego")
                );
    }

    @Test
    public void expectProperRateFactor() {
        final Expectation halfFeeFactor = feeMaxRateFactor(FACTOR_HALF);
        chooseType("nieruch.przenies-wlasn-umowa-zobow").expect(halfFeeFactor);
        chooseType("nieruch.sprzedaz-zwrsp").expect(halfFeeFactor);
        chooseType("nieruch.sprzedaz-mieszk-od-panstwa").expect(halfFeeFactor);
        chooseType("nieruch.przenies-wlasn-umowa-o-budowe").expect(halfFeeFactor);
        chooseType("nieruch.przenies-wlasn-przez-spoldz").expect(halfFeeFactor);
        chooseType("nieruch.przenies-wlasn-dzialki-bud-na-spoldz").expect(halfFeeFactor);
        chooseType("nieruch.umowa-przep-9").expect(halfFeeFactor);
        chooseType("nieruch.darowizna-lokalu-rodzina").expect(halfFeeFactor);
        chooseType("nieruch.umowa-deweloperska").expect(halfFeeFactor);
        chooseType("nieruch.odrb-wlasn-i-przenies-lokalu").expect(halfFeeFactor);
        chooseType("nieruch.sprzedaz-dzialki-bud").expect(halfFeeFactor);
        chooseType("nieruch.przenies-wlasn-dzialki-z-domem").expect(halfFeeFactor);
        chooseType("nieruch.sprzedaz-lokalu-mieszk").expect(halfFeeFactor);
    }

    @Test
    public void expectProperConstantFeeMaxRate() {
        chooseType("nieruch.zwolnienie-od-obciazen")
                .expect(fee(60d), noValueQuestion(), feeMessage(60d));
        chooseType("nieruch.zrzeczenie-wlasnosci-lub-prawa-uzytk")
                .expect(fee(80d), noValueQuestion(), feeMessage(80d));
    }

    @Test
    public void expectQuestionAboutDisposalDetailedType() {
        chooseType(DISPOSAL_TYPE_ANSWER).then().expect(
                choiceQuestionWithCode(DISPOSAL_DETAILED_TYPE)
                        .withNOptions(5)
                        .havingOption("nieruch.zbycie.spoldz-wlasn-prawa.lokal", "Spółdzielczego własnościowego prawa do lokalu")
                        .havingOption("nieruch.zbycie.spoldz-wlasn-prawa.rozne", "Własnościowego spółdzielczego prawa do lokalu " +
                                "mieszkalnego, spółdzielczego prawa do lokalu użytkowego, prawa do domu jednorodzinnego w spółdzielni mieszkaniowej " +
                                "(prawa do lokalu mieszkalnego w domu budowanym przez spółdzielnię mieszkaniową " +
                                "w celu przeniesienia jego własności na członka)")
                        .havingOption("nieruch.zbycie.ekspektatywa", "Ekspektatywy spółdzielczego własnościowego prawa do lokalu, " +
                                "odrębnej własności lokalu lub domu jednorodzinnego w trybie ustawy " +
                                "z dnia 15 grudnia 2000 r. o spółdzielniach mieszkaniowych (Dz. U. z 2003 r. Nr 119, poz. 1116, z późn. zm.5))")
                        .havingOption("nieruch.zbycie.swiadczenia-sgefoigr", "Nieruchomości, jeżeli w związku z tą umową " +
                                "następuje wypłata świadczeń z tytułu ubezpieczenia społecznego rolników albo renty strukturalnej " +
                                "współfinansowanej ze środków pochodzących z Sekcji Gwarancji Europejskiego Funduszu Orientacji i Gwarancji Rolnej")
                        .havingOption("nieruch.zbycie.inne", "Czegoś innego")
        );
    }

    @Test
    public void expectHalfRateFactorForDisposalActsWithTheseDetailedTypes() {
        final Expectation halfFeeFactor = feeMaxRateFactor(FACTOR_HALF);
        chooseType("nieruch.zbycie").then()
                .chooseOption(DISPOSAL_DETAILED_TYPE, "nieruch.zbycie.spoldz-wlasn-prawa.lokal")
                .expect(halfFeeFactor, valueQuestion());
        chooseType("nieruch.zbycie").then()
                .chooseOption(DISPOSAL_DETAILED_TYPE, "nieruch.zbycie.spoldz-wlasn-prawa.rozne")
                .expect(halfFeeFactor, valueQuestion());
        chooseType("nieruch.zbycie").then()
                .chooseOption(DISPOSAL_DETAILED_TYPE, "nieruch.zbycie.ekspektatywa")
                .expect(halfFeeFactor, valueQuestion());
    }

    @Test
    public void expectConstantFeeForDisposalActsWithThisDefailedType() {
        chooseType("nieruch.zbycie").then()
                .chooseOption(DISPOSAL_DETAILED_TYPE, "nieruch.zbycie.swiadczenia-sgefoigr")
                .expect(fee(600d), noValueQuestion(), feeMessage(600d));
    }

    @Test
    public void expectQuestionAboutMortgagePurpose() {
        chooseType("nieruch.hipoteka").then().expect(
                questionWithCode(HOUSING_MORTGAGE).ofType(ChoiceQuestion.class)
        );
    }

    @Test
    public void testRateFactorForMortgage() {
        chooseType("nieruch.hipoteka").then()
                .chooseOption(HOUSING_MORTGAGE, YES)
                .expect(
                        feeMaxRateFactor(FACTOR_QUARTER)
                );

        chooseType("nieruch.hipoteka").then()
                .chooseOption(HOUSING_MORTGAGE, NO)
                .expect(
                        feeMaxRateFactor(FACTOR_HALF)
                );
    }

    private TalkingBot chooseType(final String chosenType) {
        return startTalk()
                .chooseOption(CATEGORY, REAL_ESTATE_CATEGORY)
                .chooseOption(TYPE, chosenType);
    }


}
