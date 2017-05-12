package Application;

import java.util.concurrent.Semaphore;

public class Locker {
	Resource r;
	int readCount = 0;
	Semaphore mutex = new Semaphore(1);
	Semaphore writingMutex = new Semaphore(1);

	public Locker(String name){
		r = new Resource(name);
	}

	public Resource getResource(){
		return r;
	}

	public void requestRead() throws InterruptedException {
		mutex.acquire();
		readCount++;
		if(readCount == 1)
			writingMutex.acquire();
		mutex.release();
	}

	public void releaseRead()  throws InterruptedException {
		mutex.acquire();
		readCount--;
		if(readCount == 0)
			writingMutex.release();
		mutex.release();
	}

	public void requestWriting() throws InterruptedException {
		writingMutex.acquire();
	}

	public void releaseWriting() throws InterruptedException {
		writingMutex.release();
	}
}
