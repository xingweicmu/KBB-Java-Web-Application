package scale;

import adapter.EditThread;

public class EditOptions extends Thread {
	private String modelName;
	private int operation;
	private EditThread et;
	private String newOptionSetName;
	private String optionSetName;
	private String newOptionName;

	public EditOptions(String modelName, EditThread editThread, int operation, String newAddedOptionSetName) {
		this.modelName = modelName;
		this.et = editThread;
		this.operation = operation;
		this.newOptionSetName = newAddedOptionSetName;
	}
	
	public EditOptions(String modelName, EditThread editThread, int operation, String optionSetName, String newOptionName){
		this.modelName = modelName;
		this.et = editThread;
		this.operation = operation;
		this.optionSetName = optionSetName;
		this.newOptionName = newOptionName;
	}
	
	

	public void run() {
		switch (operation) {
		// Updating Option Set
		case 1:
			// Invoking Synchronized Adding method
			et.addOptionSet(modelName, newOptionSetName);
			break;
		case 2:
			// Invoking Synchronized Deleting method
			et.deleteOptionSet(modelName, newOptionSetName);
			break;
		case 3:
			// Invoking Non-Synchronized Adding method
			et.addOptionSetWithoutSync(modelName, newOptionSetName);
			break;
		case 4:
			// Invoking Non-Synchronized Deleting method
			et.deleteOptionSetWithoutSync(modelName, newOptionSetName);
			break;
		
		// Updating Option
		case 5:
			et.addOption(modelName, optionSetName, newOptionName);
			break;
		case 6:
			et.deleteOption(modelName, optionSetName, newOptionName);
			break;
		}

	}
}
