package Application;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class DataManager implements IManager {
	static HashMap<String,Locker> association;

	public DataManager(String path) {
		association = new HashMap<>();
		association.put("arq1", new Locker("arq1"));
		association.put("arq2", new Locker("arq2"));
		association.put("arq3", new Locker("arq3"));
	}

	public String read(String name) throws InterruptedException {
		String info = "";
		try {
			Locker l = association.get(name);
			Resource r = l.getResource();
			association.get(name).requestRead();

			info = r.doRead();
			long threadId = Thread.currentThread().getId();
			System.out.println("Thread " + threadId + " lendo do arquivo "+ name +": " + info);

			association.get(name).releaseRead();
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + ": " + e.getMessage());
			System.out.println("Arquivo: " + name);
			System.out.println(e.getCause());
			e.printStackTrace();
		}

		return info;
	}

	public void write(String path, int message) throws InterruptedException, FileNotFoundException {
		try {
			Locker l = association.get(path);
			Resource r = l.getResource();
		    association.get(path).requestWriting();
		
				r.doWrite(message);
		
		    association.get(path).releaseWriting();
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + ": " + e.getMessage());
			System.out.println("Arquivo: " + path);
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	    
	}
}
