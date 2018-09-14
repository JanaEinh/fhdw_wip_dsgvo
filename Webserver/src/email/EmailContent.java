package email;

import org.json.JSONException;
import org.json.JSONObject;

/* Elements of Emails that are handed over by the Dsgvo Application */

public class EmailContent {

	private String subject = "";
	private String content = "";
	private String timestamp = ""; // timestamp to identify response from Server
	private String email = ""; // email address of customer
	private String attachmentPath = ""; // PDF to Customer is sent as attachment

	public EmailContent(JSONObject object) throws JSONException {

		subject = object.get("subject").toString();
		content = object.get("content").toString();
		timestamp = object.get("timestamp").toString();
		email = object.getString("email").toString();
		attachmentPath = object.getString("attachmentPath").toString();

		// Parse Subject
		if (subject.isEmpty()) {
			subject = "Kein Betreff angegeben";
		}

		// Parse Message Content
		if (content.isEmpty()) {
			content = "Keine Nachricht angegeben";
		}

		// Parse Timestamp Content
		if (timestamp.isEmpty()) {
			timestamp = "";
		}

		// Parse Email Address
		if (email.isEmpty()) {
			email = "";
		}

		// Parse attachment path
		if (attachmentPath.isEmpty()) {
			attachmentPath = "";
		}

	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

}