package ReaderWriter;

public class Reader implements Runnable {
	Resource connection;
	
	public Reader(Resource resource) {
		connection = resource;
	}
	
	public void run() {
		try {
			Resource.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
