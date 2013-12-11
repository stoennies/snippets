package eu.toennies.snippets;


public class ComputingTime {
	private static final float thousand = 1000F;
	private static final float sixty = 60F;
	private static final float twentyFour = 24F;
	
	/**
	 * Hidden utility class constructor
	 */
	private ComputingTime() {
		super();
	}

	
	public static long getInMiliseconds(long startTime) {
		return getInMiliseconds(startTime, System.currentTimeMillis());
	}

	public static float getInSeconds(long startTime) {
		return getInMiliseconds(startTime)/thousand;
	}

	public static float getInMinutes(long startTime) {
		return getInMiliseconds(startTime)/(sixty*thousand);
	}

	public static float getInHours(long startTime) {
		return getInMiliseconds(startTime)/(sixty*sixty*thousand);
	}

	public static float getInDays(long startTime) {
		return getInMiliseconds(startTime)/(twentyFour*sixty*sixty*thousand);
	}

	public static long getInMiliseconds(long startTime, long endTime) {
		return endTime-startTime;
	}

	public static float getInSeconds(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/thousand;
	}

	public static float getInMinutes(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/(sixty*thousand);
	}

	public static float getInHours(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/(sixty*sixty*thousand);
	}

	public static float getInDays(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/(twentyFour*sixty*sixty*thousand);
	}
}
