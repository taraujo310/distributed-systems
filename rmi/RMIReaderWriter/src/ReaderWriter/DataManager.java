package ReaderWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class DataManager {
	static HashMap<String,Locker> association;
    
	public DataManager( ) {
		association = new HashMap<>();
		association.put("arq1", new Locker("arq1"));
		association.put("arq2", new Locker("arq2"));
		association.put("arq3", new Locker("arq3"));
	}
	
	public static String read(String name) throws InterruptedException {
		Resource r = association.get(name).getResource();
		association.get(name).requestRead();
		
		String info = r.get().doRead();
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread " + threadId + " lendo do arquivo: " + info);
		
		association.get(name).releaseRead();
		
		return info;
	}
	
	public static void write(int message) throws InterruptedException, FileNotFoundException {
		writingMutex.acquire();
		
		r.doWrite(message);
		
		writingMutex.release();
	}
}