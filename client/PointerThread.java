import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * Created by Minsungkim on 2017-03-18.
 */
public class PointerThread extends Thread{
    private MulticastSocket s;

    private Communication pointerSock;

    private byte[] inBuf;

    private int x, y;
    private byte status;

    MouseControll mouseControll;

    public PointerThread(Communication pointerSock){
        this.s = s;
        this.pointerSock = pointerSock;

        inBuf = new byte[4];
        mouseControll = new MouseControll();
    }

    //byte to int
    public double getInt(byte[] array, int offset) {
        return (double) (((array[offset] & 0xff) << 8) | ((array[offset+1] & 0xff)));
    }

    @Override
    public void run(){
        while(true){
            inBuf = pointerSock.recvPointer();

            if(inBuf != null) {
                x = (int) getInt(inBuf, 0);
                y = (int) getInt(inBuf, 2);

                System.out.println("x : " + x + ", y : " + y + ", status : " + status);
                mouseControll.MouseContorl(x, y);
            }
        }
    }
}
