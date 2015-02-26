package adapter;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import exception.AutoException;
import util.AutoBuilder;
import model.Automobile;

public abstract class ProxyAutomobile {

	private static LinkedHashMap<String, Automobile> autos = new LinkedHashMap<String, Automobile>();

	public void fix() {
		// To be override
	}

	public void updateOptionSetName(String modelName, String optionSetName,
			String newName) {
		autos.get(modelName).updateOptionSetName(optionSetName, newName);

	}

	public void updateOptionPrice(String modelName, String optionSetName,
			String optionName, float newPrice) {

		autos.get(modelName).updateOptionPrice(optionSetName, optionName,
				newPrice);

	}

	public void BuildAuto(String filename, String modelName) {
		boolean fixed = false;
		AutoBuilder autoBuilder = new AutoBuilder();
		try {
			autos.put(modelName, autoBuilder.buildAutoObject(filename));
		} catch (AutoException e) {
			e.fix();
			fixed = true;
		}
		if (fixed) {
			try {
				autos.put(modelName,
						autoBuilder.buildAutoObject(filename + "_fixed"));
			} catch (AutoException e) {
				e.fix();
			}
		}

	}

	public void printAuto(String Modelname) {

		System.out.println(autos.get(Modelname).toString());

	}

	public void setOptionChoice(String modelName, String optionSetName,
			String optionName) {
		autos.get(modelName).setOptionChoice(optionSetName, optionName);
	}

	public String getOptionChoice(String modelname, String optionSetname) {
		return autos.get(modelname).getOptionChoice(optionSetname);
	}

	public float getOptionChoicePrice(String modelName, String optionSetName) {
		return autos.get(modelName).getOptionChoicePrice(optionSetName);
	}

	public float getTotalPrice(String modelName) {
		return autos.get(modelName).getTotalPrice();
	}

	public void deleteOptionSet(String modelName, String optionSetName) {
		if (autos.get(modelName).deleteOptionSet(optionSetName)) {
			System.out.println("[System Info]Target Option Set Deleted!");
		} else {
			System.out.println("[System Info]Deleted failed!");
		}

	}

	public void addOptionSet(String modelName, String optionSetName) {
		autos.get(modelName).addOptionSet(optionSetName);
		System.out.println("[System Info]New Option Set Added!");

	}

	public void deleteOptionSetWithoutSync(String modelName,
			String optionSetName) {
		if (autos.get(modelName).deleteOptionSetWithOutSynchronize(
				optionSetName)) {
			System.out.println("[System Info]Target Option Set Deleted!");
		} else {
			System.out.println("[System Info]Deleted failed!");
		}

	}

	public void addOptionSetWithoutSync(String modelName, String optionSetName) {
		autos.get(modelName).addOptionSetWithOutSynchronize(optionSetName);
		System.out.println("[System Info]New Option Set Added!");

	}
	
	public void addOption(String modelName, String optionSetName, String optionName){
		autos.get(modelName).addOption(optionSetName, optionName);
	}
	public void deleteOption(String modelName, String optionSetName, String optionName){
		autos.get(modelName).deleteOption(optionSetName, optionName);
	}
	
	public void buildCarFromProperties(String modelName, Automobile auto){
		autos.put(modelName, auto);
	}
	public String printAllModelName(){
		Iterator<String> iterator = autos.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		while(iterator.hasNext()){
			sb.append(iterator.next());
			sb.append("\t");
		}
		return sb.toString();
	}
	public Automobile getSelectedAuto(String modelName){
		Automobile auto = autos.get(modelName);
		return auto;
	}


}
