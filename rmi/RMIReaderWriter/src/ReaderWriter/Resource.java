package ReaderWriter;

import java.util.ArrayList;
import java.util.List;

public class Resource {
	static List<Integer> db;
	static int resource = 1;
	static int mutex=1;
	static int readCount = 0;
	
	public Resource() {
		db = new ArrayList<Integer>();
	}
	
	public static void read() throws InterruptedException {
		down(mutex);
		readCount++;
		if(readCount == 1)
			down(resource);
		up(mutex);
		
		String info = doRead();
		System.out.println(info);
		
		down(mutex);
		readCount--;
		if(readCount == 0)
			up(resource);
		up(mutex);
	}
	
	public static void write(int message) throws InterruptedException {
		Thread t = Thread.currentThread();
		down(resource);
		
		doWrite(message);
		
		up(resource);
	}
	
	private static void down(int semaphore) throws InterruptedException {
		Thread t = Thread.currentThread();
		if (semaphore == 0){
			synchronized(t) {
				t.wait();
			}
		} else 
			semaphore--;
	}
	
	private static void up(int semaphore) throws InterruptedException {
		Thread t = Thread.currentThread();
		if (readCount > 0){
			synchronized(t) {
				t.notify();
			}
		}else
			semaphore++;
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
