package au.com.generic.email.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MimeMessagePreparatorImpl implements MimeMessagePreparator {

	private String to;
	private String subject;
	private String body;
	private String from;

	public MimeMessagePreparatorImpl(String to, String subject, String body, String from) {
		this.body = body;
		this.to = to;
		this.subject = subject;
		this.from = from;
	}

	/*
	 * Method called during Spring JavaMailSender.send method
	 * 
	 * @param mimeMessage
	 * 
	 * @see org.springframework.mail.javamail.MimeMessagePreparator#prepare(javax.mail.internet.MimeMessage)
	 */
	public void prepare(MimeMessage mimeMessage) throws Exception {

		// Setting few parameters for email
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		message.setTo(to);
		message.setFrom(from);
		message.setSubject(subject);
		message.setText(body, true);
	}

}
