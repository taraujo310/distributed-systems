package ReaderWriter;

import java.util.concurrent.Semaphore;

public class Locker {
	static Resource r;
	static int readCount = 0;
	static Semaphore mutex = new Semaphore(1);
    static Semaphore writingMutex = new Semaphore(1);
    
    public Locker(String name){
    	r = new Resource(name);
    }
    
    public Resource getResource(){
    	return r;
    }
    
    @SuppressWarnings("unused")
	public static void requestRead() throws InterruptedException {
		mutex.acquire();
		readCount++;
		if(readCount == 1)
			writingMutex.acquire();
		mutex.release();
	}
	
	@SuppressWarnings("unused")
	public static void releaseRead()  throws InterruptedException {
		mutex.acquire();
		readCount--;
		if(readCount == 0)
			writingMutex.release();
		mutex.release();
	}
	
}
