package Application;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class DataManager {
	static HashMap<String, RWLockable> association;
	
	public static enum Strategy {
        FAVORING_READERS,
        FAVORING_WRITERS
    }

	public DataManager(String path, Strategy favoring) {
		association = new HashMap<>();
		association.put("arq1", LockerFactory(favoring, "arq1"));
		association.put("arq2", LockerFactory(favoring, "arq2"));
		association.put("arq3", LockerFactory(favoring, "arq3"));
	}

	public String read(String name) throws InterruptedException {
		String info = "";
		Resource r = association.get(name).getResource();
		association.get(name).requestRead();

		info = r.doRead();
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread " + threadId + " lendo do arquivo " + name + ": " + info);

		association.get(name).releaseRead();

		return info;
	}

	public void write(String path, int message) throws InterruptedException, FileNotFoundException {
		Resource r = association.get(path).getResource();
		association.get(path).requestWriting();

		r.doWrite(message);

		association.get(path).releaseWriting();
	}
	
	private RWLockable LockerFactory(Strategy choice, String filepath) {
		switch(choice) {
		case FAVORING_READERS:
			return new RLocker(filepath);
		case FAVORING_WRITERS:
			return new WLocker(filepath);
		default: 
			return new RLocker(filepath);
		}
	}
}
