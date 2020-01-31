package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationEndpointTest {


    private OutputNotificationEndpoint sut;

    @BeforeAll
    public void setUp() {
        sut = new OutputNotificationEndpoint();
    }

    @Test
    public void withMessageAcceptedShouldReturnSucces() {

        OutputNotificationResponse response = sut.outputNotificationNotification(new OutputNotificationRequest());

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE, response.getOutputNotificationResponse().getRespMsg());

    }

}
