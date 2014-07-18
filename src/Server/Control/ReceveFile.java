package Server.Control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import Client.Model.Filetosend;

/**
*
* @author dangth&dongnh
*/

public class ReceveFile{
	private OutputStream os;

	public ReceveFile(String FilePatch, String FileName) {
		try {
			File f = new File(FilePatch + "/" + FileName);
			if (!f.exists())
				f.createNewFile();
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Write(Filetosend fts) {
		System.out.println("write:"+fts.getK());
		if (fts.getK() == -1) {
			try {
				System.out.println("Write File Successfull!");
				os.flush();
				os.close();
				return;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			System.out.println("Writeting!");
			os.write(fts.getB(), 0, fts.getK());
			os.flush();
			os.close();
		} catch (IOException ex) {
			System.out.println("Error File Too Large! ");
			ex.printStackTrace();
		}
	}
	
}
