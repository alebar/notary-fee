package com.acme.notary;

import org.junit.Test;

import static com.ruletalk.test.ConclusionExpectation.conclusionOfType;
import static com.ruletalk.test.TalkingBot.startTalk;
import static org.assertj.core.api.Assertions.assertThat;

public class InitTest {

    @Test
    public void initShouldInsertListOfConstantTypes() {
        startTalk().then()
                .expect(
                        conclusionOfType(NotarialActTypesNotRelatedToValue.class)
                                .matchingCondition(list ->
                                    assertThat(list.getTypes()).isNotEmpty()
                                )
                );
    }
}
