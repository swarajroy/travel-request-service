package org.agiledev.learnbydoing.travelrequestservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by swarajroy on 15/08/2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApprovalMessagesListenerTest {

    @Autowired
    private ApprovalMessagesListener listener;

    @Autowired
    private Sink sink;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onApprovalMessageArrival() throws Exception {

        final String approvalStatus = "APPROVED";

        SubscribableChannel input = sink.input();
        input.send(MessageBuilder.withPayload(new ApprovalMessage("APPROVED")).build());

        BDDAssertions.then(this.listener.approvalStatus).isEqualTo(approvalStatus);
    }

}