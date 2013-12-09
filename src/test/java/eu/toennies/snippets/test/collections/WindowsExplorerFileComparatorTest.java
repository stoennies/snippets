package eu.toennies.snippets.test.collections;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import eu.toennies.snippets.collections.WindowsExplorerFileComparator;

public class WindowsExplorerFileComparatorTest {

	final File[] filesExpected = { new File("slide1.png"), new File("slide2.png"),
			new File("slide9.png"), new File("slide10.png"),
			new File("slide11.png") };

	@Test
	public final void testCompare() {
		File[] filesToSort = { new File("slide1.png"), new File("slide10.png"),
				new File("slide11.png"), new File("slide2.png"),
				new File("slide9.png") };

		Arrays.sort(filesToSort, new WindowsExplorerFileComparator());
		assertArrayEquals(filesExpected, filesToSort);
	}

}
