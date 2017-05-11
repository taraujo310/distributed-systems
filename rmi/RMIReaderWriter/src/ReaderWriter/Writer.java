package ReaderWriter;

import java.io.FileNotFoundException;

public class Writer implements Runnable {
	int messageToWrite;
	DataManager connection;
	
	public Writer(int message, DataManager data) {
		messageToWrite = message;
		connection = data;
	}
	
	public void run() {
		try {
			DataManager.write(messageToWrite);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
