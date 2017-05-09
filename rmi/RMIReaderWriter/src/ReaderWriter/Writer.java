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
			System.out.println("Escrevendo o número " + messageToWrite + " no arquivo");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
