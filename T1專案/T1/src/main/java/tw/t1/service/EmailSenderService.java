package tw.t1.service;

import javax.mail.MessagingException;

public interface EmailSenderService {

	public void sendSimpleEmail(String toEmail, String subject, String body);

	public void sendHtmlEmail(String toEmail, String subject, String body) throws MessagingException;

	public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachment)
			throws MessagingException;

	public void sendEmailWithInlineResource(String toEmail, String subject, String body, String attachment)
			throws MessagingException;
}
