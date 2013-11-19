package au.com.generic.email.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import au.com.generic.email.Attachment;
import au.com.generic.email.EmailManager;



public class EmailManagerImpl implements EmailManager {

	private static final Log logger = LogFactory.getLog(EmailManagerImpl.class);

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public boolean sendMail(String to, String from, String replyTo, String subject, String body, String contentType,
			List<Attachment> attachments) {
	  logger.debug(String.format("sending message to(%s), from(%s), subject(%s)", to, from, subject));
		MimeMessage message = mailSender.createMimeMessage();

		OutputStream out = null;
		File tmpFile = null;

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setReplyTo(new InternetAddress(replyTo));
			helper.setText(body, true);
			helper.setSubject(subject);
			helper.setSentDate(new Date());

			for (Attachment attachment : attachments) {
				tmpFile = new File(attachment.getFilename());
				out = new FileOutputStream(tmpFile);
				byte[] buf = new byte[2048];
				int len;

				while ((len = attachment.getData().read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				helper.addAttachment(attachment.getFilename(), new FileSystemResource(tmpFile), attachment
						.getContentType());
			}

			this.mailSender.send(message);
		} catch (Exception ex) {
			logger.error("Exception throw while sending email", ex);
			ex.printStackTrace();
		
			return false;
		} finally {
			//always delete the tmp file
			if (tmpFile !=null){
				FileTools.delete(tmpFile);
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					logger.error("Exception closing output stream", ex);
					return false;
				}
			}
		}

		return true;
	}

	public boolean sendMail(String to, String from, String replyTo, String subject, String body, String contentType,
			boolean isHtml) {
    logger.debug(String.format("sending message to(%s), from(%s), subject(%s)", to, from, subject));
		MimeMessage message = mailSender.createMimeMessage();

		if (to.split(",").length > 1) {
			return sendMail(to.split(","), subject, body, from, contentType, isHtml);
		}

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, isHtml);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setReplyTo(new InternetAddress(replyTo));
			helper.setText(body, isHtml);
			helper.setSubject(subject);
			helper.setSentDate(new Date());

			this.mailSender.send(message);
		} catch (Exception ex) {
			logger.error("Exception throw while sending email", ex);
			return false;
		}

		return true;
	}

	public boolean sendMail(String to, String subject, String body, String from, String contentType, boolean isHtml) {
    logger.debug(String.format("sending message to(%s), from(%s), subject(%s)", to, from, subject));

		if (to.split(",").length > 1) {
			return sendMail(to.split(","), subject, body, from, contentType, isHtml);
		}

		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, isHtml, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(body, isHtml);
			helper.setSubject(subject);
			helper.setSentDate(new Date());

			this.mailSender.send(message);
		} catch (Exception ex) {
			logger.error("Exception throw while sending email", ex);
			return false;
		}

		return true;
	}

	public boolean sendMail(String[] to, String subject, String body, String from, String contentType, boolean isHtml) {
    logger.debug(String.format("sending message to(%s), from(%s), subject(%s)", to, from, subject));
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, isHtml, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(body, isHtml);
			helper.setSubject(subject);
			helper.setSentDate(new Date());

			this.mailSender.send(message);
		} catch (Exception ex) {
			logger.error("Exception throw while sending email", ex);
			return false;
		}

		return true;
	}

	public boolean sendMail(String[] tos, String from, String replyTo, String subject, String body, String contentType,
			boolean isHtml) {
    logger.debug(String.format("sending message to(%s), from(%s), subject(%s)", tos, from, subject));
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(tos);
			helper.setText(body, true);
			helper.setSubject(subject);
			helper.setSentDate(new Date());

			this.mailSender.send(message);
		} catch (Exception ex) {
			logger.error("Exception throw while sending email", ex);
			return false;
		}

		return true;
	}

}
