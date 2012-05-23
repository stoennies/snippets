package eu.toennies.snippets.network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public final class UrlWorker {
	
	
	public static void getRawFile(String url, String path, boolean overwrite) throws IOException {

		URL u = new URL(url);

	    String filename = path + File.separator + u.getFile().substring(u.getFile().lastIndexOf('/') + 1);
	    File outputFile = new File(filename);

	    if(outputFile.exists() && !overwrite) {
	    	return;
	    }
		
	    URLConnection uc = u.openConnection();
	    String contentType = uc.getContentType();
	    int contentLength = uc.getContentLength();
	    if (contentType.startsWith("text/") || contentLength == -1) {
	      throw new IOException("This is not a binary file.");
	    }
	    InputStream raw = uc.getInputStream();
	    InputStream in = new BufferedInputStream(raw);
	    byte[] data = new byte[contentLength];
	    int bytesRead = 0;
	    int offset = 0;
	    while (offset < contentLength) {
	      bytesRead = in.read(data, offset, data.length - offset);
	      if (bytesRead == -1)
	        break;
	      offset += bytesRead;
	    }
	    in.close();

	    if (offset != contentLength) {
	      throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
	    }

	    FileOutputStream out = new FileOutputStream(filename);
	    out.write(data);
	    out.flush();
	    out.close();
	}

}
