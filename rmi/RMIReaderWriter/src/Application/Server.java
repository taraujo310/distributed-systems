package Application;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import ReaderWriter.Controller;

public class Server implements IReaderWriter {
	private Controller controller;
	
	public Server() {
		controller = new Controller();
	}
	
	public String read() throws InterruptedException {
		return controller.read();
	}
	
	public void write(int toInsert) {
		controller.write(toInsert);
	}
	
	public static void main(String args[]) {
		try {
			Server readerWriterServer = new Server();
			IReaderWriter stub = (IReaderWriter) UnicastRemoteObject.exportObject(readerWriterServer, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("readerWriter", stub);
			
			System.out.println("Reader/Writer Server is ready!");
		} catch (Exception e) {
			System.err.println("Server Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
