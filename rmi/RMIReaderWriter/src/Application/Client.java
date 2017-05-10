package Application;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public Client() {}
	
	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.getRegistry();
			IReaderWriter stub = (IReaderWriter) registry.lookup("readerWriter");
			
			String type = args[0].trim();
			
			if (type.equals("writer")) {
				int value = Integer.parseInt(args[1]);
				stub.write(value);
			}
			
			if (type.equals("reader")) {
				String info = stub.read();
				System.out.println(info);
			}
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
