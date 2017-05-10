package Application;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IReaderWriter extends Remote {
	public String read() throws RemoteException, InterruptedException;
	public void write(int toInsert) throws RemoteException;
}
