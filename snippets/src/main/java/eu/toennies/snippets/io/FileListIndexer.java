package eu.toennies.snippets.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import eu.toennies.snippets.collections.WindowsExplorerFileComparator;

public class FileListIndexer {

	// These are the instance fields of the WordList class
	private RandomAccessFile indexFile; // the file to read words from
	private long[] positions; // the index that gives the position of each word
	private String indexFileName;
	private List<File> files;
	
	// Create a WordList object based on the named file
	public FileListIndexer(String indexFileName) {
		this.indexFileName = indexFileName;
	}
	
	public void openIndex() throws IOException {
		// Open the random access file for read-only access
		indexFile = new RandomAccessFile(indexFileName, "r");
		
		// Now read the array of file positions from it
		int numwords = indexFile.readInt(); // Read array length
		positions = new long[numwords]; // Allocate array
		for (int i = 0; i < numwords; i++)
			// Read array contents
			positions[i] = indexFile.readLong();
	}
	
	public String getMovedFileName(int position, String oldDirectory, String newDirectory) throws IOException {
		// Make sure close() hasn't already been called.
		if (indexFile == null)
			throw new IllegalStateException("already closed");
		indexFile.seek(positions[position]); // Move to the word position in the file.
		return indexFile.readUTF().replace(oldDirectory, newDirectory); // Read and return the string at that position.
	}

	// This method creates the filename list may take a long time!
	public void createIndex(String sourceName, boolean recursive, String[] fileTypes) throws IOException {
		Collection<File> fileList = FileUtils.listFiles(new File(sourceName), fileTypes, recursive);
		files = new ArrayList<File>(fileList);
		Collections.sort(files, new WindowsExplorerFileComparator());
		
		// Open the file for read/write access ("rw"). We only need to write,
		// but have to request read access as well
		RandomAccessFile tempIndexFile = new RandomAccessFile(indexFileName, "rw");

		// This array will hold the positions of each word in the file
		long wordPositions[] = new long[files.size()];

		// Reserve space at the start of the file for the wordPositions array
		// and the length of that array. 4 bytes for length plus 8 bytes for
		// each long value in the array.
		tempIndexFile.seek(4L + (8 * files.size()));

		// Now, loop through the words and write them out to the file,
		// recording the start position of each word. Note that the
		// text is written in the UTF-8 encoding, which uses 1, 2, or 3 bytes
		// per character, so we can't assume that the string length equals
		// the string size on the disk. Also note that the writeUTF() method
		// records the length of the string so it can be read by readUTF().
		for (int i = 0; i < files.size(); i++) {
			wordPositions[i] = tempIndexFile.getFilePointer(); // record file position
			tempIndexFile.writeUTF(files.get(i).getAbsolutePath()); // write word
		}

		// Now go back to the beginning of the file and write the positions
		tempIndexFile.seek(0L); // Start at beginning
		tempIndexFile.writeInt(wordPositions.length); // Write array length
		for (int i = 0; i < wordPositions.length; i++)
			// Loop through array
			tempIndexFile.writeLong(wordPositions[i]); // Write array element
		tempIndexFile.close(); // Close the file when done.
	}

	// Call this method when the WordList is no longer needed.
	public void close() throws IOException {
		if (indexFile != null)
			indexFile.close(); // close file
		indexFile = null; // remember that it is closed
		positions = null;
	}

	// Return the number of words in the WordList
	public int size() {
		// Make sure we haven't closed the file already
		if (indexFile == null)
			throw new IllegalStateException("already closed");
		return positions.length;
	}

	// Return the string at the specified position in the WordList
	// Throws IllegalStateException if already closed, and throws
	// ArrayIndexOutOfBounds if i is negative or >= size()
	public String getFileName(int position) throws IOException {
		// Make sure close() hasn't already been called.
		if (indexFile == null)
			throw new IllegalStateException("already closed");
		indexFile.seek(positions[position]); // Move to the word position in the file.
		return indexFile.readUTF(); // Read and return the string at that position.
	}
}
