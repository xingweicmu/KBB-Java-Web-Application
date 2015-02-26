package server;

import java.util.Properties;

import model.Automobile;

public class BuildCarModelOptions {

	public static Automobile buildAutoFromProperties(Properties properties){
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
