package eu.toennies.snippets.io;

import java.io.IOException;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		FileListIndexer indexer = new FileListIndexer("files.data");
		indexer.openIndex();
		System.out.println(indexer.getMovedFileName(12, "I:\\toennies\\", "I:\\ifis\\Forschung\\Datensätze\\"));
		
		

	}

}
