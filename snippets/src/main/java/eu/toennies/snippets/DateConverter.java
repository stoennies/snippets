package eu.toennies.snippets;

import java.sql.Date;
import java.util.Calendar;

public class DateConverter {
	
	
	public static Date asSQLDate(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
	    cal.set( Calendar.YEAR, year);
	    cal.set( Calendar.MONTH, month );
	    cal.set( Calendar.DATE, day );
		return new Date(cal.getTimeInMillis());
	}


}
