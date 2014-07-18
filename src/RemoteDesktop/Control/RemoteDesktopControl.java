package RemoteDesktop.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import Client.Control.ClientControl;
import RemoteDesktop.View.MainForm;
import Server.Control.ServerControl;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 
 * @author dangth&dongnh
 */

public class RemoteDesktopControl {
	private MainForm view;
	private ClientControl clientctrl;
	private ServerControl svctrl;

	public RemoteDesktopControl(MainForm view) {
		this.view = view;
		view.setYourIP(getIP());
		view.setPort("9999");
		view.setYourPass(getPass());
		view.refreshPass(new RefreshPass());
		view.setctIP("");
		view.setctPort("");
		view.setctPass("");
		loadConfig();
		view.StartServerListener(new StartServerLisnter());
		view.StopServerListener(new StopServerLisnter());
		view.ConnectListener(new ConnectListener());
		view.SendFileListener(new SendFile());
		view.BrowerFileListener(new Brower());
	}

	// Set savepatch to save file
	private void loadConfig() {
		ArrayList<String> s = Config.ReadFile();
		view.setSavePatch(s.get(0));
	}

	// Load patch Save
	private void saveConfig(ArrayList<String> s) {
		Config.WriteFile(s);
	}

	// get IP
	private String getIP() {
		try {
			return (Inet4Address.getLocalHost().getHostAddress());
			// return (InetAddress.getLocalHost().getHostAddress());
		} catch (Exception ex) {
			return "localhost";
		}
	}

	// get port connect
	private int getPort() {
		Random ran = new Random();
		int k = ran.nextInt(8975) + 1024;
		return k;
	}

	// get password connect
	private String getPass() {
		SecureRandom random = new SecureRandom();// Random password String;
		return new BigInteger(30, random).toString(32);
	}

	// event Refesh Password
	class RefreshPass implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.setYourPass(getPass());
		}

	}

	// event Send file
	class SendFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File f = view.getSentFile();
			if (f == null)
				return;
			clientctrl.sendFile(f);
			view.showMesseger("Sent file " + f.getName());

		}

	}

	// Event Brower file
	class Brower implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Brower");
			File f = view.getSaveFilePatch();
			if (f == null)
				return;
			String filePath = f.getPath();
			ArrayList<String> s = new ArrayList<String>();
			s.add(filePath);
			Config.WriteFile(s);
			view.setSavePatch(filePath);
		}

	}

	// Event Stop Server listen
	class StopServerLisnter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			svctrl.CloseServer();
			view.updateServerStatus();
		}
	}

	// Envent Start Server
	class StartServerLisnter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			svctrl = new ServerControl(view);
			view.updateServerStatus();
		}
	}
	//Event disconnect....
	class DisConnectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clientctrl.disConnectServer();

		}

	}
	//Event Connetion...
	class ConnectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (view.getctIP().equals("")) {//Check set IP
				view.showMesseger("Bạn phải nhập IP");
				return;
			}
			if (view.getctPort() == -1) {//Check Port
				view.showMesseger("Bạn phải nhập port là số");
				return;
			}
			if (view.getctPass().equals("")) {//Check password
				view.showMesseger("Bạn phải nhập password");
				return;
			}
			//Package...
			clientctrl = new ClientControl(view.getctIP(), view.getctPort(),
					view.getctPass(), view.getDimension(), view);
		}
	}

}
