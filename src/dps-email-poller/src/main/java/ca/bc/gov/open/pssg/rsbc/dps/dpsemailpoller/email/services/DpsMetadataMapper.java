package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;

public interface DpsMetadataMapper {
    DpsMetadata map(EmailMessage emailMessage, DpsFileInfo dpsFileInfo, String tenant);
}
