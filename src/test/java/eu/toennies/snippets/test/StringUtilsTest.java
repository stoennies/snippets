package eu.toennies.snippets.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import eu.toennies.snippets.StringUtils;

public class StringUtilsTest {

	private final String result = "Hello World!";
	private final String[] spacesTestArray = { "Hello  World!", "Hello   World!",
			"Hello      World!", " Hello World!", "    Hello World!",
			"     Hello      World!   " };
	private final String[] newlineTestArray = { "Hello\n World!", "Hello \n  World!",
			"Hello  \n    World!", "\n Hello World!", "  \n  Hello World!",
			"     Hello   \n   World!  \n  " };

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testRemoveSpaces() {
		for (String testString : Arrays.asList(spacesTestArray)) {
			assertEquals(result, StringUtils.removeSpaces(testString));
		}
	}

	@Test
	public final void testRemoveSpacesAndNewlines() {
		for (String testString : Arrays.asList(newlineTestArray)) {
			assertEquals(result, StringUtils.removeSpacesAndNewlines(testString));
		}
	}

}
