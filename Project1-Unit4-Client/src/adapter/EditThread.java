package adapter;

public interface EditThread {

	void addOptionSet(String modelName, String optionSetName);

	void deleteOptionSet(String modelName, String optionSetName);

	void addOptionSetWithoutSync(String modelName, String optionSetName);

	void deleteOptionSetWithoutSync(String modelName, String optionSetName);
	
	void addOption(String modelName, String optionSetName, String optionName);
	
	void deleteOption(String modelName, String optionSetName, String optionName);
}