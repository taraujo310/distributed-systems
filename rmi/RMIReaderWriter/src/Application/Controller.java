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

	public static String read(String path) throws InterruptedException {
		Reader r = new Reader(path, favoring);
		Thread t = new Thread(r);
		t.start();
		t.join();
		return r.getRead();
	}

	public static void write(String path, int toInsert) throws InterruptedException  {
		new Thread(new Writer(path, toInsert, Strategy.FAVORING_WRITERS)).start();
	}

  public static void main(String[] args) {
    try{
      write("arq3", 5);
      read("arq3");
      write("arq1", 6);
      read("arq1");
      write("arq2", 7);
      read("arq2");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
