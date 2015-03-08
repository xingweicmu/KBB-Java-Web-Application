package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

import model.Automobile;

public class DefaultSocketClient implements SocketClientConstants,
		SocketClientInterface {

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Socket socket;
	private String strHost;
	private int iPort;
	private Automobile auto;

	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}

	@Override
	public boolean openConnection() {
		try {
			socket = new Socket(strHost, iPort);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			return false;
		}
		try {
			objectOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());

		} catch (Exception e) {
			if (DEBUG)
				System.err
						.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		return true;
	}

	@Override
	public void handleSession() {
		CarModelOptionIO carModelIO = new CarModelOptionIO();
		SelectCarOption selectCarOption = new SelectCarOption();
		Properties props = null;
		Scanner input = new Scanner(System.in);
		String command = "";
		while (!command.equals("3")) {
			System.out.println("\n-------Welcome------");
			System.out.println("Input your command:");
			System.out.println("1 to create model");
			System.out.println("2 to configure model");
			System.out.println("3 to quit");
			System.out.println("4 to delete model");
			System.out.println("--------------------");
			// Read Command
			command = input.nextLine();
			sendCommand(command);
			switch (command) {
			case "1":
				System.out.println("#Please input the property file name:");
				String filename = input.nextLine();
				// Read property file
				props = carModelIO.readPropertyFile(filename);
				// Send property Object
				sendPropertyObject(props);
				receiveResponse();
				break;
			case "2":
				// List all models
				System.out.println("#Here are all available models:");
				if (!receiveAllModelNames()
						.equals("[Server Info]There is no model to be listed. Please create model first.")) {
					// Choose a model
					System.out.println("#Please choose a model:");
					String modelName = input.nextLine();
					sendSelectedModelName(modelName);
					// Receive selected model
					if (receiveSelectedModel() != null) {
						// Configure the model
						selectCarOption.configureAudo(auto);
					}
					else{
						System.out.println("#Model Not Found! Please enter the right name.");
					}
				}
				break;
				
			case "4":
				// List all models
				System.out.println("#Here are all available models:");
				if (!receiveAllModelNames()
						.equals("[Server Info]There is no model to be listed. Please create model first.")) {
					// Choose a model
					System.out.println("#Please choose a model to delete:");
					String modelName = input.nextLine();
					// Send selected model Name to server
					sendSelectedModelName(modelName);
					// Receive response from the server and check if deleted successfully
					if (receiveIfDeleted().equals("true")) {
						System.out.println("#Model deleted successufully!");
					}
					else{
						System.out.println("#Model Not Found! Please enter the right name.");
					}
				}
				break;

			}

		}
		System.out.println("#Bye.");

	}
	
	public String receiveIfDeleted(){
		String result = "";
		try {
			result = (String) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			if (DEBUG)
				System.out.println("Error in receiving result from "
						+ strHost + ":" + iPort);

		}
		return result;
	}

	private Automobile receiveSelectedModel() {
		try {
			auto = (Automobile) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			if (DEBUG)
				System.out.println("Error in receiving selected model from "
						+ strHost + ":" + iPort);

		}
		return auto;
	}

	private void sendSelectedModelName(String modelName) {

		try {
			// textWriter.println(modelName);
			objectOutputStream.writeObject(modelName);
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error writing model name to " + strHost);
		}
	}

	private String receiveAllModelNames() {
		String response = "";
		try {
			// response = textReader.readLine();
			response = (String) objectInputStream.readObject();
			System.out.println(response);
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Handling session with " + strHost + ":"
						+ iPort);
		}
		return response;
	}

	public void sendPropertyObject(Properties props) {
		try {
			objectOutputStream.writeObject(props);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error writing properties to " + strHost);
		}
	}

	public void receiveResponse() {
		String response = "";
		try {
			// response = textReader.readLine();
			response = (String) objectInputStream.readObject();
			System.out.println(response);
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Handling session with " + strHost + ":"
						+ iPort);
		}
	}

	public void sendCommand(String command) {
		try {
			// textWriter.println(command);
			objectOutputStream.writeObject(command);
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error writing command to " + strHost);
		}

	}

	@Override
	public void closeSession() {
		try {
			// textReader = null;
			// textWriter = null;
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}

	}

	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	public void setPort(int iPort) {
		this.iPort = iPort;
	}

}
