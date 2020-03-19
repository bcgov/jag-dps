package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.health;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LivenessTest {

    private Liveness sut;

    @BeforeAll
    public void setUp() {
        sut = new Liveness();
    }

    @Test
    public void itShouldReturnUp() {
        String result = sut.testLiveness();
        Assertions.assertEquals("{\"status\":\"UP\"}", result);
    }

}
