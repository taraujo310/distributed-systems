package ReaderWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resource {
	private File file;
	
	public Resource(String name) {
		file = new File(name);
	}
	
	public void doWrite(int number) throws InterruptedException, FileNotFoundException {
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread "+ threadId +" escrevendo o número " + number + " no arquivo");
		try{
			FileWriter fileW = new FileWriter (file,true);
            BufferedWriter buffW = new BufferedWriter (fileW);
            buffW.newLine();
			buffW.write(Integer.toString(number));
			buffW.close();
		} catch (Exception e){
			
		}
	}
	
	public String doRead() throws InterruptedException {
		String line = "";
		
		try{
			InputStream is = new FileInputStream(file);
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
			line = br.readLine();
			br.close();
		} catch (Exception e){
			
		}
				
		return line;
	}
}
