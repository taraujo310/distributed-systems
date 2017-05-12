package Application;

import java.io.FileNotFoundException;

public interface IManager {
	public String read(String path) throws InterruptedException;
	public void write(String path, int message) throws InterruptedException, FileNotFoundException;
}
