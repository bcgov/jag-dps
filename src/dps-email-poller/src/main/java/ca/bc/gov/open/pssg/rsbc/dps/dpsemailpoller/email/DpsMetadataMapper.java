package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;

public interface DpsMetadataMapper {
    DpsMetadata map(EmailMessage emailMessage, String tenant);
}
