package email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

/* Fetching Email Response */

public class FetchMail {

	// Method that can be called from other class
	public static String fetchEmail(String timestamp) {

		// Parameters for email fetch, in this case a gmail account is used
		String host = "imap.gmail.com";
		String mailStoreType = "imap";
		String username = "client.dsgvo.testuser1@gmail.com";
		// Has to be catched out of database
		String password = "";//Please Insert Password for Mail Account here

		return fetch(host, mailStoreType, username, password, timestamp);

	}

	public static String fetch(String imapHost, String storeType, String user, String password, String timestamp) {
		try {
			// Properties of Email Server
			Properties properties = new Properties();
			properties.put("mail.store.protocol", storeType);
			properties.put("mail.imap.host", imapHost);
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the IMAP store object and connect with the server
			Store store = emailSession.getStore("imaps");
			store.connect(imapHost, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);

			// the imap protocol only allows to fetch read emails
			// all emails will be set to read
			Message[] messages = emailFolder.getMessages();
			for (int j = 0; j < messages.length; j++) {
				messages[j].setFlag(Flags.Flag.SEEN, true);
			}

			boolean emailFound = false;
			int tries = 0;
			int i = 0;
			String result = "";

			// Start of searching the email inbox
			while (!emailFound && tries <= 3 && messages.length > 0) {
				if (i >= messages.length) {
					tries++;
					// After 3 times searching through the whole inbox, search will be interrupted
					if (tries >= 3) {
						return "";
					}
					i = 0;

					// Catching the inbox again, in case the search started before email arrived
					emailFolder = store.getFolder("INBOX");
					emailFolder.open(Folder.READ_WRITE);
					messages = emailFolder.getMessages();
					for (int j = 0; j < messages.length; j++) {
						messages[j].setFlag(Flags.Flag.SEEN, true);
					}

				}

				Message message = messages[i];
				String subject = message.getSubject();

				// Split subject to get the timestamp
				String[] parts = subject.split("\\s+");
				String curr_timestamp = parts[parts.length - 1];

				// Is timestamp the same -> Email found
				if (curr_timestamp.equals(timestamp)) {
					result = fetchContent(message);
					if (result.equals("Unknown Email Type")) {
						result = "";
					}
					// Delete message after successful read
					messages[i].setFlag(Flags.Flag.DELETED, true);
					emailFound = true;
				} else {
					i++;
				}
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

			return result;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return "NO SUCH PROVIDER";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "MESSAGE ERROR";
		} catch (IOException e) {
			e.printStackTrace();
			return "IO ERROR";
		} catch (Exception e) {
			e.printStackTrace();
			return "OTHER ERROR";
		}
	}

	// Fetch content of the message
	public static String fetchContent(Part p) throws Exception {
		// check if the content is plain text
		if (p.isMimeType("text/plain")) {
			return ((String) p.getContent());
		} else {
			return "Unknown Email Type";
		}
	}

}