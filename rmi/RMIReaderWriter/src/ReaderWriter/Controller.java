package ReaderWriter;

import java.util.Random;

public class Controller {

	public static void main(String[] args) throws InterruptedException {
		Resource db = new Resource();
		Random rand = new Random();
		int numberOfReaders = rand.nextInt(10)+1, numberOfWriters = rand.nextInt(6)+1;

		//for(int i = 0; i < numberOfWriters; i++) new Thread(new Writer(rand.nextInt(100), db)).start();
		for(int i = 0; i < numberOfReaders; i++) new Thread(new Reader(db)).start();

		System.out.println("cabo");
	}
}
