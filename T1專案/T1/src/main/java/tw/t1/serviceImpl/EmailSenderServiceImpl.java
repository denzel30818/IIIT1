package tw.t1.serviceImpl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tw.t1.service.EmailSenderService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	// 發送普通郵件
	public void sendSimpleEmail(String toEmail, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("ycivy0424@gmail.com"); // 寄件人Email
		message.setTo(toEmail); // 收件人Email
		message.setSubject(subject); // 郵件標題
		message.setText(body); // 郵件內容

		mailSender.send(message);
		System.out.println("Mail Send...");
	}

	// 發送HTML郵件
	public void sendHtmlEmail(String toEmail, String subject, String body) throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom("ycivy0424@gmail.com"); // 寄件人Email
		mimeMessageHelper.setTo(toEmail); // 收件人Email
		mimeMessageHelper.setSubject(subject); // 郵件標題
		mimeMessageHelper.setText(body, true); // 郵件內容

		mailSender.send(mimeMessage);
		System.out.println("Mail Send...");
	}

	// 發送附檔郵件
	public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachment)
			throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom("ycivy0424@gmail.com"); // 寄件人Email
		mimeMessageHelper.setTo(toEmail); // 收件人Email
		mimeMessageHelper.setSubject(subject); // 郵件標題
		mimeMessageHelper.setText(body, true); // 郵件內容

		FileSystemResource fileSystem = new FileSystemResource(new File(attachment));
		mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem); // 附加檔案

		mailSender.send(mimeMessage);
		System.out.println("Mail Send...");

	}

	// 發送嵌入靜態資源郵件
	public void sendEmailWithInlineResource(String toEmail, String subject, String body, String attachment)
			throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom("ycivy0424@gmail.com"); // 寄件人Email
		mimeMessageHelper.setTo(toEmail); // 收件人Email
		mimeMessageHelper.setSubject(subject); // 郵件標題
		mimeMessageHelper.setText(body, true); // 郵件內容

		FileSystemResource fileSystem = new FileSystemResource(new File(attachment));
		mimeMessageHelper.addInline(fileSystem.getFilename(), fileSystem); // 嵌入檔案

		mailSender.send(mimeMessage);
		System.out.println("Mail Send...");
	}

}
