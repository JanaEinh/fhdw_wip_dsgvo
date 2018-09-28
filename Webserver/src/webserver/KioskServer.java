package webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import email.EmailContent;
import email.FetchMail;
import email.SendMail;
import mail_converter.StringConverter;
import pdf_generator.PDFCreator;
import mail_converter.CustomerRelatedData;
import database.QueryApp;

/* Main Class */
/* Webserver for hosting Dsgvo Application and doing the email communication */

public class KioskServer extends NanoHTTPD {

	public String[] allowedIPS = { "127.0.0.1" };
	private static final String WEB_APP_PATH = "C:\\Users\\niklas.frank\\Documents\\Theoriephase\\4. Semester\\WIP Projekt\\GitHub Projekt\\fhdw_wip_dsgvo\\Dsgvo\\src";
	// For Response Email
	private CustomerRelatedData emailContentCRD = null;

	// starts the server
	public KioskServer(int port) {
		super(port);
		try {
			start(5000000, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// processes the requests to the server
	@Override
	public Response serve(IHTTPSession session) {
		String[] splits = session.getUri().split("/", 0);

		if (splits.length > 0) {
			String methodID = splits[1];
			if (authorize(getRemoteIp(session.getHeaders()))) {
				if (methodID.equalsIgnoreCase("sendmail")) {
					// Send email
					return newFixedLengthResponse(sendEmail(session));
				} else if (methodID.equalsIgnoreCase("fetchmail")) {
					// Fetch email
					return newFixedLengthResponse(fetchEmail(session));
				} else if (methodID.equalsIgnoreCase("checklogin")) {
					// Check if login data are correct
					return newFixedLengthResponse(checkLogin(session));
				} else if (methodID.equalsIgnoreCase("status")) {
					// Status of server for testing
					return newFixedLengthResponse(
							"OK <br>Remote-IP: " + getRemoteIp(session.getHeaders()) + "<br>AccessGranted: ");
				} else {
					// show Dsgvo Application content
					return parseWebAppContent(splits, methodID);
				}
			} else {
				return newFixedLengthResponse("IP NOT ALLOWED");
			}
		} else {
			return newFixedLengthResponse("UNKNOWN COMMAND");
		}
	}

	private String checkLogin(IHTTPSession session) {
		String result = "";
		try {
			if (session.getMethod() == Method.POST) {
				Map<String, String> files = new HashMap<String, String>();
				session.parseBody(files);

				// Get HTTP Post parameters
				String postBody = session.getQueryParameterString();

				if (postBody == null) {
					postBody = files.get("postData");
					postBody = java.net.URLDecoder.decode(postBody, "UTF-8");
				}

				if (postBody != null && postBody.startsWith("{")) {
					JSONObject obj = new JSONObject(postBody);
					// get username and password out of HTTP Post data
					String email = obj.get("email").toString();
					String password = obj.getString("password").toString();

					// Check if login data are correct
					QueryApp qa = new QueryApp();
					String pw_db = qa.getUserPassword(email);
					if (pw_db.equals(password)) {
						// if the two passwords equal, login data are correct
						result = "OK";
					} else {
						result = "NOT OK";
					}
				} else {
					return "No Data input";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// Fetch Email from Inbox
	private String fetchEmail(IHTTPSession session) {
		String result = "";
		try {
			if (session.getMethod() == Method.POST) {
				Map<String, String> files = new HashMap<String, String>();
				session.parseBody(files);

				// Get HTTP Post parameters
				String postBody = session.getQueryParameterString();

				if (postBody == null) {
					postBody = files.get("postData");
					postBody = java.net.URLDecoder.decode(postBody, "UTF-8");
				}

				if (postBody != null && postBody.startsWith("{")) {
					JSONObject obj = new JSONObject(postBody);
					String timestamp = obj.get("timestamp").toString();

					// Fetch Email
					result = FetchMail.fetchEmail(timestamp);
				} else {
					return "NO HTTP POST DATA";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "FETCH MAIL ERROR";
		}

		// Customer Related Data is needed for PDF creation
		emailContentCRD = StringConverter.parseStringToCustomerData(result);
		// String is already formatted
		return StringConverter.parseStringFromMail(result);
	}

	// Send Email
	private String sendEmail(IHTTPSession session) {
		String result = "";
		try {
			if (session.getMethod() == Method.POST) {
				Map<String, String> files = new HashMap<String, String>();
				session.parseBody(files);

				// Get HTTP Post Parameters
				String postBody = session.getQueryParameterString();

				if (postBody == null) {
					postBody = files.get("postData");
					postBody = java.net.URLDecoder.decode(postBody, "UTF-8");
				}

				String email_customer = "";
				String attachmentPath = "";
				if (postBody != null && postBody.startsWith("{")) {
					JSONObject obj = new JSONObject(postBody);
					if (obj.has("email")) {
						// if email key is specified, mail should be sent to customer
						email_customer = obj.get("email").toString();
						String update = obj.getString("update").toString();
						// if no customer data are found, no email to customer is sent
						if (update.equals("YES")) {
							emailContentCRD.getCustomer().setEmail(email_customer);
						}
						if (emailContentCRD.getCustomer() != null) {
							attachmentPath = PDFCreator.generatePDFFromCustomerData(emailContentCRD);
							obj.put("email", email_customer);
							obj.put("attachmentPath", attachmentPath);
							EmailContent ec = new EmailContent(obj);
							SendMail.sendMail(ec.getSubject(), ec.getContent(), ec.getTimestamp(), email_customer,
									attachmentPath);
							result = "OK";
						} else {
							result = "NO MAIL TO CUSTOMER";
						}
					} else {
						// mail to server, no customer email address or attachment needed
						email_customer = "";
						obj.put("email", email_customer);
						attachmentPath = "";
						obj.put("attachmentPath", attachmentPath);
						EmailContent ec = new EmailContent(obj);
						SendMail.sendMail(ec.getSubject(), ec.getContent(), ec.getTimestamp(), email_customer,
								attachmentPath);
						result = "OK";
					}		
				} else {
					SendMail.sendMail("Testmail", "Testcontent", "Today", "", "");
					result = "TEST OK";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "SEND MAIL ERROR";
		}
		return result;
		
	}

	// Parses Dsgvo Web Application content
	private Response parseWebAppContent(String[] splits, String methodID) {
		FileInputStream fis = null;
		StringBuilder fileURL = new StringBuilder();
		for (int i = 1; i < splits.length; i++) {
			fileURL.append("\\");
			fileURL.append(splits[i]);
		}

		// standard main page is index.html
		if (methodID.equalsIgnoreCase("dsgvo")) {
			fileURL = new StringBuilder();
			fileURL.append("\\index.html");
		}
		try {
			if (!fileURL.toString().isEmpty()) {
				File f = new File(WEB_APP_PATH + fileURL.toString());
				if (f.exists()) {
					fis = new FileInputStream(f);
					return newFixedLengthResponse(Status.OK, getMimeType(f.getAbsolutePath()), fis, f.length());
				} else {
					return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "File not found");
				}
			} else {
				return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "File not found");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "File not loaded");
		}
	}

	// checks if allowed IP is used
	private boolean authorize(String ip) {
		if (allowedIPS.length == 0) {
			return true;
		} else {
			for (String tempIP : allowedIPS) {
				if (tempIP.equals(ip)) {
					return true;
				}
			}
		}
		return false;
	}

	// returns Mime Type of a file, used for parsing web app content
	private String getMimeType(String filename) {
		String ext = filename.substring(filename.length() - 3, filename.length());

		if (ext.equals("tml")) {
			return "text/html";
		} else if (ext.equals("jpg")) {
			return "image/jpeg";
		} else if (ext.equals("png")) {
			return "image/png";
		} else if (ext.equals("css")) {
			return "text/css";
		} else if (ext.equals(".js")) {
			return "text/javascript";
		} else {
			return "text/plain";
		}
	}

	// returns used ip-address
	private String getRemoteIp(Map<String, String> headers) {
		return headers.get("http-client-ip");
	}

}