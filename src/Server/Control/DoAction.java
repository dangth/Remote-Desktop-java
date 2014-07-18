package Server.Control;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;

import Client.Model.Filetosend;
import RemoteDesktop.View.MainForm;

/**
*
* @author dangth&dongnh
*/

public class DoAction extends Thread {

    private ObjectInputStream ois;
    private Dimension dimension;
    private Robot robot;
    private Toolkit toolkit;
    private Dimension screensize;
    private ReceveFile recevefile;
    private MainForm view;

    public DoAction(ObjectInputStream ois, Dimension dimension, MainForm view) {
        this.ois = ois;
        this.dimension = dimension;
        this.view = view;
        toolkit = Toolkit.getDefaultToolkit();
        screensize = toolkit.getScreenSize();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Point Mousepoint(int x, int y) {
        if (dimension.getWidth() <= 0) {
            return new Point(x, y);
        }
        double tx = (double) x / dimension.getWidth();
        double ty = (double) y / dimension.getHeight();
        Point mouse = new Point((int) (tx * screensize.getWidth()),
                (int) (ty * screensize.getHeight()));
        return mouse;
    }

    @Override
    public void run() {
        boolean isWritingFile = false;
        while (true) {
            try {
                Object o = ois.readObject();
                if (o instanceof Filetosend) {
                    Filetosend fts = (Filetosend) o;
                    recevefile = new ReceveFile(view.getSavePatch(),
                            fts.getFilename());

                    recevefile.Write(fts);
                    System.out.println("File saved");
                }
                if (o instanceof KeyEvent) {
                    isWritingFile = false;
                    KeyEvent k = (KeyEvent) o;
                    switch (k.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            robot.keyPress(k.getKeyCode());
                            break;
                        case KeyEvent.KEY_RELEASED:
                            robot.keyRelease(k.getKeyCode());
                            break;
                    }
                }
                if (o instanceof MouseEvent) {
                    isWritingFile = false;
                    MouseEvent m = (MouseEvent) o;
                    int x = m.getX();
                    int y = m.getY();
                    int button = m.getButton();
                    int buttonMask = 0;
                    // Move Mouse:
                    Point mousepoint = Mousepoint(x, y);
                    robot.mouseMove(mousepoint.x, mousepoint.y);
                    // Check Button:
                    switch (button) {
                        case MouseEvent.BUTTON1:
                            buttonMask = MouseEvent.BUTTON1_MASK;
                            break;
                        case MouseEvent.BUTTON2:
                            buttonMask = MouseEvent.BUTTON2_MASK;
                            break;
                        case MouseEvent.BUTTON3:
                            buttonMask = MouseEvent.BUTTON3_MASK;
                            break;
                    }
                    // do mouse action:
                    switch (m.getID()) {
                        case MouseEvent.MOUSE_PRESSED:
                            robot.mousePress(buttonMask);
                            break;
                        case MouseEvent.MOUSE_RELEASED:
                            robot.mouseRelease(buttonMask);
                            break;
                        case MouseEvent.MOUSE_WHEEL:
                            robot.mouseWheel(buttonMask);
                            break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
