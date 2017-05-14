package Application;

import Application.DataManager.Strategy;

public class Controller {
	static Strategy favoring;
	
	public Controller() {
		this(Strategy.FAVORING_READERS);
	}
	
	public Controller(Strategy choice) {
		favoring = choice;
	}

	public String read(String path) throws InterruptedException {
		Reader r = new Reader(path, favoring);
		Thread t = new Thread(r);
		t.start();
		t.join();
		return r.getRead();
	}

	public void write(String path, int toInsert) throws InterruptedException  {
		new Thread(new Writer(path, toInsert, Strategy.FAVORING_WRITERS)).start();
	}
}
