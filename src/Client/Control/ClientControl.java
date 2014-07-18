package Client.Control;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.View.ClientView;
import RemoteDesktop.View.MainForm;

/**
*
* @author dangth&dongnh
*/

public class ClientControl {
	private Socket socket;
	private String host = "localhost";
	private int port = 9999;
	private String pass;
	private ClientView view;
	private Dimension dimension;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private MainForm mainview;

	public ClientControl(String host, int port,String pass, Dimension dimension,MainForm mainview) {
		this.dimension = dimension;
		this.host = host;
		this.port = port;
		this.pass=pass;
		this.mainview=mainview;
		connectServer();
	}

	private void connectServer() {
		try {
			socket = new Socket(host, port);
			view = new ClientView();
			view.setVisible(true);
			updateScreen();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mainview.showMesseger("Không tìm thấy host");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mainview.showMesseger("Không tìm thấy host");
		}
	}
	public void disConnectServer()
	{
		try {
			socket.close();
                        ois.close();
                        oos.close();
			mainview.updateClientStatus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendFile(File f) {
		SendFile sf = new SendFile(oos, f.getPath(),f.getName());
		sf.start();
	}

	private void updateScreen() {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("FR CLIENT:sending pass");
			oos.writeObject(pass);
			System.out.println("FR CLIENT:sent pass");
			String result=(String) ois.readObject();
			if(result.equals("OK"))
			{
			mainview.updateClientStatus();	
			oos.writeObject(dimension);
			ActionHandler ac = new ActionHandler(view, oos);
			ScreenUpdate su = new ScreenUpdate(view, ois);
			su.start();}
			else
			{
				view.showMessager("Bạn nhập sai pass hoặc Server không sẵn sàng");
				view.setVisible(false);
				socket.close();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			view.showMessager("Bạn nhập sai pass hoặc Server không sẵn sàng");
			view.setVisible(false);
		}

	}

}
