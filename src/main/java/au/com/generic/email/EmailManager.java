package au.com.generic.email;

import java.util.List;

/**
 * Abstraction for sending email messages. Behind the scenes this manager may use FatWire mail API's
 */
public interface EmailManager {

	/**
	 * Primary method for sending email in various forms and optionally with attachments
	 */
	public boolean sendMail(String to, String from, String replyTo, String subject, String body, String contentType,
			List<Attachment> attachments);

	/**
	 * 
	 */
	public boolean sendMail(String[] to, String subject, String body, String from, String contentType, boolean isHtml);

	public boolean sendMail(String to, String subject, String body, String from, String contentType, boolean isHtml);

	public boolean sendMail(String to, String from, String replyTo, String subject, String body, String contentType,
			boolean isHtml);

	public boolean sendMail(String[] tos, String from, String replyTo, String subject, String body, String contentType,
			boolean isHtml);


}
