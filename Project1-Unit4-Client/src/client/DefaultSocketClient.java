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
			// textReader = new BufferedReader(new
			// InputStreamReader(socket.getInputStream()));
			// textWriter = new PrintWriter(socket.getOutputStream(), true);
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
				receiveAllModelNames();
				// Choose a model
				System.out.println("#Please choose a model:");
				String modelName = input.nextLine();
				sendSelectedModelName(modelName);
				// Receive selected model
				receiveSelectedModel();
				// Configure the model
				selectCarOption.configureAudo(auto);
				break;

			}

		}
		System.out.println("#Bye.");

	}

	private void receiveSelectedModel() {
		try {
			auto = (Automobile) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			if (DEBUG)
				System.out.println("Error in receiving selected model from "
						+ strHost + ":" + iPort);

		}

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

	private void receiveAllModelNames() {
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
