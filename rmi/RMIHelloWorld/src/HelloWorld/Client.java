package HelloWorld;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public Client() {}
	
	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.getRegistry();
			IHello stub = (IHello) registry.lookup("Hello");
			
			String response = stub.say();
			System.out.println("Hello Server says: " + response);
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
