import com.sun.org.apache.bcel.internal.classfile.Unknown;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by Minsungkim on 2017-03-15.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        //image ip,port
        int imagePort = 5000;
        String imageGroup = "225.4.5.6";

        MulticastSocket imageS = new MulticastSocket(imagePort);
        imageS.joinGroup(InetAddress.getByName(imageGroup));

        //pointer ip.port
        int pointerPort = 5001;
        String pointerGroup = "225.4.5.7";

        MulticastSocket pointerS = new MulticastSocket(pointerPort);
        pointerS.joinGroup(InetAddress.getByName(pointerGroup));

        image_window iw = new image_window(1280, 768);

        //Image Thread
        ImageThread imgThread = new ImageThread(imageS, iw, 1280, 768);
        Thread imageThread = new Thread(imgThread);
        imageThread.setDaemon(true);
        imageThread.start();

        //Pointer Thread
        PointerThread pThread = new PointerThread(pointerS, iw);
        Thread pointerThread = new Thread(pThread);
        pointerThread.setDaemon(true);
        pointerThread.start();

        //s.leaveGroup(InetAddress.getByName(group));
        //s.close();

        System.out.println("Exit!");
    }
}
