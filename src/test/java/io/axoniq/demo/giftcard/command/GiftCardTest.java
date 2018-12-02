package io.axoniq.demo.giftcard.command;

import io.axoniq.demo.giftcard.api.IssueCmd;
import io.axoniq.demo.giftcard.api.IssuedEvt;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GiftCardTest {

    private FixtureConfiguration<GiftCard> fixture;

    @BeforeEach
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    public void shouldCreate() {
        String id = UUID.randomUUID().toString();
        int amount = 20;
        IssueCmd issueCmd = new IssueCmd(id, amount);
        IssuedEvt issuedEvt = new IssuedEvt(id, amount);
        fixture.givenNoPriorActivity().when(issueCmd)
                .expectEvents(issuedEvt);


    }

}