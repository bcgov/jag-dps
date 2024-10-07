package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsMSGraphException;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.Message;

import java.util.List;

public interface MSGraphService {
	
	public List<Message> GetMessages(String filter) throws DpsMSGraphException;
	public List<Attachment> getAttachments(Message message) throws DpsMSGraphException;
	public byte[] getAttachmentContent(Attachment attachment) throws DpsMSGraphException;

	public void sendMessage(Message message, boolean saveToSentItems) throws DpsMSGraphException;
	public void deleteMessage(Message message) throws DpsMSGraphException;
	public String getPasswordCredentialsExpiryDate() throws DpsMSGraphException;

	public Message moveToFolder(String messageId, String folderName, boolean createIfNotExist) throws DpsMSGraphException;
}
