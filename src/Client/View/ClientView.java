package Client.View;

import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
*
* @author dangth&dongnh
*/

public class ClientView extends JFrame {
	private JButton Screen;

	public ClientView() {
		Screen = new JButton();
		Screen.setBorder(null);
		this.add(Screen);
		this.setResizable(false);
		this.setTitle("Remote Desktop v1.1");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientView.class.getResource("/Image/icon.png")));
	}

	public void setSize(int w, int h) {
		Screen.setSize(w, h);
	}

	public void setScreen(byte[] pic) {
		ImageIcon img = new ImageIcon(pic);
		this.resize(img.getIconWidth() + 15, img.getIconHeight() + 35);
		Screen.resize(img.getIconWidth(), img.getIconHeight());
	
		Screen.setIcon(img);
	}

	public void KeyActionListener(KeyListener a) {
		Screen.addKeyListener(a);
	}

	public void MouseWeelListener(MouseWheelListener a) {
		Screen.addMouseWheelListener(a);
	}

	public void MouseMotionListener(MouseMotionListener a) {
		Screen.addMouseMotionListener(a);
	}

	public void MouseListener(MouseListener a) {
		Screen.addMouseListener(a);
	}
	public void showMessager(String s)
	{
		JOptionPane.showMessageDialog(this, s);
	}
//    public static void main(String args[]) {
//        new ClientView().show();
//    }
}
