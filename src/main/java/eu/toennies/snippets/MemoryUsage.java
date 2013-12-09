package eu.toennies.snippets;

import java.text.DecimalFormat;

/**
 * A utility class for calculating the memory usage of a routine.
 * 
 * @author toennies
 *
 */
public class MemoryUsage {
	
	private final long memstart;
	private final DecimalFormat df;

	/**
	 * The default constructor using the 0.00 format for the method return values.
	 */
	public MemoryUsage() {
		this("0.00");
	}
	
	/**
	 * The constructor using the given decimal format for the method return values.
	 * @param decimalFormat
	 */
	public MemoryUsage(String decimalFormat) {
		df = new DecimalFormat(decimalFormat);
		System.gc();
		System.gc();
		memstart = Runtime.getRuntime().freeMemory();
	}
	
	/**
	 * Retrieves the used memory space in bytes.
	 * @return the used memory in bytes
	 */
	private long getBytes() {
		System.gc();
		System.gc();
		final long memend = Runtime.getRuntime().freeMemory();
		return (memstart - memend);
	}
	
	
	/**
	 * Returns the used memory space in bytes using the decimal format given by initialization.
	 * @return the used memory in bytes
	 */
	public String getRoundedBytes() {
		return df.format(getBytes()) + " B";
	}
	
	/**
	 * Returns the used memory space in kilobytes using the decimal format given by initialization.
	 * @return the used memory in kilobytes
	 */
	public String getRoundedKB() {
		return df.format(getBytes() /  1024F) + " KB";
	}

	/**
	 * Returns the used memory space in megabytes using the decimal format given by initialization.
	 * @return the used memory in megabytes
	 */
	public String getRoundedMB() {
		return df.format(getBytes() /  (1024F * 1024F)) + " MB";
	}

	/**
	 * Returns the used memory space in gigabytes using the decimal format given by initialization.
	 * @return the used memory in gigabytes
	 */
	public String getRoundedGB() {
		return df.format(getBytes() /  (1024F * 1024F * 1024F)) + " GB";
	}

}
