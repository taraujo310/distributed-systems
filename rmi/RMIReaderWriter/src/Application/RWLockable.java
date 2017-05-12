package Application;

public interface RWLockable {
	public Resource getResource();
	public void requestRead() throws InterruptedException;
	public void releaseRead()  throws InterruptedException;
	public void requestWriting() throws InterruptedException;
	public void releaseWriting() throws InterruptedException;
}
