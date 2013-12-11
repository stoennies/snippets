package eu.toennies.snippets;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A utility class for doing date conversions.
 * 
 * @author toennies
 *
 */
public class DateConverter {
	
	/**
	 * Hidden utility class constructor
	 */
	private DateConverter() {
		super();
	}

	/**
	 * This method instantiates a sql data object from the given integer values.
	 * @param day to use
	 * @param month to use
	 * @param year to use
	 * @return the sql date
	 */
	public static Date asSQLDate(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
	    cal.set( Calendar.YEAR, year);
	    cal.set( Calendar.MONTH, month );
	    cal.set( Calendar.DATE, day );
		return new Date(cal.getTimeInMillis());
	}
	
	public static String toUtcDate(String dateStr) throws Exception {
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		// Add other parsing formats to try as you like:
		String[] dateFormats = { "yyyy-MM-dd", "MMM dd, yyyy hh:mm:ss Z" };
		for (String dateFormat : dateFormats) {
			try {
				return out.format(new SimpleDateFormat(dateFormat).parse(dateStr));
			} catch (ParseException ignore) {
			}
		}
		throw new Exception("Invalid date: " + dateStr);
	}


}
