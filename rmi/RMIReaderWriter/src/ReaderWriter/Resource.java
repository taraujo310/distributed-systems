package ReaderWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Resource {
	static List<Integer> db;
	static int readCount = 0;
	static Semaphore mutex = new Semaphore(1);
    static Semaphore writingMutex = new Semaphore(1);
    
	public Resource() {
		db = new ArrayList<>();
	}
	
	private static void requestRead() throws InterruptedException {
		mutex.acquire();
		readCount++;
		if(readCount == 1)
			writingMutex.acquire();
		mutex.release();
	}
	
	private static void releaseRead()  throws InterruptedException {
		mutex.acquire();
		readCount--;
		if(readCount == 0)
			writingMutex.release();
		mutex.release();
	}
	
	public static String read() throws InterruptedException {
		requestRead();
		
		String info = doRead();
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread " + threadId + " lendo do arquivo: " + info);
		
		releaseRead();
		
		return info;
	}
	
	public static void write(int message) throws InterruptedException {
		writingMutex.acquire();
		
		doWrite(message);
		
		writingMutex.release();
	}
	
	public static void doWrite(int number) throws InterruptedException {
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread "+ threadId +" escrevendo o número " + number + " no arquivo");
		db.add(number);
	}
	
	public static String doRead() throws InterruptedException {
		String line = "";
		
		for(int n : db) {
			line  += n + ", ";
		}
		int endIndex = line.length()-2;
		endIndex = (endIndex < 0) ? 0 : endIndex;
				
		return line.substring(0, endIndex);
	}
}
