package Application;

import java.util.concurrent.Semaphore;

public class NLocker implements RWLockable {
	Resource r;
	int readersCounter = 0;
	Semaphore queueMutex = new Semaphore(1); // Fila de threads ou a ordem delas
	Semaphore readersCounterMutex = new Semaphore(1); // protege o readersCounter
	Semaphore resourceMutex = new Semaphore(1); // Protege o recurso

	public NLocker(String name){
		r = new Resource(name);
	}

	public Resource getResource(){
		return r;
	}

	public void requestRead() throws InterruptedException {
		queueMutex.acquire();

    readersCounterMutex.acquire();
		if(++readersCounter == 1)
			resourceMutex.acquire();

    queueMutex.release();
    readersCounterMutex.release();
	}

	public void releaseRead()  throws InterruptedException {
		readersCounterMutex.acquire();

		if(--readersCounter == 0)
			resourceMutex.release();

    readersCounterMutex.release();
	}

	public void requestWriting() throws InterruptedException {
		queueMutex.acquire();
		resourceMutex.acquire();
    queueMutex.release();
	}

	public void releaseWriting() throws InterruptedException {
		resourceMutex.release();
	}
}
