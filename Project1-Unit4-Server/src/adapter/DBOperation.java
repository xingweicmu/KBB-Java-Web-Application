package adapter;

import model.Automobile;

public interface DBOperation {
	public void insertAuto(Automobile auto);

	public void deleteAuto(String modelName);

	public void updateAuto(String modelName, String optionSetName,
			String optionName, float price);
}
