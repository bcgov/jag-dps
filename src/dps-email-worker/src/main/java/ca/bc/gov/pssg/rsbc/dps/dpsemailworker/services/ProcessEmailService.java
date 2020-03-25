package ca.bc.gov.pssg.rsbc.dps.dpsemailworker.services;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;

public interface ProcessEmailService {
    String sendProccessedMessage(DpsMetadata dpsMetadata);
}
