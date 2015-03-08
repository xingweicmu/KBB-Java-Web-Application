package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import model.Automobile;
import adapter.AutoServer;
import adapter.BuildAuto;
import util.AutoBuilder;

public class DefaultSocketClient extends Thread implements
		SocketClientInterface, SocketClientConstants {

	private Socket socket = null;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private BuildAuto autoServer;

	public DefaultSocketClient(Socket socket) {
		super("AutoServerSocketHandler");
		this.socket = socket;
		autoServer = new BuildAuto();
	}

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}

	}

	/**
	 * Receive selected model name from the client.
	 */
	public String receiveSelectedModelName() {
		String line = "";
		try {
			line = (String) objectInputStream.readObject();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
		return line;
	}

	/**
	 * Send the selected model back to client.
	 * 
	 * @param selectedName
	 *            the name of selected model
	 */
	public void sendSelectedModel(String selectedName) {
		Automobile auto = autoServer.getSelectedAuto(selectedName);
		try {

			objectOutputStream.writeObject(auto);
			if (auto != null) {
				System.out.println("Requested model has been sent!");
			} else {
				System.out.println("No model found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send all model names to client.
	 */
	public boolean sendAllModelNames() {
		String allNames = autoServer.printAllModelName();
		try {
			// System.out.println(allNames);
			if (allNames != "") {
				objectOutputStream.writeObject(allNames);
				System.out.println("Model List Sent!");
				return true;
			} else {
				objectOutputStream
						.writeObject("[Server Info]There is no model to be listed. Please create model first.");
				System.out.println("No model to be sent!");
				return false;
			}
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error in writing response");
		}
		return false;

	}

	/**
	 * Receive Property Object from the client.
	 */
	private void receivePropertyObject() {
		try {
			Properties props = new Properties();
			props = (Properties) objectInputStream.readObject();
			if (props.getProperty("CarModel") != null) {
				// AutoBuilder autoBuilder = new AutoBuilder();
				autoServer = new BuildAuto();
				Automobile auto = BuildCarModelOptions
						.buildAutoFromProperties(props);
				autoServer.buildCarFromProperties(
						props.getProperty("CarModel"), auto);
				System.out.println(props.getProperty("CarModel")
						+ " Created Successfully!");
				sendResponse("[Server Info] New Model Created Successfully");
			} else {
				sendResponse("[Server Info] Fail to create model!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read command from client.
	 * 
	 * @return String the command
	 */
	private String readCommand() {
		String line = "";
		try {
			line = (String) objectInputStream.readObject();
			if (line != null)
				System.out.println("Received command: " + line);

		} catch (Exception e) {
			return null;
		}
		return line;
	}

	/**
	 * Set up input and output stream
	 */
	private void setUpInputOutputStream() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean openConnection() {
		setUpInputOutputStream();
		return true;
	}

	@Override
	public void handleSession() {
		while (true) {
			// Read Command
			String command = readCommand();
			if (command == null)
				break;
			switch (command) {
			case "1":
				// Receive Property object and send back response
				receivePropertyObject();

				break;
			case "2":
				// Send all model names
				if (sendAllModelNames()) {
					// Receive selected model name
					String selectedName = receiveSelectedModelName();
					// Send selected model object
					sendSelectedModel(selectedName);
				}
				break;
			case "4":
				// Send all model names
				if (sendAllModelNames()) {
					// Receive selected model name
					String selectedName = receiveSelectedModelName();
					// Delete selected model object and send back response
					deleteSelectedModel(selectedName);
				}
				break;
			case "3":
				System.out.println("Client exited!");
				break;
			}

		}

	}
	
	/**
	 * Delete the model with the model name and send response back.
	 * @param modelName
	 */
	public void deleteSelectedModel(String modelName){
		
		try {
			if(autoServer.removeAuto(modelName)){
				objectOutputStream.writeObject("true");
			}else
			{
				objectOutputStream.writeObject("false");
			}

		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error in writing response");
		}
	}

	/**
	 * Send response to client.
	 */
	public void sendResponse(String response) {
		try {

			objectOutputStream.writeObject(response);

		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error in writing response");
		}
	}

	@Override
	public void closeSession() {
		try {
			objectInputStream = null;
			objectOutputStream = null;
			socket.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket");
		}

	}
}
