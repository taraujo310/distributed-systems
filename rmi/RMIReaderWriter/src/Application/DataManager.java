package Application;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class DataManager {
	static HashMap<String, RWLockable> association;
  Semaphore mapMutex = new Semaphore(1);

	public static enum Strategy {
        FAVORING_READERS,
        FAVORING_WRITERS,
        SELFISH
    }

	public DataManager(String path, Strategy favoring) {
		association = new HashMap<>();
		association.put("arq1", LockerFactory(favoring, "arq1"));
		association.put("arq2", LockerFactory(favoring, "arq2"));
		association.put("arq3", LockerFactory(favoring, "arq3"));
	}

	public String read(String name) throws InterruptedException {
		String info = "";

    mapMutex.acquire();
		RWLockable lock = association.get(name);
    Resource r = lock.getResource();
    mapMutex.release();

		lock.requestRead();

		info = r.doRead();
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread " + threadId + " lendo do arquivo " + name + ": " + info);

		lock.releaseRead();

		return info;
	}

	public void write(String path, int message) throws InterruptedException, FileNotFoundException {
    mapMutex.acquire();
    RWLockable lock = association.get(path);
		Resource r = lock.getResource();
    mapMutex.release();

		lock.requestWriting();

		r.doWrite(message);

		lock.releaseWriting();
	}

	private RWLockable LockerFactory(Strategy choice, String filepath) {
		switch(choice) {
		case FAVORING_READERS:
			return new RLocker(filepath);
		case FAVORING_WRITERS:
			return new WLocker(filepath);
		case SELFISH:
			return new NLocker(filepath);
		default:
			return new RLocker(filepath);
		}
	}
}
