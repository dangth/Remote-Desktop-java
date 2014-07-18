package Server.Control;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

/**
*
* @author dangth&dongnh
* capture screen and get Screen..
*/

public class GetScreen extends Thread {

    private ObjectOutputStream oos;
    private Dimension dimension;
    // get Screen:
    private Toolkit toolkit;
    private Dimension screensize;
    private Robot robot;
    private ServerControl sc;

    public GetScreen(ObjectOutputStream oos, Dimension dimension, ServerControl sc) {
        this.oos = oos;
        this.dimension = dimension;
        this.sc = sc;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.sleep(30);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            toolkit = Toolkit.getDefaultToolkit();
            screensize = toolkit.getScreenSize();
            try {
                robot = new Robot();
                BufferedImage screenImage = robot.createScreenCapture(new Rectangle(
                        screensize));
                if (dimension.getWidth() > 0 && dimension.getHeight() > 0) {
                    screenImage = Resize(screenImage, dimension.getWidth(), dimension.getHeight());
                }
                //System.out.println("Next size:"+dimension.getWidth()+"-"+ dimension.getHeight());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(screenImage, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                oos.writeObject(imageInByte);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                sc.DisconnectAllClients();
                break;
            }
        }
    }

    public BufferedImage Resize(BufferedImage bf, double wn, double hn) {
        int w = bf.getWidth();
        int h = bf.getHeight();
        double ww = (double) (wn / w);
        double hh = (double) (hn / h);
        AffineTransform tx = new AffineTransform();
        tx.scale(ww, hh);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        bf = op.filter(bf, null);
        return bf;
    }

}
