package pe.com.fabrica.aldesa.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

	private DateUtil() {
		throw new UnsupportedOperationException();
	}

	public static Date of(String contentDate) {
		return Date.from(LocalDate.parse(contentDate).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static String getCurrentHour() {
		LocalDateTime ldt = LocalDateTime.now();
		String text = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		return text.split("T")[1];
	}

}
