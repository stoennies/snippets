package eu.toennies.snippets.network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

public final class UrlWorker {

	private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UrlWorker.class);
	

	/**
	 * Hidden constructor for utility classes.
	 */
	private UrlWorker() {
		super();
	}
	

	/**
	 * This method retrieves a binary file from a given URL and saves the file to
	 * the given path.
	 * 
	 * @param url
	 *            - the address of the remote file
	 * @param path
	 *            - the address where to store the file
	 * @param overwrite
	 *            - true if a already existing file should be overriden
	 * @return true if the file could successfully be retrieved
	 */
	public static boolean getRawFile(final String url, final String path, final boolean overwrite) {
		URL u = null;
		try {
			u = new URL(url);

		} catch (MalformedURLException e) {
			LOGGER.warn("MalformedURLException caught while attemping to create new URL object for: " + url);
			LOGGER.trace("MalformedURLException trace {}", e);
			return false;
		}

		
		// check for already existing file
		final String filename = path + File.separator + u.getFile().substring(u.getFile().lastIndexOf('/') + 1);
		File outputFile = new File(filename);
		if (outputFile.exists() && !overwrite) {
			LOGGER.info("The file " + outputFile.getAbsolutePath()
			        + " already exists and should not be overridden. Therefore, we skipped downloading the file from "
			        + url);
			return false;
		}
		
		URLConnection uc = null;
		int contentLength;
		try {
			uc = u.openConnection();
		} catch(IOException e) {
			LOGGER.warn("IOException caught while opening a connection to: " + url);
			LOGGER.trace("IOException trace {}", e);
			return false;
		}
		
        String contentType = uc.getContentType();
		contentLength = uc.getContentLength();
		if (contentType.startsWith("text/") || contentLength == -1) {
			LOGGER.warn("This " + url + " is not a binary file. Stopped downloading it.");
			return false;
		}

		InputStream raw = null;
		InputStream in = null;
		byte[] data = null;
		try {
			raw = uc.getInputStream();
			in = new BufferedInputStream(raw);
			data = new byte[contentLength];
			int bytesRead = 0;
			int offset = 0;
			while (offset < contentLength) {
				bytesRead = in.read(data, offset, data.length - offset);
				if (bytesRead == -1) {
					break;
				}
				offset += bytesRead;
			}
			in.close();

			if (offset != contentLength) {
				LOGGER.warn("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
				return false;
			}
		
		} catch(IOException e) {
			LOGGER.warn("IOException caught while reading from: " + url);
			LOGGER.trace("IOException trace {}", e);
			return false;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(raw);
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFile);
			out.write(data);
			out.flush();
			out.close();
        } catch (IOException e) {
			LOGGER.warn("IOException caught while writing file to: " + path);
			LOGGER.trace("IOException trace {}", e);
			return false;
        } finally {
			IOUtils.closeQuietly(out);
		}
		return true;
	}

}
