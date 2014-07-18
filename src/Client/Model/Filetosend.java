package Client.Model;

import java.io.Serializable;

/**
*
* @author dangth&dongnh
*/

public class Filetosend implements Serializable{
	private String filename;
	private byte[] b;
	private int k;

	public Filetosend(String filename, byte[] b, int k) {
		super();
		this.filename = filename;
		this.b = b;
		this.k = k;
	}

	public byte[] getB() {
		return b;
	}

	public void setB(byte[] b) {
		this.b = b;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
