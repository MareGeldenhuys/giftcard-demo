package io.axoniq.demo.giftcard.command;

import io.axoniq.demo.giftcard.api.CancelCmd;
import io.axoniq.demo.giftcard.api.CancelEvt;
import io.axoniq.demo.giftcard.api.IssueCmd;
import io.axoniq.demo.giftcard.api.IssuedEvt;
import io.axoniq.demo.giftcard.api.RedeemCmd;
import io.axoniq.demo.giftcard.api.RedeemedEvt;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import java.lang.invoke.MethodHandles;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Profile("command")
public class GiftCard {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private String id;
    private int remainingValue;
    private String status;

    public GiftCard() {
        log.debug("empty constructor invoked");
    }

    @CommandHandler
    public GiftCard(IssueCmd cmd) {
        log.debug("handling {}", cmd);
        if(StringUtils.isEmpty(cmd.getId())) throw new IllegalArgumentException("Id is blank");
        if(cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
        apply(new IssuedEvt(cmd.getId(), cmd.getAmount()));
    }

    @CommandHandler
    public String handle(RedeemCmd cmd) {
        log.debug("handling {}", cmd);
        if(cmd.getAmount() <= 0) return "amount <= 0";
        if(cmd.getAmount() > remainingValue) return "amount > remaining value";
        apply(new RedeemedEvt(id, cmd.getAmount()));
        return "Success";
    }

    @CommandHandler
    public void handle(CancelCmd cmd) {
        log.debug("handling {}", cmd);
        apply(new CancelEvt(id));
    }

    @EventSourcingHandler
    public void on(IssuedEvt evt) {
        log.debug("event sourcing applying {}", evt);
        id = evt.getId();
        remainingValue = evt.getAmount();
        log.debug("new remaining value: {}", remainingValue);
    }

    @EventSourcingHandler
    public void on(RedeemedEvt evt) {
        log.debug("event sourcing applying {}", evt);
        remainingValue -= evt.getAmount();
        log.debug("new remaining value: {}", remainingValue);
    }

    @EventSourcingHandler
    public void on(CancelEvt evt) {
        log.debug("event sourcing applying {}", evt);
        remainingValue = 0;
        log.debug("new remaining value: {}", remainingValue);
    }
}
