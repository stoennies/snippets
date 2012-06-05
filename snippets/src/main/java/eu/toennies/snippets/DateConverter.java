package eu.toennies.snippets;

import java.sql.Date;
import java.util.Calendar;

/**
 * A utility class for doing date conversions.
 * 
 * @author toennies
 *
 */
public class DateConverter {
	
	
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


}
