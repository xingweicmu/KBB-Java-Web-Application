package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Automobile;

/**
 * FileIO is a util class responsible for serializing an object of Automotive
 * and deserializing the file.
 * 
 * @author Xing Wei
 * 
 */

public class FileIO {

	public void serializeAuto(Automobile automobile, String filename) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(filename));
			out.writeObject(automobile);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Automobile deserializeAuto(String filename) {
		Automobile automobile = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filename));
			automobile = (Automobile) in.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return automobile;
	}
}
