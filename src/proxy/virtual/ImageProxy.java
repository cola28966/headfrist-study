package proxy.virtual;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxy implements Icon {

    ImageIcon imageIcon;

    URL imageURL;

    Thread retrievalThread;

    boolean retrieving = false;

    public ImageProxy(URL imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int getIconWidth() {
        if(imageIcon  != null) {
            return imageIcon.getIconWidth();
        } else {
            return 800;
        }
    }

    @Override
    public int getIconHeight() {
        if(imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
            return 600;
        }
    }

    @Override
    public void paintIcon(final Component c, Graphics g , int x, int y) {
        if(imageIcon != null) {
            imageIcon.paintIcon(c,g,x,y);
        } else {
            g.drawString("Loading CD cover, please wait...", x +300,y+190);
            if(!retrieving) {
                retrieving = true;//保证只有一个线程调用 paint
                retrievalThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            imageIcon = new ImageIcon(imageURL, "CD Cover");
                            c.repaint();//重新调用paintIcon方法
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                retrievalThread.start();
            }
        }
    }
}
