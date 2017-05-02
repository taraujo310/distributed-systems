package HelloWorld;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements IHello {
	public Server() {}
	
	public String say() {
		return "Hello World!";
	}
	
	public static void main(String args[]) {
		try {
			Server helloServer = new Server();
			IHello stub = (IHello) UnicastRemoteObject.exportObject(helloServer, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Hello", stub);
			
			System.out.print("Hello Server is ready!");
		} catch (Exception e) {
			System.err.println("Server Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
