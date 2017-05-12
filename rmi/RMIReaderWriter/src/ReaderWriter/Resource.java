package ReaderWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		System.out.println("Thread "+ threadId +" escrevendo o número " + number + " no arquivo " + file.getName());
		try{
			FileWriter fileW = new FileWriter (file,true);
      BufferedWriter buffW = new BufferedWriter (fileW);
			buffW.write(Integer.toString(number));
      buffW.newLine();
			buffW.close();
		} catch (Exception e){

		}
	}

	public String doRead() throws InterruptedException {
		String text = "";

		try{
			InputStream is = new FileInputStream(file);
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
      String line = "";

      while ((line = br.readLine()) != null) {
        text += line + ", ";
      }

			br.close();
		} catch (Exception e){

		}

    int endIndex = text.length()-2;
    endIndex = (endIndex < 0) ? 0 : endIndex;

		return text.substring(0, endIndex);
	}
}
