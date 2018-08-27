package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import email.EmailContent;
import email.SendMail;

public class KioskServer extends NanoHTTPD {

	public String[] allowedIPS = { "127.0.0.1" };

	public KioskServer(int port) {
		super(port);
		try {
			start(100000, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response serve(IHTTPSession session) {
		String[] splits = session.getUri().split("/", 0);

		if (splits.length > 0) {
			String methodID = splits[1];
			if (authorize(getRemoteIp(session.getHeaders()))) {
				if (methodID.equalsIgnoreCase("sendmail")) {
					return newFixedLengthResponse(sendEmail(session));
				} else if (methodID.equalsIgnoreCase("status")) {
					return newFixedLengthResponse(
							"OK <br>Remote-IP: " + getRemoteIp(session.getHeaders()) + "<br>AccessGranted: ");
				} else {
					return parseWebAppContent(splits, methodID);
				}
			} else {
				return newFixedLengthResponse("IP NOT ALLOWED");
			}
		} else {
			return newFixedLengthResponse("PING FAILED");
		}
	}

	private String sendEmail(IHTTPSession session) {
		try {
			if (session.getMethod() == Method.POST) {
				Map<String, String> files = new HashMap<String, String>();
				session.parseBody(files);

				String postBody = session.getQueryParameterString();

				if (postBody == null) {
					postBody = files.get("postData");
					postBody = java.net.URLDecoder.decode(postBody, "UTF-8");
				}

				if (postBody != null && postBody.startsWith("{")) {
					JSONObject obj = new JSONObject(postBody);
					EmailContent ec = new EmailContent(obj);
					boolean erg = SendMail.sendMail(ec.getSubject(), ec.getContent());
					return "OK";
				} else {
					SendMail.sendMail("Testmail", "Testcontent");
					return "TEST OK";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

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

	private String getRemoteIp(Map<String, String> headers) {
		return headers.get("http-client-ip");
	}

	public Response rerouteRequest(String targetURL) {
		HttpURLConnection connection = null;
		StringBuilder response = null;

		try {
			URL url = new URL("http://localhost:62001/" + targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(10000);
			connection.setRequestMethod("GET");

			connection.setUseCaches(false);
			connection.setDoOutput(false);

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			response = new StringBuilder(); // or StringBuffer if
											// Java version 5+

			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}

			rd.close();

			return newFixedLengthResponse(response.toString());

		} catch (Exception e) {
			return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", "Service not available");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	private Response parseWebAppContent(String[] splits, String methodID) {
		FileInputStream fis = null;
		StringBuilder fileURL = new StringBuilder();
		for (int i = 1; i < splits.length; i++) {
			fileURL.append("\\");
			fileURL.append(splits[i]);
		}

		// standard is index.html
		if (methodID.equalsIgnoreCase("dsgvo")) {
			fileURL = new StringBuilder();
			fileURL.append("\\index.html");
		}
		try {
			if (!fileURL.toString().isEmpty()) {
				File f = new File(
						"C:\\Users\\Jana\\Documents\\FHDW\\4.Semester\\WIP\\fhdw_wip_dsgvo\\fhdw_wip_dsgvo\\Dsgvo\\src"
								+ fileURL.toString());
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

}