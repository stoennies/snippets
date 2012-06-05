package eu.toennies.snippets.collections;

import java.io.File;
import java.util.Comparator;

/**
 * This filter class is an implementation of a comparator for files.
 * It will sort the files as Windows does in its explorer.
 * 
 * @author toennies
 *
 */
public class WindowsExplorerFileComparator implements Comparator<File> {
	private String filePath1, filePath2;
	private int pos1, pos2, pathLength1, pathLength2;

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(File file1, File file2) {
		filePath1 = file1.getAbsolutePath();
		filePath2 = file2.getAbsolutePath();
		pathLength1 = filePath1.length();
		pathLength2 = filePath2.length();
		pos1 = pos2 = 0;

		int result = 0;
		while (result == 0 && pos1 < pathLength1 && pos2 < pathLength2) {
			char ch1 = filePath1.charAt(pos1);
			char ch2 = filePath2.charAt(pos2);

			if (Character.isDigit(ch1)) {
				result = Character.isDigit(ch2) ? compareNumbers() : -1;
			} else if (Character.isLetter(ch1)) {
				result = Character.isLetter(ch2) ? compareOther(true) : 1;
			} else {
				result = Character.isDigit(ch2) ? 1
						: Character.isLetter(ch2) ? -1 : compareOther(false);
			}

			pos1++;
			pos2++;
		}

		return result == 0 ? pathLength1 - pathLength2 : result;
	}

	/**
	 * This method compares numbers within the file path.
	 * @return the comparator result
	 */
	private int compareNumbers() {
		int end1 = pos1 + 1;
		while (end1 < pathLength1 && Character.isDigit(filePath1.charAt(end1))) {
			end1++;
		}
		int fullLen1 = end1 - pos1;
		while (pos1 < end1 && filePath1.charAt(pos1) == '0') {
			pos1++;
		}

		int end2 = pos2 + 1;
		while (end2 < pathLength2 && Character.isDigit(filePath2.charAt(end2))) {
			end2++;
		}
		int fullLen2 = end2 - pos2;
		while (pos2 < end2 && filePath2.charAt(pos2) == '0') {
			pos2++;
		}

		int delta = (end1 - pos1) - (end2 - pos2);
		if (delta != 0) {
			return delta;
		}

		while (pos1 < end1 && pos2 < end2) {
			delta = filePath1.charAt(pos1++) - filePath2.charAt(pos2++);
			if (delta != 0) {
				return delta;
			}
		}

		pos1--;
		pos2--;

		return fullLen2 - fullLen1;
	}

	/**
	 * This method compares characters.
	 * 
	 * @param isLetters
	 * @return the compare value of the character
	 */
	private int compareOther(boolean isLetters) {
		char ch1 = filePath1.charAt(pos1);
		char ch2 = filePath2.charAt(pos2);

		if (ch1 == ch2) {
			return 0;
		}

		if (isLetters) {
			ch1 = Character.toUpperCase(ch1);
			ch2 = Character.toUpperCase(ch2);
			if (ch1 != ch2) {
				ch1 = Character.toLowerCase(ch1);
				ch2 = Character.toLowerCase(ch2);
			}
		}

		return ch1 - ch2;
	}
	
}
