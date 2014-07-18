package Client.Control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Client.View.ClientView;

/**
*
* @author dangth&dongnh
*/

public class ScreenUpdate extends Thread {
	private ClientView v;
	private ObjectInputStream ois;

	public ScreenUpdate(ClientView v, ObjectInputStream ois) {
		super();
		this.v = v;
		this.ois = ois;
	}

	@Override
	public void run() {
		while (ois!=null) {
			try {
				byte[] pic = (byte[]) ois.readObject();
				v.setScreen(pic);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
