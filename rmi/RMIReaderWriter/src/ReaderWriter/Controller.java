package ReaderWriter;

public class Controller {
	DataManager data;
	
	public Controller(String file) {
		data = new DataManager(file);
	}
	
	public String read() throws InterruptedException {
		Reader r = new Reader(data);
		Thread t = new Thread(r);
		t.start();
		t.join();
		return r.getRead();
	}
	
	public void write(int toInsert) {
		new Thread(new Writer(toInsert, data)).start();
	}
}
