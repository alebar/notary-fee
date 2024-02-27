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

public class TestamentCategoryTest {

    private final static String TESTAMENT_CATEGORY = "testament";

    @Test
    public void testTypeQuestionForTestamentCategory() {
        startTalk()
                .chooseOption(CATEGORY, TESTAMENT_CATEGORY)
                .expect(
                        choiceQuestionWithCode(TYPE)
                                .withNOptions(10)
                                .havingOption("testament.testament", "testament")
                                .havingOption("testament.testament-zapis-zwykly", "testament zawierający zapis zwykły, " +
                                        "polecenie lub pozbawienie uprawnionego prawa do zachowku")
                                .havingOption("testament.testament-zapis-wind", "testament zawierający zapis windykacyjny")
                                .havingOption("testament.odwolanie", "odwołanie testamentu")
                                .havingOption("testament.oswiadczenie-o-spadku", "oświadczenie o przyjęciu lub odrzuceniu spadku")
                                .havingOption("testament.poswiadczenie-dziedziczenia-gosp-rolne", "sporządzenie aktu " +
                                        "poświadczenia dziedziczenia ustawowego lub testamentowego, " +
                                        "uzupełniającego aktu poświadczenia dziedziczenia " +
                                        "w zakresie spadkobierców dziedziczących gospodarstwo rolne")
                                .havingOption("testament.poswiadczenie-dziedziczenia-zapis-wind", "sporządzenie aktu " +
                                        "poświadczenia dziedziczenia testamentowego z zapisem windykacyjnym")
                                .havingOption("testament.protokol-dziedziczenia", "sporządzenie protokołu dziedziczenia")
                                .havingOption("testament.protokol-otwarcia-i-ogloszenia", "sporządzenie protokołu " +
                                        "otwarcia i ogłoszenia testamentu")
                                .havingOption("testament.zaswiadczenie-powolanie-wykonawcy", "sporządzenie zaświadczenia " +
                                        "o powołaniu wykonawcy testamentu")
                );
    }

    @Test
    public void expectProperConstantFeeMaxRate() {
        chooseType("testament.testament").expect(fee(50d), noValueQuestion(), feeMessage(50d));
        chooseType("testament.testament-zapis-zwykly").expect(fee(150d), noValueQuestion(), feeMessage(150d));
        chooseType("testament.testament-zapis-wind").expect(fee(200d), noValueQuestion(), feeMessage(200d));
        chooseType("testament.odwolanie").expect(fee(30d), noValueQuestion(), feeMessage(30d));
        chooseType("testament.oswiadczenie-o-spadku").expect(fee(50d), noValueQuestion(), feeMessage(50d));
        chooseType("testament.poswiadczenie-dziedziczenia-gosp-rolne").expect(fee(50d), noValueQuestion(), feeMessage(50d));
        chooseType("testament.poswiadczenie-dziedziczenia-zapis-wind").expect(fee(100d), noValueQuestion(), feeMessage(100d));
        chooseType("testament.protokol-dziedziczenia").expect(fee(100d), noValueQuestion(), feeMessage(100d));
        chooseType("testament.protokol-otwarcia-i-ogloszenia").expect(fee(50d), noValueQuestion(), feeMessage(50d));
        chooseType("testament.zaswiadczenie-powolanie-wykonawcy").expect(fee(30d), noValueQuestion(), feeMessage(30d));
    }

    private TalkingBot chooseType(final String chosenType) {
        return startTalk()
                .chooseOption(CATEGORY, TESTAMENT_CATEGORY)
                .chooseOption(TYPE, chosenType);
    }

}
