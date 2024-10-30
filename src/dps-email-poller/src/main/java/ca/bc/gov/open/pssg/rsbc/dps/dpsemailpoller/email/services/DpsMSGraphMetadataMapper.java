package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import com.microsoft.graph.models.Message;

public interface DpsMSGraphMetadataMapper {
    DpsMetadata map(Message emailMessage, DpsFileInfo dpsFileInfo, String tenant);
}
