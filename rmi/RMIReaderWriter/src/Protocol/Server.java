package Protocol;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import Application.Controller;

public class Server implements IReaderWriter {
	private Controller controller;

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

	public static void main(String args[]) {
		try {
			Server readerWriterServer = new Server();
			IReaderWriter stub = (IReaderWriter) UnicastRemoteObject.exportObject(readerWriterServer, 0);
			Registry registry = LocateRegistry.createRegistry(1099);

			registry.bind("readerWriter", stub);

			System.out.println("Reader/Writer Server is ready!");
		} catch (Exception e) {
			System.err.println("Server Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
