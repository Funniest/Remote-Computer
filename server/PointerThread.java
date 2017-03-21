import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class PointerThread extends Thread{
    private MulticastSocket socket;
    private Communication pointerSock;

    private byte[] outBuf;
    private image_window iw;

    private int x,y;

    private String group;
    private int port;

    public PointerThread(MulticastSocket socket, image_window iw, String group, int port){
        this.socket = socket;
        this.iw = iw;
        this.outBuf = new byte[4];

        this.group = group;
        this.port = port;

        pointerSock = new Communication(socket, group, port);
    }

    private void putInt(int value, byte[] array, int offset) {
        array[offset]   = (byte)(0xff & (value >> 8));
        array[offset+1] = (byte)(0xff & value);
    }

    public void setRatio(int x, int y){
        int wdith = iw.getWidth();
        int height = iw.getHeight();

        if(height == 768)
            y = (int)(y * 1.5);
        else if(height == 1050)
            y = (int)(y * 1.15);
        else if(height == 1080)
            y = (int)(y * 1.1);

        if(wdith == 1280)
            x = (int)(x * 1.5);
        else if(wdith == 1360)
            x = (int)(x * 1.4);
        else if(wdith == 1400)
            x = (int)(x * 1.4);
        else if(wdith == 1680)
            x = (int)(x * 1.05);
        else if(wdith == 1920)
            x = (int)(x * 1);

        this.x = x;
        this.y = y;
    }

    @Override
    public void run(){
        while(true) {
            setRatio(iw.getMouse_x(), iw.getMouse_y());

            putInt(x, outBuf, 0);
            putInt(y, outBuf, 2);

            if(iw.getMouseStatus()) {
                System.out.println("x : " + x + ", y : " + y);
                pointerSock.sendPointer(outBuf, group, port);
            }

            try{
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
