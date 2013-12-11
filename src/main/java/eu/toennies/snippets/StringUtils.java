package eu.toennies.snippets;


public class StringUtils {
	
	/**
	 * Hidden constructor for utility class.
	 */
	private StringUtils() {
		super();
	}

	/**
	 * Removes trailing and leading whitespaces and replace all whitespaces occurring twice or more within
	 * the string by one whitespace.
	 * @param original - the original string
	 * @return the cleaned string
	 */
	public static String removeSpaces(String original) {
		return original.trim().replaceAll("\\s+", " ");
	}
	
	/**
	 * Removes trailing and leading whitespaces and replace all whitespaces occurring twice or more within
	 * the string by one whitespace. Also removes newlines within the string.
	 * @param original - the original string
	 * @return the cleaned string
	 */
	public static String removeSpacesAndNewlines(String original) {
		return original.trim().replaceAll("\\n", "").replaceAll("\\s+", " ");
	}

}
