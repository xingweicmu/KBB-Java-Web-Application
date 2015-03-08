package server;

import java.util.Properties;

import model.Automobile;

public class BuildCarModelOptions {

	/**
	 * Build auto from property file.
	 * @param properties the Properties object
	 * @return Automobile the automobile generated
	 */
	public static Automobile buildAutoFromProperties(Properties properties){
		String carModel = properties.getProperty("CarModel");
		Automobile auto = null;
		if(carModel != (null)){
			
			// Get basic info on the automobile and initialize the auto
			String carMake = properties.getProperty("CarMake");
			float basePrice = Float.parseFloat(properties.getProperty("BasePrice"));
			auto = new Automobile(carMake, carModel, basePrice);
			
			int optionSetNum = 1;
			while(properties.getProperty("Option"+optionSetNum) != (null)){
				
				// Get and set optionset info 
				String optionSetName = properties.getProperty("Option"+optionSetNum);
				auto.setValueOfOptionSet(optionSetName);
				int optionNum = 1;
				while(properties.getProperty("Option"+optionSetNum+"Name"+optionNum) != null){
					
					// Get and set option info
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
