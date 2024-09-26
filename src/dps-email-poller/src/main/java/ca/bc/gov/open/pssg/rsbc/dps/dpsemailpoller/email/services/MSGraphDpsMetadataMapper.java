package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import com.microsoft.graph.models.Message;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;

public interface MSGraphDpsMetadataMapper {
    DpsMetadata map(Message emailMessage, DpsFileInfo dpsFileInfo, String tenant);
}
