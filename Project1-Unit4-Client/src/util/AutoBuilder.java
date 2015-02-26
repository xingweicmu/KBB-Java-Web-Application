package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import exception.AutoException;

import model.Automobile;

/**
 * AutoBuilder is a util class for building an object of Automotive from reading
 * a file.
 * 
 * @author Xing Wei
 * 
 */
public class AutoBuilder {

	public Automobile buildAutoObject(String filename) throws AutoException {
		Automobile auto = null;
		try {
			FileReader file = new FileReader(filename);
			BufferedReader br = new BufferedReader(file);
			String makeLine = br.readLine();
			if (makeLine.split(":").length < 2) {
				throw new AutoException(AutoException.Type.MISSING_MODEL_NAME,
						filename);
			}
			String make = makeLine.split(":")[1];

			String modelLine = br.readLine();
			String model = modelLine.split(":")[1];

			String basePriceLine = br.readLine();
			if (basePriceLine.split(":").length < 2) {
				throw new AutoException(AutoException.Type.MISSING_BASE_PRICE,
						filename);
			}
			float basePrice = Float.parseFloat(basePriceLine.split(":")[1]);
			// int size = countProperties(filename);
			// auto = new Automobile(carName, basePrice, size - 2);
			auto = new Automobile(make, model, basePrice);

			int optionSetindex = 0;
			String line = br.readLine();
			// For each optionSet
			while (line != null) {
				String[] optionSets = line.split(":");
				if (optionSets.length < 2) {
					if (optionSets[0].equals("Color"))
						throw new AutoException(
								AutoException.Type.MISSING_COLOR_OPTIONSET,
								filename);
				}
				String optionSetName = optionSets[0];
				String[] options = optionSets[1].split(";");
				if (options.length == 1) {
					if (optionSetName.equals("Transmission"))
						throw new AutoException(
								AutoException.Type.MISSING_TRANSMISSION_MANUAL_OPTION,
								filename);
				}
				// auto.setValueOfOptionSet(optionSetindex, optionSetName,
				// options.length);
				auto.setValueOfOptionSet(optionSetName);

				// For each option
				for (int i = 0; i < options.length; i++) {
					int optIndex = i;
					String parts[] = options[i].split(",");
					String optName = parts[0];
					float price = Float.parseFloat(parts[1]);
					// auto.setValueOfOpitonSet(optionSetindex, optIndex,
					// optName,
					// price);
					auto.setValueOfOptionSet(optionSetName, optName, price);
				}
				optionSetindex++;
				line = br.readLine();
			}

			br.close();

		} catch (FileNotFoundException e) {
			throw new AutoException(AutoException.Type.WRONG_FILENAME, filename);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return auto;
	}
	
	public Automobile buildAutoFromProperties(Properties properties){
		String carModel = properties.getProperty("CarModel");
		Automobile auto = null;
		if( carModel != (null)){
			//auto = new Automobile();
			String carMake = properties.getProperty("CarMake");
			float basePrice = Float.parseFloat(properties.getProperty("BasePrice"));
			auto = new Automobile(carMake, carModel, basePrice);
			
			int optionSetNum = 1;
			while(properties.getProperty("Option"+optionSetNum) != (null)){
				String optionSetName = properties.getProperty("Option"+optionSetNum);
				auto.setValueOfOptionSet(optionSetName);
				int optionNum = 1;
				while(properties.getProperty("Option"+optionSetNum+"Name"+optionNum) != null){
					String optionName = properties.getProperty("Option"+optionSetNum+"Name"+optionNum);
					float optionPrice = Float.parseFloat(properties.getProperty("Option"+optionSetNum+"Price"+optionNum));
					auto.setValueOfOptionSet(optionSetName, optionName, optionPrice);
					optionNum++;
				}
				optionSetNum++;
			}
			
		}
		return auto;
	}

}
