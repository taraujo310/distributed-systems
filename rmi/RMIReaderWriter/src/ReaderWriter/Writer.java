package ReaderWriter;

public class Writer implements Runnable {
	int messageToWrite;
	Resource connection;
	
	public Writer(int message, Resource resource) {
		messageToWrite = message;
		connection = resource;
	}
	
	public void run() {
		try {
			Resource.write(messageToWrite);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
