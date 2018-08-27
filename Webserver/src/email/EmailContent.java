package email;

import org.json.JSONException;
import org.json.JSONObject;

public class EmailContent {

	private String subject = "";
	private String content = "";

	public EmailContent(JSONObject object) throws JSONException {

		subject = object.get("subject").toString();
		content = object.get("content").toString();

		// Parse Subject
		if (subject.isEmpty()) {
			subject = "Kein Betreff angegeben";
		}

		// Parse Message Content
		if (content.isEmpty()) {
			content = "Keine Nachricht angegeben.";
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

}