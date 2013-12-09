package eu.toennies.snippets.test.serialization;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import eu.toennies.snippets.serialization.ObjectVault;

public class ObjectVaultTest {

	private VaultObject object = new VaultObject("test1");
	ObjectVault<VaultObject> ov = new ObjectVault<VaultObject>();
	File testFile = new File("test");

	@Test
	public void testStoreObject() {
		try {
			ov.storeObject(testFile, object);
			
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testLoadObject() {
		try {
			VaultObject object2 = ov.loadObject(testFile);
			Assert.assertEquals(object, object2);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (ClassNotFoundException e) {
			fail(e.getMessage());
		}
	}

}
