package ReaderWriter;

public class Controller {
	Resource db;
	
	public Controller() {
		db = new Resource();
	}
	
	public String read() throws InterruptedException {
		Reader r = new Reader(db);
		Thread t = new Thread(r);
		t.start();
		t.join();
		return r.getRead();
	}
	
	public void write(int toInsert) {
		new Thread(new Writer(toInsert, db)).start();
	}
}
