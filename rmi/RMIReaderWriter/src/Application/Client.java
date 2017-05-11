package Application;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable{

    private IReaderWriter stub;
    private Job job;
    private int value;
    
    public static enum Job{
        READ,
        WRITE
    }
    public Client(Job job) {
        this(job,0);
    }
    public Client(Job job, int value) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            stub = (IReaderWriter) registry.lookup("readerWriter");
            this.job = job;
            this.value = value;
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void write(int value) {
        try {
            stub.write(value);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void read() {
        String info = null;
        try {
            info = stub.read();
        } catch (RemoteException | InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(info);
    }

    @Override
    public void run() {
        switch(job){
            case READ:
                read();
                break;
            case WRITE:
                write(value);
                break;
        }
    }
}
