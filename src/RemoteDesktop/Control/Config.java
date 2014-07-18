package RemoteDesktop.Control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
*
* @author dangth&dongnh
*/

public class Config {

	/**
	 * @param args
	 */
	public static ArrayList<String> ReadFile() {
		ArrayList<String> config = new ArrayList<String>();
		try {
			File f = new File("config.txt");
			if (!f.exists())
				f.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder();
			String line;
			line = br.readLine();
			while (line != null) {
				config.add(line);
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			String everything = sb.toString();
			br.close();
			return config;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return config;
		}
	}

	public static void WriteFile(ArrayList<String> s) {
		try {
			File f = new File("config.txt");
			if (!f.exists())  f.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (String line : s)
			{bw.write(line);
			bw.newLine();
			System.out.println("Write:"+line);
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}


}
