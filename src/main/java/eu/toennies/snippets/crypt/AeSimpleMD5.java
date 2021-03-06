package eu.toennies.snippets.crypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This utility class provides a MD5 hashing.
 * 
 * @author toennies
 *
 */
public class AeSimpleMD5 {
	
	/**
	 * Hidden constructor for utility class.
	 */
	private AeSimpleMD5() {
		super();
	}

	/**
	 * This method converts a byte[] array to its hex version.
	 * 
	 * @param data - the byte array to convert
	 * @return a Hex String representation of the given byte array
	 */
	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int twoHalfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9)) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = data[i] & 0x0F;
			} while (twoHalfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * This method calculates a M5D hash of the given text.
	 * 
	 * @param text - the text to hash
	 * @return a 32 character long hash of the given text
	 * 
	 * @throws NoSuchAlgorithmException if a MD5 algorithm could not be found
	 * @throws UnsupportedEncodingException if the utf-8 encoding is not available
	 */
	public static String md5(String text) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		md.update(text.getBytes("UTF-8"), 0, text.length());
		byte[] md5hash = md.digest();
		return convertToHex(md5hash);
	}
}