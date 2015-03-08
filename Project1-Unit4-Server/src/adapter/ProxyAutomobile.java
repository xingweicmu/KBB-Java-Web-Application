package adapter;

import java.util.Iterator;
import java.util.LinkedHashMap;

import db.DBDeleteAuto;
import db.DBInsertAuto;
import db.DBUpdateAuto;
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
		updateAuto(modelName, optionSetName, optionName, newPrice);

	}

	public void BuildAuto(String filename, String modelName) {
		boolean fixed = false;
		AutoBuilder autoBuilder = new AutoBuilder();
		try {
			if (!autos.containsKey(modelName)) {
				autos.put(modelName, autoBuilder.buildAutoObject(filename));
				insertAuto(autos.get(modelName));
			}
		} catch (AutoException e) {
			e.fix();
			fixed = true;
		}
		if (fixed) {
			try {
				if (!autos.containsKey(modelName)) {
					autos.put(modelName,
							autoBuilder.buildAutoObject(filename + "_fixed"));
					insertAuto(autos.get(modelName));
				}
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

	public void addOption(String modelName, String optionSetName,
			String optionName) {
		autos.get(modelName).addOption(optionSetName, optionName);
	}

	public void deleteOption(String modelName, String optionSetName,
			String optionName) {
		autos.get(modelName).deleteOption(optionSetName, optionName);
	}

	public void buildCarFromProperties(String modelName, Automobile auto) {
		if (!autos.containsKey(modelName)) {
			autos.put(modelName, auto);
			insertAuto(auto);
		}
	}

	public String printAllModelName() {
		Iterator<String> iterator = autos.keySet().iterator();
		String sb = "";
		while (iterator.hasNext()) {
			sb += (iterator.next());
			sb += ("\t");
		}
		return sb;
	}

	public Automobile getSelectedAuto(String modelName) {
		Automobile auto = autos.get(modelName);
		return auto;
	}

	public void insertAuto(Automobile auto) {
		DBInsertAuto insertAuto = new DBInsertAuto("JSPHDEV");
		insertAuto.insertAuto(auto);
	}

	public boolean removeAuto(String modelName) {
		if (autos.containsKey(modelName)) {
			autos.remove(modelName);
			deleteAuto(modelName);
			return true;
		} else
			return false;
	}

	public void deleteAuto(String modelName) {
		DBDeleteAuto deleteAuto = new DBDeleteAuto("JSPHDEV");
		deleteAuto.deleteAuto(modelName);
	}

	public void updateAuto(String modelName, String optionsetName,
			String optionName, float newPrice) {
		DBUpdateAuto update = new DBUpdateAuto("JSPHDEV");
		update.updateOptionPrice(modelName, optionsetName, optionName, newPrice);
	}
}
