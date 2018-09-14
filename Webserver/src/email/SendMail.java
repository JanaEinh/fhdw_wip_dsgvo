package email;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/* Class to send an email to server or customer */

public class SendMail {

	// Method can be called from other class
	// returns true if sending was successful, otherwise false
	public static boolean sendMail(String subject, String content, String timestamp, String email,
			String attachmentPath) {

		final String username = "client.dsgvo.testuser1@gmail.com";
		// Has to catched from database
		final String password = ""; //please insert

		// Email properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Authentification
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// Prepare email parameters
		try {
			Message message = new MimeMessage(session);

			// Set sender
			if (email.equals("")) {
				// Message to server
				message.setFrom(new InternetAddress(username));
			} else {
				// Set alias if message goes to customer
				message.setFrom(new InternetAddress(username, "Malermeister Mustermann"));
			}

			// Set recipient
			if (email.equals("")) {
				// Email to server
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("server.dsgvo.testuser1@gmail.com"));
			} else {
				// Email to customer
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			}

			// Set subject
			message.setSubject(subject + timestamp);

			if (!attachmentPath.equals("")) {
				// if String attachment path is not empty, the pdf has to be attached
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(content);

				// Create a multipart message
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				// set attachment
				messageBodyPart = new MimeBodyPart();
				File f = new File(attachmentPath);
				DataSource source = new FileDataSource(f);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(f.getName());
				multipart.addBodyPart(messageBodyPart);

				// set content
				message.setContent(multipart);
			} else {
				// Email goes to Server, no attachment
				message.setText(content);
			}

			// Send message
			Transport.send(message);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
