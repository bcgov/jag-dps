/**
 * 
 */
package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.MSGraphException;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.Message;

import java.util.List;

/**
 * MS Graph Service Interface
 * 
 * @author 176899
 *
 */
public interface MSGraphService {
	
	public List<Message> GetMessages(String filter) throws MSGraphException;
	public List<Attachment> getAttachments(String id) throws MSGraphException;
	public byte[] getAttachmentContent(Attachment attachment) throws MSGraphException;

	public void sendMessage(Message message, boolean saveToSentItems) throws MSGraphException;
	public void deleteMessage(Message message) throws MSGraphException;
	public String getPasswordCredentialsExpiryDate() throws MSGraphException;

	public Message moveToFolder(String id, String folderName) throws MSGraphException;
}
