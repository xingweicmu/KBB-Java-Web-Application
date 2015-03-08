package server;

import java.net.ServerSocket;
import java.util.Date;

import db.CreateSchema;
import db.CreateTable;

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
			System.out.println("Server Established on port " + port);
			databaseSetUp();
			System.out.println("Server Database Setup Successfully");
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

	public void databaseSetUp() {
		// Load the database from file
		CreateSchema createSchema = new CreateSchema();
		createSchema.createDatabaseFromFile("sql/create_database.sql");

		// Load the table from file
		CreateTable createTable = new CreateTable("JSPHDEV");
		createTable.createDatabaseFromFile("sql/auto.sql");
		createTable.createDatabaseFromFile("sql/optionset.sql");
		createTable.createDatabaseFromFile("sql/auto_optionset.sql");
		createTable.createDatabaseFromFile("sql/option.sql");
		createTable.createDatabaseFromFile("sql/optionset_option.sql");
	}
}
