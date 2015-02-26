package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * AutoException.
 * 
 * @author Xing Wei
 * 
 */

public class AutoException extends Exception {

	public enum Type {
		WRONG_FILENAME, MISSING_MODEL_NAME, MISSING_BASE_PRICE, MISSING_COLOR_OPTIONSET, MISSING_TRANSMISSION_MANUAL_OPTION
	}

	Type error = null;
	String filename = "";

	public AutoException(Type t, String filename) {
		super();
		this.error = t;
		this.filename = filename;
		log();
	}

	public void log() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(
					"AutoExceptionLog", true));
			Date time = new Date();
			bw.append(time.toString());
			bw.append("\t");
			bw.append("Exception: ");
			bw.append(this.error.toString());
			bw.append("\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fix() {
		FixError fixer = new FixError();
		switch (error) {
		case WRONG_FILENAME:
			fixer.fixWrongFileName(filename);
			break;
		case MISSING_MODEL_NAME:
			fixer.fixMissingModelName(filename);
			break;
		case MISSING_BASE_PRICE:
			fixer.fixMissingBasePrice(filename);
			break;
		case MISSING_COLOR_OPTIONSET:
			fixer.fixMissingColorOptionSet(filename);
			break;
		case MISSING_TRANSMISSION_MANUAL_OPTION:
			fixer.fixMissingTransmissionManualOption(filename);
			break;
		}
	}
}
