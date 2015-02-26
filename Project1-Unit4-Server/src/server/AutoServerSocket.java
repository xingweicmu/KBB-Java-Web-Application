package server;

import java.net.ServerSocket;
import java.util.Date;

public class AutoServerSocket extends Thread {

	private int port;

	public AutoServerSocket(int port) {
		this.port = port;
	}

	public void run() {
		startServer();
	}

	public void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server Established on port "+port);
			while (true) {
				// Initialize a thread to handle connection 
				new DefaultSocketClient(serverSocket.accept()).start();
				Date date = new Date();
				System.out.println("New Connection established at "
						+ date.toString());
			}
		} catch (Exception e) {
			System.out.println("Failed to estabilish connection on port: "
					+ port);
		}
	}
}
