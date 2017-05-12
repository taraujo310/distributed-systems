package Application;

import Application.DataManager.Strategy;

public class Reader implements Runnable {
	DataManager connection;
	public String read;
	private String path;

	public Reader(String path, Strategy favoring) {
		connection = new DataManager(path, favoring);
		this.path = path;
	}

	public void run() {
		try {
			read = connection.read(path);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRead() throws InterruptedException {
		return read;
	}

}
