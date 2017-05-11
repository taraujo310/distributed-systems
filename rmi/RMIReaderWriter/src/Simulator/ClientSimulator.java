package Simulator;

import Application.Client;
import Application.Client.Job;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSimulator {

    public static void main(String[] args) {
        Random rand = new Random();
        int numberOfReaders = rand.nextInt(10) + 1, numberOfWriters = rand.nextInt(6) + 1;
        List<Thread> threads = new ArrayList<>();
        Client client;
        
        for (int i = 0; i < numberOfWriters; i++) {
            client = new Client(Job.WRITE, rand.nextInt(100));
            threads.add(new Thread(client));
        }
        
        for (int i = 0; i < numberOfReaders; i++) {
            client = new Client(Job.READ);
            threads.add(new Thread(client));
        }
        
        for (Thread thread : threads){
            thread.start();
        }
           
        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("cabo");
    }

}
