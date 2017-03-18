import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * Created by Minsungkim on 2017-03-18.
 */
public class PointerThread extends Thread{
    private Communication communication;

    private byte[] outBuf;

    private PointerInfo pointer;
    private PointerInfo tmpPointer;

    public PointerThread(Communication communication){
        this.communication = communication;
        outBuf = new byte[4];

        this.pointer = MouseInfo.getPointerInfo();
    }

    private void putInt(int value, byte[] array, int offset) {
        array[offset]   = (byte)(0xff & (value >> 8));
        array[offset+1] = (byte)(0xff & value);
    }

    @Override
    public void run(){
        while(true) {
            //Get pointer posi
            tmpPointer = MouseInfo.getPointerInfo();
            if (tmpPointer != null) {
                if (pointer.getLocation().getX() != tmpPointer.getLocation().getX() ||
                        pointer.getLocation().getY() != tmpPointer.getLocation().getY()) {
                    pointer = tmpPointer;
                /* Cursor coordinates */
                    int x = (int) pointer.getLocation().getX();
                    int y = (int) pointer.getLocation().getY();

                    System.out.println("X : " + x + ", Y : " + y);
                /* Put the coordinates into byte array */
                    putInt(x, outBuf, 0);
                    putInt(y, outBuf, 2);

                    communication.sendPointer(outBuf);
                }
            }

            try {
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
