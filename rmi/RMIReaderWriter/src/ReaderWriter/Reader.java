package ReaderWriter;

public class Reader implements Runnable {
	DataManager connection;
	public String read;
	
	public Reader(DataManager data) {
		connection = data;
	}
	
	public void run() {
		try {
			read = DataManager.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getRead() throws InterruptedException {
		return read;
	}

}
