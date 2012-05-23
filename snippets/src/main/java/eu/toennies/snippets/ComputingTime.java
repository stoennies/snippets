package eu.toennies.snippets;

public class ComputingTime {
	
	public static long getInMiliseconds(long startTime) {
		return getInMiliseconds(startTime, System.currentTimeMillis());
	}

	public static float getInSeconds(long startTime) {
		return getInMiliseconds(startTime)/1000F;
	}

	public static float getInMinutes(long startTime) {
		return getInMiliseconds(startTime)/(60*1000F);
	}

	public static float getInHours(long startTime) {
		return getInMiliseconds(startTime)/(60*60*1000F);
	}

	public static float getInDays(long startTime) {
		return getInMiliseconds(startTime)/(24*60*60*1000F);
	}

	public static long getInMiliseconds(long startTime, long endTime) {
		return endTime-startTime;
	}

	public static float getInSeconds(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/1000F;
	}

	public static float getInMinutes(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/(60*1000F);
	}

	public static float getInHours(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/(60*60*1000F);
	}

	public static float getInDays(long startTime, long endTime) {
		return getInMiliseconds(startTime, endTime)/(24*60*60*1000F);
	}
}
