package Client.Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Client.View.ClientView;

/**
*
* @author dangth&dongnh
*/

public class ActionHandler {
	private ClientView v;
	private ObjectOutputStream oos;

	public ActionHandler(ClientView v, ObjectOutputStream oos) {
		super();
		this.v = v;
		this.oos = oos;

		v.MouseWeelListener(new MWL());
		v.MouseListener(new ML());
		v.MouseMotionListener(new MML());
		v.KeyActionListener(new KeyAction());
	}

	class ML implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				oos.writeObject(arg0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			try {
				oos.writeObject(arg0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			try {
				oos.writeObject(arg0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class MML implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			try {
				oos.writeObject(arg0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class MWL implements MouseWheelListener {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			try {
				oos.writeObject(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	class KeyAction implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			try {
				oos.writeObject(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			try {
				oos.writeObject(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		@Override
		public void keyTyped(KeyEvent e) {
		

		}

	}
}
