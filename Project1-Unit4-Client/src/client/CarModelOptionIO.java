package client;

import java.io.FileInputStream;
import java.util.Properties;

public class CarModelOptionIO {

	public Properties readPropertyFile(String filename){
		Properties props = new Properties();
		try{
			FileInputStream in = new FileInputStream(filename);
			props.load(in);
		}
		catch(Exception e){
			//e.printStackTrace();
			System.err.println("Could not find the file!");
		}
		return props;
	}
}
