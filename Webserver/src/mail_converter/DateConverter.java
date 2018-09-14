package mail_converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {
	public static String convertFromSQLToNormal(String originalDate) {
		originalDate += " 00:00";
		DateTimeFormatter formatterOriginal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		DateTimeFormatter formatterTarget = DateTimeFormatter.ofPattern("dd.MM.yyy");
		LocalDateTime dateTime = LocalDateTime.parse(originalDate, formatterOriginal);
		String convertedDate = formatterTarget.format(dateTime);
		
		return convertedDate;	
	}
}
