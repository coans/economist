package com.economist.util;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class EmailUtil {
	
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	
	private static final Logger logger = Logger.getLogger(EmailUtil.class);

	@Autowired
	private VelocityEngine velocityEngine;
	
	@Value("${mail.smtp.host}")
	private String mailSmtpHost;
	
	@Value("${mail.smtp.port}")
	private String mailSmtpPort;
	
	@Value("${mail.smtp.auth}")
	private String mailSmtpAuth;
	
	@Value("${mail.smtp.starttls.enable}")
	private String mailSmtpStarttlsEnable;

	@Value("${mail.address}")
	private String mailAddress;
	
	@Value("${mail.personal}")
	private String mailPersonal;
	
	@Value("${mail.user}")
	private String mailUser;
	
	@Value("${mail.password}")
	private String mailPassword;
	
	
	@Async
	public void sendMail(String toEmail, String subject, String templateLocation, Map<String, Object> model) {
		Properties props = new Properties();
		props.setProperty(MAIL_SMTP_HOST, mailSmtpHost);
		props.setProperty(MAIL_SMTP_PORT, mailSmtpPort);
		props.put(MAIL_SMTP_AUTH, mailSmtpAuth);
		props.put(MAIL_SMTP_STARTTLS_ENABLE, mailSmtpStarttlsEnable);
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUser, mailPassword);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		try {
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(mailAddress, mailPersonal));
	        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toEmail));
	        msg.setSubject(subject);
	        String msgBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "utf-8", model);
	        msg.setContent(msgBody, "text/html; charset=utf-8");
	        Transport.send(msg);
		} catch (Exception e) {
			logger.error("Error sending email.", e);
		}
        logger.debug("Email sent successfully.");
	}
}
