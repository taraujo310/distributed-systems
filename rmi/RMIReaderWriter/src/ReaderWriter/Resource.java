package ReaderWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Resource {
	static List<Integer> db;
	static int readCount = 0;
	static Semaphore mutex = new Semaphore(1);
        static Semaphore resource = new Semaphore(1);
	public Resource() {
		db = new ArrayList<>();
	}
	
	public static void read() throws InterruptedException {
		mutex.acquire();
		readCount++;
		if(readCount == 1)
			resource.acquire();
		mutex.release();
		
		String info = doRead();
		System.out.println(info);
		
		mutex.acquire();
		readCount--;
		if(readCount == 0)
			resource.acquire();
		mutex.release();
	}
	
	public static void write(int message) throws InterruptedException {
		Thread t = Thread.currentThread();
		resource.acquire();
		
		doWrite(message);
		
		resource.acquire();
	}
	
	public static void doWrite(int number) {
		db.add(number);
	}
	
	public static String doRead() {
		String[] numbers = new String[db.size()];
		int i = 0;
		
		for(int n : db) {
			numbers[i++] = "" + n + "";
		}
		
		return String.join(", ", numbers);
	}
}
