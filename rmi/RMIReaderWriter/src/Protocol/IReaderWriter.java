package Protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IReaderWriter extends Remote {
	public String read(String path) throws RemoteException, InterruptedException;
	public void write(String path, int toInsert) throws RemoteException, InterruptedException;
}
