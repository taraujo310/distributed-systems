package Application;

import java.util.concurrent.Semaphore;

public class NLocker implements RWLockable {
	Resource r;
	int readCount = 0;
	Semaphore readersFlow = new Semaphore(1); // Fluxo de leitores
	Semaphore writersFlow = new Semaphore(1); // Fluxo de escritores
	Semaphore protector = new Semaphore(1); // Protetor para decremento de leitores em bloco e liberação para fluxo de escritores

	public NLocker(String name){
		r = new Resource(name);
	}

	public Resource getResource(){
		return r;
	}

	public void requestRead() throws InterruptedException {
		readersFlow.acquire();
		readCount++;
		if(readCount == 1)
			writersFlow.acquire();
		readersFlow.release();
	}

	public void releaseRead()  throws InterruptedException {
		protector.acquire();
		readCount--;
		if(readCount == 0)
			writersFlow.release();
		protector.release();
	}

	public void requestWriting() throws InterruptedException {
		readersFlow.acquire();
		writersFlow.acquire();
	}

	public void releaseWriting() throws InterruptedException {
		writersFlow.release();
		readersFlow.release();
	}
}
