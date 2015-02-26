package exception;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A helper class to fix error for exception.
 * 
 * @author Xing Wei
 * 
 */
public class FixError {

	public void fixWrongFileName(String fileName) {

		// If file doesn't exist, we will read from a default file
		// and write to the fixed file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"input")));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					fileName + "_fixed")));
			String line = br.readLine();
			while (line != null) {

				bw.append(line);
				bw.append("\n");
				line = br.readLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fixMissingModelName(String fileName) {
		// If missing model name in the file, read from the file and append
		// default model name to the fixed file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					fileName)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					fileName + "_fixed")));
			String line = br.readLine();
			while (line != null) {
				if (line.equals("Name:")) {
					bw.append(line);
					bw.append("FordZTW");
					bw.append("\n");
				} else {
					bw.append(line);
					bw.append("\n");
				}
				line = br.readLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fixMissingBasePrice(String fileName) {
		// If missing base price in the file, read from the file and append
		// default base price to the fixed file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					fileName)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					fileName + "_fixed")));
			String line = br.readLine();
			while (line != null) {
				if (line.equals("Base Price:")) {
					bw.append(line);
					bw.append("100000"); // Default base price
					bw.append("\n");
				} else {
					bw.append(line);
					bw.append("\n");
				}
				line = br.readLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fixMissingColorOptionSet(String fileName) {
		// If missing color option set in the file, read from the file and
		// append default color option set to the fixed file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					fileName)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					fileName + "_fixed")));
			String line = br.readLine();
			while (line != null) {
				if (line.equals("Color:")) {
					bw.append(line);
					bw.append("Fort Knox Gold Clearcoat Metallic,0;"
							+ "Liquid Grey Clearcoat Metallic,0;"
							+ "Infra-Red Clearcoat,0;"
							+ "Grabber Green Clearcoat Metallic,0;"
							+ "Sangria Red Clearcoat Metallic,0;"
							+ "French Blue Clearcoat Metallic,0;"
							+ "Twilight Blue Clearcoat Metallic,0;"
							+ "CD Silver Clearcoat Metallic,0;"
							+ "Pitch Black Clearcoat,0;"
							+ "Cloud 9 White Clearcoat,0"); // Default color
															// sets // base
															// price
					bw.append("\n");
				} else {
					bw.append(line);
					bw.append("\n");
				}
				line = br.readLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fixMissingTransmissionManualOption(String fileName) {
		// If missing manual option in the file, read from the file and append
		// default manual option to the fixed file.
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					fileName)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					fileName + "_fixed")));
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("Transmission:")) {
					bw.append(line);
					bw.append("manual,-815");
					bw.append("\n");

				} else {
					bw.append(line);
					bw.append("\n");
				}
				line = br.readLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
