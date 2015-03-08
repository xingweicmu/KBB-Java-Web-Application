package driver;

import client.DefaultSocketClient;

public class ClientDriverUnit6 {

	public static void main(String[] args){
		System.out.println("**UNIT 6 CLIENT TESTING**");
		System.out.println("========================");
		DefaultSocketClient client = new DefaultSocketClient("localhost", 9000);
		client.run();
	}
}
