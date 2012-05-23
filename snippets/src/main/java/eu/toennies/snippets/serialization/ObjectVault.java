package eu.toennies.snippets.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Utility class for storing and loading java object at some place.
 * 
 * @author toennies
 *
 * @param <T> the class of the objects for this vault
 */
public final class ObjectVault<T extends Serializable> {

	/**
	 * Store the object to the given output location.
	 * 
	 * @param output
	 * @param object
	 * @throws IOException
	 */
	public void storeObject(File output, T object) throws IOException {
		FileOutputStream fos = new FileOutputStream(output);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(object);
		out.flush();
		out.close();
	}

	/**
	 * Read the object from the given input location.
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public T loadObject(File input) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream(input);
		ObjectInputStream in = new ObjectInputStream(fin);
		@SuppressWarnings("unchecked")
		T object = (T) in.readObject();
		in.close();
		return object;
	}

}
