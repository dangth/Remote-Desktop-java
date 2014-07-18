package Client.Control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;

import Client.Model.Filetosend;

/**
*
* @author dangth&dongnh
*/

public class SendFile extends Thread {
	private ObjectOutputStream oos;
	private String filepatch;
	private String filename;

	public SendFile(ObjectOutputStream oos, String filepatch, String filename) {
		super();
		this.oos = oos;
		this.filepatch = filepatch;
		this.filename = filename;
	}

	@Override
	public void run() {

		try {
			File f = new File(filepatch);
			FileInputStream fi = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fi);
			byte[] temp = new byte[(int) f.length()];
			int k = 0;
			while (true) {
				k = bis.read(temp);
				if(k==-1) break;
				System.out.println("Sending"+k);
				Filetosend fts = new Filetosend(filename, temp, k);
				oos.writeObject(fts);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
