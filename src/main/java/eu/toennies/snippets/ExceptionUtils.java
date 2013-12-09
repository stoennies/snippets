package eu.toennies.snippets;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * This is a utility class for exception handling.
 * 
 * @author toennies
 *
 */
public class ExceptionUtils {

	
	/**
	 * This method returns the exception stack trace as string.
	 * 
	 * @param exception - the exception to get the stack trace from
	 * @return the stack trace as string
	 */
	public static String getStackTrace(Exception exception) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		exception.printStackTrace(printWriter);
		return result.toString();
	}
}
