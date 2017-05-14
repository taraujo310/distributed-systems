package Application;

import java.io.FileNotFoundException;

import Application.DataManager.Strategy;

public class Writer implements Runnable {
	int messageToWrite;
	DataManager connection;
	private String path;

	public Writer(String path, int message, Strategy favoring) {
		this.path = path;
		messageToWrite = message;
		connection = new DataManager(path, favoring);
	}

        @Override
	public void run() {
		try {
			connection.write(path, messageToWrite);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e){
                    e.printStackTrace();
                    System.out.println(e + "Path: "+path+" Msg: "+messageToWrite);
                }
               

	}
}
