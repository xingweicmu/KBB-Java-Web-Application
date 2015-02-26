package adapter;

import model.Automobile;

public interface AutoServer {

	public void buildCarFromProperties(String modelName, Automobile auto);

	public String printAllModelName();

	public Automobile getSelectedAuto(String modelName);

}
