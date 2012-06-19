package eu.toennies.snippets.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import eu.toennies.snippets.collections.WindowsExplorerFileComparator;

public class FileLister {
	
	public static List<File> getSortedWindowsExplorerList(final File directory, final String[] fileTypes, final boolean recursive)
			throws IOException {

		if (!directory.isDirectory()) {
			throw new IOException("The given directory is not a directory!");
		}

		Collection<File> fileList = FileUtils.listFiles(directory, fileTypes, recursive);
		List<File> files = new ArrayList<File>(fileList);
		Collections.sort(files, new WindowsExplorerFileComparator());
		
		return files;
	}


}
