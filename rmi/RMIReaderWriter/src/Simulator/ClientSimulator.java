package Simulator;

import Protocol.Client;
import Protocol.Client.Job;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientSimulator {

    public static void main(String[] args) {
        Random rand = new Random();
        int numberOfReaders = rand.nextInt(10) + 1, numberOfWriters = rand.nextInt(6) + 1;
        List<Thread> threads = new ArrayList<>();
        Client client;
        String[] files = new String[] { "arq1", "arq2", "arq3" };
        
        int major = (numberOfReaders > numberOfWriters) ? numberOfReaders : numberOfWriters;
        String file;
        
        for(int i = 0; i < major; i++) {
        	file = files[rand.nextInt(3)];
        	client = new Client(Job.READ, file);
        	threads.add(new Thread(client));
        	
        	file = files[rand.nextInt(3)];
        	client = new Client(Job.WRITE, file, rand.nextInt(100));
        	threads.add(new Thread(client));
        }

        for (Thread thread : threads){
        	thread.start();
        }
        
        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("cabo");
    }

}
