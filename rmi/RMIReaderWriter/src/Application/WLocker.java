package Application;

import java.util.concurrent.Semaphore;

public class WLocker implements RWLockable {
	Resource r;
	int readersCounter = 0, writersCounter = 0;
	Semaphore readersCounterMutex = new Semaphore(1), // protects readersCounter
			writersCounterMutex = new Semaphore(1), // protects writersCounter
			closeToWriters = new Semaphore(1),
			closeToReaders = new Semaphore(1);
	
	public WLocker(String name) {
		r = new Resource(name);
	}

	@Override
	public Resource getResource() {
		return r;
	}

	@Override
	public void requestRead() throws InterruptedException {
		closeToReaders.acquire();
		readersCounterMutex.acquire();
		
		if(++readersCounter == 1) 
			closeToWriters.acquire();
		
		readersCounterMutex.release();
		closeToReaders.release();
	}

	@Override
	public void releaseRead() throws InterruptedException {
		readersCounterMutex.acquire();
		
		if(--readersCounter == 0) 
			closeToWriters.release();
		
		readersCounterMutex.release();
	}

	@Override
	public void requestWriting() throws InterruptedException {
		writersCounterMutex.acquire();
		
		if (++writersCounter == 1)
			closeToReaders.acquire(); // impede a entrada de leitores na região crítica
		
		writersCounterMutex.release();
		closeToWriters.acquire(); // impede escritores simultâneos na região crítica
	}

	@Override
	public void releaseWriting() throws InterruptedException {
		closeToWriters.release();
		writersCounterMutex.acquire();
		
		if (--writersCounter == 0)
			closeToReaders.release();
		
		writersCounterMutex.release();
	}

}
