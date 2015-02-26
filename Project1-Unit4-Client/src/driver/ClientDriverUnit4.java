package driver;

import client.DefaultSocketClient;

public class ClientDriverUnit4 {

	public static void main(String[] args){
		System.out.println("**UNIT 4 CLIENT TESTING**");
		System.out.println("========================");
		DefaultSocketClient client = new DefaultSocketClient("localhost", 9000);
		client.run();
	}
}
