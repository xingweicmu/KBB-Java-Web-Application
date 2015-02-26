package driver;

import server.AutoServerSocket;

public class ServerDriverUnit4 {

	public static void main(String[] args){
		System.out.println("**UNIT 4 SERVER TESTING**");
		System.out.println("========================");
		AutoServerSocket server = new AutoServerSocket(9000);
		server.run();
	}
}
