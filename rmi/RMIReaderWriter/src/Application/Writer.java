package Application;

import java.io.FileNotFoundException;

public class Writer implements Runnable {
	int messageToWrite;
	DataManager connection;
  private String path;

	public Writer(String path, int message) {
    this.path = path;
		messageToWrite = message;
		connection = new DataManager(path);
	}

	public void run() {
		try {
			connection.write(path, messageToWrite);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
