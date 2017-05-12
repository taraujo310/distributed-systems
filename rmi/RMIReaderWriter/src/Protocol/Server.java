package Protocol;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import Application.Controller;
import Application.DataManager.Strategy;

public class Server implements IReaderWriter {
	private static Controller controller;

	public Server() {
		controller = new Controller();
	}

	@SuppressWarnings("static-access")
	public String read(String path) throws InterruptedException {
		return controller.read(path);
	}

	@SuppressWarnings("static-access")
	public void write(String path, int toInsert) throws InterruptedException {
		controller.write(path, toInsert);
	}
	
	private static void setController(Controller ctrl) {
		controller = ctrl;
	}

	public static void main(String args[]) {
		try {
			Server readerWriterServer = new Server();
			IReaderWriter stub = (IReaderWriter) UnicastRemoteObject.exportObject(readerWriterServer, 0);
			Registry registry = LocateRegistry.createRegistry(1099);

			registry.bind("readerWriter", stub);
			
			if(!args[0].isEmpty() && args[0].equals("-WR")) {
				setController(new Controller(Strategy.FAVORING_WRITERS));
			}

			System.out.println("Reader/Writer Server is ready!");
		} catch (Exception e) {
			System.err.println("Server Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
