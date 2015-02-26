package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.Automobile.OptionSet.Option;

/**
 * Automotive class contains an inner class, OptionSet, which also contains an
 * inner class, Option.
 * 
 * @author Xing Wei
 * 
 */
public class Automobile implements Serializable {

	private String make;
	private String model;
	private ArrayList<OptionSet> optionSetList;
	private float basePrice;
	private Option choice;
	private boolean available = false;
	private boolean OptionAvailable = false;

	// Constructor
	public Automobile() {
		super();
	}

	public Automobile(String make, String model, float basePrice) {
		this.make = make;
		this.model = model;
		this.basePrice = basePrice;
		// this.optionSets = new OptionSet[size];
		this.optionSetList = new ArrayList<OptionSet>();
	}

	public synchronized float getBasePrice() {
		return this.basePrice;
	}

	public synchronized String getMake() {
		return make;
	}

	public synchronized String getModel() {
		return model;
	}

	public synchronized Option getChoice() {
		return choice;
	}

	public synchronized ArrayList<OptionSet> getOpetionSetList() {
		return this.optionSetList;
	}

	public synchronized void setChoice(Option choice) {
		this.choice = choice;
	}

	public synchronized OptionSet getOptionSet(String optionSetName) {
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).equals(optionSetName)) {
				return this.optionSetList.get(i);
			}
		}
		return null;
	}

	public synchronized String getOptionChoice(String optionSetName) {
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getName().equals(optionSetName)) {
				return this.optionSetList.get(i).getOptionChoice().getName();
			}
		}
		return null;
	}

	public synchronized float getOptionChoicePrice(String optionSetName) {
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getName().equals(optionSetName)) {
				return this.optionSetList.get(i).getOptionChoice().getPrice();
			}
		}
		return 0;
	}

	public synchronized float getTotalPrice() {
		float price = (float) 0;
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getOptionChoice() != null)
				price += this.optionSetList.get(i).getOptionChoice().getPrice();
		}
		return price;
	}

	// Find
	public synchronized OptionSet findOptionSetWithName(String optionSetName) {
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).equals(optionSetName)) {
				return this.optionSetList.get(i);
			}
		}
		return null;
	}

	// Setter
	public synchronized void setMake(String make) {
		this.make = make;
	}

	public synchronized void setModel(String model) {
		this.model = model;
	}

	public synchronized void setBasePrice(float price) {
		this.basePrice = price;
	}

	public synchronized void setValueOfOptionSet(String optionSetName) {
		OptionSet optionSet = new OptionSet(optionSetName);
		this.optionSetList.add(optionSet);
	}

	public synchronized void setValueOfOptionSet(String optionSetName,
			String optionName, float price) {

		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getName().equals(optionSetName)) {
				this.optionSetList.get(i).setOptionValue(optionName, price);
			}
		}
	}

	public synchronized void setOptionChoice(String optionSetName,
			String optionName) {

		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getName().equals(optionSetName)) {
				this.optionSetList.get(i).setOptionChoice(optionName);
			}
		}
	}

	// Delete
	public boolean deleteOptionSetWithOutSynchronize(String optionSetName) {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println("[" + name + " info]DELETing...");
		for (int i = 0; i < optionSetList.size(); i++) {
			if (optionSetList.get(i).getName().equals(optionSetName)) {
				optionSetList.remove(optionSetList.get(i));
				return true;
			}
		}
		return false;
	}

	public void addOptionSetWithOutSynchronize(String optionSetName) {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println("[" + name + " info]ADDing...");
		OptionSet newOptionSet = new OptionSet();
		newOptionSet.setName(optionSetName);
		this.optionSetList.add(newOptionSet);
	}

	public synchronized boolean deleteOptionSet(String optionSetName) {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println("[" + name + " info]DELETing...");
		while (available == false) {

			try {
				System.out
						.println("["
								+ name
								+ " error]HOOPS! Nothing to delete! Waiting for adding...");
				wait();

			} catch (InterruptedException e) {
			}
		}

		for (int i = 0; i < optionSetList.size(); i++) {
			if (optionSetList.get(i).getName().equals(optionSetName)) {
				optionSetList.remove(optionSetList.get(i));
				return true;
			}
		}
		available = false;
		notifyAll();
		return false;
	}

	public synchronized void addOptionSet(String optionSetName) {
		Thread t = Thread.currentThread();
		String name = t.getName();
		System.out.println("[" + name + " info]ADDing...");
		while (available == true) {
			try {
				System.out.println("[" + name
						+ " error]waiting for deleting...");
				wait();
			} catch (InterruptedException e) {
			}
		}

		OptionSet newOptionSet = new OptionSet();
		newOptionSet.setName(optionSetName);
		this.optionSetList.add(newOptionSet);
		available = true;
		notifyAll();
	}

	public synchronized void addOption(String optionSetName, String optionName) {
		while (OptionAvailable == true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		for (int i = 0; i < optionSetList.size(); i++) {
			if (optionSetList.get(i).getName().equals(optionSetName)) {
				optionSetList.get(i).addOption(optionName, 0);
			}
		}
		OptionAvailable = true;
		notifyAll();
	}

	public synchronized boolean deleteOption(String optionSetName,
			String optionName) {
		while (OptionAvailable == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		for (int i = 0; i < optionSetList.size(); i++) {
			if (optionSetList.get(i).getName().equals(optionSetName)) {
				optionSetList.get(i).removeOption(optionName);
				return true;
			}
		}
		OptionAvailable = false;
		notifyAll();
		return false;
	}

	// Update
	public synchronized void updateOptionSetName(String optionSetName,
			String newName) {
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getName().equals(optionSetName)) {
				this.optionSetList.get(i).setName(newName);
			}
		}
	}

	public synchronized void updateOptionPrice(String optionSetName,
			String optionName, float price) {
		for (int i = 0; i < this.optionSetList.size(); i++) {
			if (this.optionSetList.get(i).getName().equals(optionSetName)) {
				this.optionSetList.get(i).UpdateOption(optionName, price);
			}
		}
	}

	public synchronized String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Make: ");
		sb.append(make);
		sb.append("\nModel: ");
		sb.append(model);
		sb.append("\nBase Price: $");
		sb.append(basePrice);
		sb.append("\n");

		// Iterator<String> iterator = this.optionSetList.keySet().iterator();
		// while(iterator.hasNext()){
		// String key = iterator.next();
		// OptionSet optionSet = this.optionSetList.get(key);
		// sb.append("\n");
		// sb.append(optionSet.toString());
		// }
		for (int i = 0; i < this.optionSetList.size(); i++) {
			sb.append("\n");
			sb.append(this.optionSetList.get(i));
		}
		return sb.toString();
	}

	// Inner class OptionSet in Automotive
	public class OptionSet implements Serializable {
		// private Option options[];
		private String name;
		private ArrayList<Option> optionList;
		private Option choice = null;

		OptionSet() {
			super();
			this.optionList = new ArrayList<Option>();
		}

		OptionSet(String n) {
			super();
			this.name = n;
			this.optionList = new ArrayList<Option>();
		}

		public void addOption(String name, float price) {
			Option newOption = new Option(name, price);
			this.optionList.add(newOption);
		}
		
		public void removeOption(String optionName){
			for(int i = 0; i < optionList.size(); i++){
				if(optionList.get(i).getName().equals(optionName)){
					optionList.remove(i);
				}
			}
		}

		public ArrayList<Option> getOption() {
			return this.optionList;
		}

		public void setOptions(ArrayList<Option> options) {
			this.optionList = options;
		}

		public Option getOptionChoice() {
			return this.choice;
		}

		public void setOptionChoice(String optionName) {
			for (int i = 0; i < optionList.size(); i++) {
				if (optionList.get(i).getName().equals(optionName)) {
					this.choice = optionList.get(i);
				}
			}
		}

		public void setOptionValue(String optionName, float price) {
			Option option = new Option(optionName, price);
			this.optionList.add(option);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		// Find
		public Option findOptionWithName(String name) {
			for (int i = 0; i < this.optionList.size(); i++) {
				if (this.optionList.get(i).getName().equals(name)) {
					return this.optionList.get(i);
				}
			}
			return null;
		}

		// Delete
		public void DeleteOption(String name) {
			for (int i = 0; i < this.optionList.size(); i++) {
				if (this.optionList.get(i).getName().equals(name)) {
					this.optionList.remove(i);
				}
			}
		}

		// Update
		public void UpdateOption(String optionName, Option option) {
			for (int i = 0; i < this.optionList.size(); i++) {
				if (this.optionList.get(i).getName().equals(name)) {
					this.optionList.set(i, option);
				}
			}
		}

		public void UpdateOption(String opitonName, float newPrice) {
			for (int i = 0; i < this.optionList.size(); i++) {
				if (this.optionList.get(i).getName().equals(opitonName)) {
					this.optionList.get(i).setPrice(newPrice);
				}
			}
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name);
			sb.append(":\n");
			for (int i = 0; i < optionList.size(); i++) {
				sb.append(optionList.get(i).toString());
				sb.append("\n");
			}
			return sb.toString();
		}

		// Inner class Option in OptionSet
		public class Option implements Serializable {
			private String name;
			private float price;

			public Option() {
				super();
			}

			public Option(String name, float price) {
				this.name = name;
				this.price = price;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public float getPrice() {
				return price;
			}

			public void setPrice(float price) {
				this.price = price;
			}

			public String toString() {
				StringBuilder sb = new StringBuilder();
				sb.append("    -");
				sb.append(name);
				sb.append(": $");
				sb.append(price);
				return sb.toString();
			}

		}
	}

}
