import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class PointerThread extends Thread{
    private MulticastSocket s;
    private image_window iw;

    private Communication pointerSock;

    private byte[] inBuf;

    private int x, y;

    public PointerThread(MulticastSocket s, image_window iw){
        this.s = s;
        this.iw = iw;

        pointerSock = new Communication(this.s);
        inBuf = new byte[4];
    }

    //byte to int
    public double getInt(byte[] inBuf, int offset){
        return (double) (((inBuf[offset] & 0xff) << 8) | ((inBuf[offset + 1] & 0xff)));
    }

    public void setRatio(int x, int y){
        int wdith = iw.getWidth();
        int height = iw.getHeight();

        if(height == 768)
            y = (int)(y / 1.5);
        else if(height == 1050)
            y = (int)(y / 1.15);
        else if(height == 1080)
            y = (int)(y / 1.1);

        if(wdith == 1280)
            x = (int)(x / 1.5);
        else if(wdith == 1360)
            x = (int)(x / 1.4);
        else if(wdith == 1400)
            x = (int)(x / 1.4);
        else if(wdith == 1680)
            x = (int)(x / 1.05);
        else if(wdith == 1920)
            x = (int)(x / 1);

        this.x = x;
        this.y = y;
        System.out.println("W : " + wdith + ", H : " + height);
        System.out.println("X : "+ this.x + ", Y : " + this.y);
    }


    @Override
    public void run(){
        while(true){
            inBuf = pointerSock.recvPointer();
            if(inBuf != null) {
                setRatio((int) getInt(inBuf, 0), (int) getInt(inBuf, 2));
                iw.setCursorX(this.x);
                iw.setCursorY(this.y);
            }
        }
    }
}
