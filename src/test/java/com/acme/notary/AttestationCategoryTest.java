package com.acme.notary;

import com.ruletalk.test.TalkingBot;

import org.junit.Test;

import static com.acme.notary.test.Expectations.fee;
import static com.acme.notary.test.Expectations.feeMessage;
import static com.acme.notary.test.Expectations.noValueQuestion;
import static com.acme.notary.test.TestConstants.ALIVE_ATTESTATION_PURPOSE;
import static com.acme.notary.test.TestConstants.CATEGORY;
import static com.acme.notary.test.TestConstants.SIGNATURE_ATTESTATION_DETAILED_TYPE;
import static com.acme.notary.test.TestConstants.TYPE;
import static com.ruletalk.test.ChoiceQuestionExpectation.choiceQuestionWithCode;
import static com.ruletalk.test.MessageExpectation.messageWithCode;
import static com.ruletalk.test.TalkingBot.startTalk;

public class AttestationCategoryTest {

    private final static String ATTESTATION_CATEGORY = "poswiadczenia";

    @Test
    public void testTypeQuestionForAttestationCategory() {
        startTalk()
                .chooseOption(CATEGORY, ATTESTATION_CATEGORY)
                .expect(
                        choiceQuestionWithCode(TYPE)
                                .withNOptions(5)
                                .havingOption("posw.podpisu", "poświadczenie własnoręczności podpisu " +
                                        "albo odcisku palca osoby niepiśmiennej lub niemogącej pisać")
                                .havingOption("posw.czas-okazania-dok", "poświadczenie czasu okazania dokumentu")
                                .havingOption("posw.zgodnosc-odpisu", "poświadczenie zgodności odpisu z okazanym dokumentem")
                                .havingOption("posw.pozostawanie-przy-zyciu", "poświadczenie pozostawania przy życiu")
                                .havingOption("posw.pozostawanie-w-miejscu", "poświadczenie pozostawania osoby w określonym miejscu")
                );
    }

    @Test
    public void expectProperConstantFeeMaxRate() {
        chooseType("posw.czas-okazania-dok").expect(
                fee(6d, "za każdą stronę"),
                noValueQuestion(),
                feeMessage(6d, "za każdą stronę")
        );
        chooseType("posw.zgodnosc-odpisu").expect(
                fee(6d, "za każdą stronę"), noValueQuestion()
        );
        chooseType("posw.pozostawanie-w-miejscu").expect(fee(30d), noValueQuestion());
    }

    @Test
    public void expectQuestionAboutSignedDocuments() {
        chooseType("posw.podpisu").then().expect(
                choiceQuestionWithCode(SIGNATURE_ATTESTATION_DETAILED_TYPE)
                        .withNOptions(2)
                        .havingOption("posw.podpisu.przedmiot-ozn-suma", "Na dokumentach, których przedmiot jest oznaczony sumą pieniężną")
                        .havingOption("posw.podpisu.pelnom-i-inne", "Na pełnomocnictwach i innych dokumentach")
        );
    }

    @Test
    public void expectMessageWithMaxRateCalculationInstructionWhenSignedDocumentHasValue() {
        chooseType("posw.podpisu").then()
                .chooseOption(SIGNATURE_ATTESTATION_DETAILED_TYPE, "posw.podpisu.przedmiot-ozn-suma")
                .expect(
                        messageWithCode("fee-max-rate-cannot-be-calculated")
                                .containing("1/10")
                                .containing("300"),
                        noValueQuestion()
                );
    }

    @Test
    public void expectConstantRateWhenSignedDocumentDoNotHaveValue() {
        chooseType("posw.podpisu").then()
                .chooseOption(SIGNATURE_ATTESTATION_DETAILED_TYPE, "posw.podpisu.pelnom-i-inne")
                .expect(
                       fee(20d),
                        noValueQuestion(),
                        feeMessage(20d)
                );
    }

    @Test
    public void expectQuestionAboutPurposeOfProofingSomeoneIsAlive() {
        chooseType("posw.pozostawanie-przy-zyciu").then().expect(
                choiceQuestionWithCode(ALIVE_ATTESTATION_PURPOSE)
                        .withNOptions(2)
                        .havingOption("posw.pozostawanie-przy-zyciu.zus", "W celu otrzymania emerytury, " +
                                "renty lub innych świadczeń z ubezpieczenia społecznego")
                        .havingOption("posw.pozostawanie-przy-zyciu.inne", "W innym celu")
        );
    }

    @Test
    public void expectProperConstantRatesForAliveAttestation() {
        chooseType("posw.pozostawanie-przy-zyciu")
                .chooseOption(ALIVE_ATTESTATION_PURPOSE, "posw.pozostawanie-przy-zyciu.zus")
                .expect(fee(5d), noValueQuestion(), feeMessage(5d));
        chooseType("posw.pozostawanie-przy-zyciu")
                .chooseOption(ALIVE_ATTESTATION_PURPOSE, "posw.pozostawanie-przy-zyciu.inne")
                .expect(fee(30d), noValueQuestion(), feeMessage(30d));
    }

    private TalkingBot chooseType(final String chosenType) {
        return startTalk()
                .chooseOption(CATEGORY, ATTESTATION_CATEGORY)
                .chooseOption(TYPE, chosenType);
    }
}
