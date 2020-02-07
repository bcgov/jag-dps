package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationConsumerTest {

    private OutputNotificationConsumer sut;

    @BeforeAll
    public void setUp() {
        sut = new OutputNotificationConsumer();
    }

    @Test
    public void withAMessageShouldProcess() {

        Assertions.assertDoesNotThrow(() -> {
            sut.receiveMessage(new OutputNotificationMessage(Keys.CRRP_QUEUE_NAME, "fileid"));
        });

    }


}
