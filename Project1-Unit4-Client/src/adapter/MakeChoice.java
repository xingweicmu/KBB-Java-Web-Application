package adapter;

/**
 * MakeChoice interface.
 * 
 * @author Xing Wei
 *
 */
public interface MakeChoice {

	public void setOptionChoice(String modelName, String optionSetName,
			String optionName);

	public String getOptionChoice(String modelname, String optionSetname);

	public float getOptionChoicePrice(String modelName, String optionSetName);

	public float getTotalPrice(String modelName);
}
