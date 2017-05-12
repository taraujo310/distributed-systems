package Application;

import java.io.FileNotFoundException;

public class Teste {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException{
		Resource r = new Resource("teste.txt");
		System.out.println(r.doRead());
		r.doWrite(45);
		r.doWrite(38);
		System.out.println(r.doRead());
		System.out.println(r.doRead());
	}
}
