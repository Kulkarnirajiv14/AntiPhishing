package com.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectFileHelper {
	public static Object readObjectFromFile(String fileName) {
		Object contacts = null;
		try {
			File f = new File(fileName);
			if (f.exists()) {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(fileName));
				contacts = ois.readObject();
				ois.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contacts;
	}

	public static void writeObject2File(Object map1, String fileName) {
		ObjectOutputStream fos;
		try {
			File f = new File(fileName);
			fos = new ObjectOutputStream(new FileOutputStream(f));

			fos.writeObject(map1);
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
