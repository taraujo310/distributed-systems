package ReaderWriter;

public class Reader implements Runnable {
	Resource connection;
	public String read;
	
	public Reader(Resource resource) {
		connection = resource;
	}
	
	public void run() {
		try {
			read = Resource.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getRead() throws InterruptedException {
		return read;
	}

}
