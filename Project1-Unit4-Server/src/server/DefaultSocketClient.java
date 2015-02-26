package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
	private AutoServer autoServer;

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

	public String receiveSelectedModelName() {
		String line = "";
		try {
			// line = textReader.readLine();
			line = (String) objectInputStream.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	public void sendSelectedModel(String selectedName) {
		Automobile auto = autoServer.getSelectedAuto(selectedName);
		try {
			objectOutputStream.writeObject(auto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendAllModelNames() {
		String allNames = autoServer.printAllModelName();
		// textWriter.write(allNames);

		try {
			// textWriter.println(allNames);
			objectOutputStream.writeObject(allNames);
			System.out.println("Model List Sent!");
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error in writing response");
		}

	}

	private void receivePropertyObject() {
		try {
			Properties props = new Properties();
			props = (Properties) objectInputStream.readObject();
			AutoBuilder autoBuilder = new AutoBuilder();
			autoServer = new BuildAuto();
			// AutoServer autoServer = new BuildCarModelOptions();
			autoServer.buildCarFromProperties(props.getProperty("CarModel"),
					BuildCarModelOptions.buildAutoFromProperties(props));
			System.out.println(props.getProperty("CarModel"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String readCommand() {
		String line = "";
		try {
			// line = textReader.readLine();
			line = (String) objectInputStream.readObject();
			if (line != null)
				System.out.println("Received command: " + line);

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
		return line;
	}

	private void setUpInputOutputStream() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
			// textReader = new BufferedReader(new InputStreamReader(
			// socket.getInputStream()));
			// textWriter = new PrintWriter(socket.getOutputStream(), true);

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
				// Receive Property object 
				receivePropertyObject();
				System.out.println("New Model Created!");
				// Send back response
				sendResponse();

				break;
			case "2":
				// Send all model names
				sendAllModelNames();
				// Receive selected model name
				String selectedName = receiveSelectedModelName();
				// Send selected model object
				sendSelectedModel(selectedName);
				System.out.println("Requested model has been sent!");
				break;
			case "3":
				System.out.println("Client exited!");
				break;
			}

		}

	}

	public void sendResponse() {
		try {
			// textWriter.println("[Server Info] New Model Created Successfully");
			objectOutputStream
					.writeObject("[Server Info] New Model Created Successfully");
			// System.out.println("[Server Info] New Model Created Successfully");
		} catch (Exception e) {
			if (DEBUG)
				System.out.println("Error in writing response");
		}
	}

	@Override
	public void closeSession() {
		try {
			// textWriter = null;
			// textReader = null;
			objectInputStream = null;
			objectOutputStream = null;
			socket.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket");
		}

	}
}
