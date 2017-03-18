import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by Minsungkim on 2017-03-16.
 */
public class Main {
    public static SendThread sendThread;
    public static CaptureThread captureThread;
    public static PointerThread pointerThread;

    public Main() {
        //Image group
        int imagePort = 5000;
        String imageGroup = "225.4.5.6";
        MulticastSocket imageS = null;

        //Poionter Group
        int pointerPort = 5001;
        String pointerGroup = "225.4.5.7";
        MulticastSocket pointerS = null;
        try {
            imageS = new MulticastSocket();
            pointerS = new MulticastSocket();
        } catch (IOException e) {
            System.out.println("멀티캐스트 소켓 초기화 실패");
        }

        Communication imageSock = new Communication(imageS, imageGroup, imagePort);

        try {
            sendThread = new SendThread(imageSock);
        } catch (IOException e) {
            e.printStackTrace();
        }

        captureThread = new CaptureThread();

        sendThread.start();
        captureThread.start();

        Communication pointerSock = new Communication(pointerS, pointerGroup, pointerPort);

        pointerThread = new PointerThread(pointerSock);
        pointerThread.start();

        try {
            captureThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Start");
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
