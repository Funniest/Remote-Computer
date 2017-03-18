import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Minsungkim on 2017-03-16.
 */
public class Communication {
    //SIZE
    private int DATA_MAX_SIZE = 65507;
    private int NUMBER_OF_PACKETS = 255;

    //cursor buffer size
    private int EXCEPT_BUFFER_LEN = 4;

    MulticastSocket s;
    DatagramPacket pack;

    public Communication(MulticastSocket s){
        this.s = s;
    }

    public BufferedImage recvImage(){
        byte[] recvBuf = new byte[DATA_MAX_SIZE];
        byte[] imageBuf = new byte[DATA_MAX_SIZE * NUMBER_OF_PACKETS];

        byte k = 1;
        int iSlice = 0;

        while(true){
            try {
                pack = new DatagramPacket(recvBuf, recvBuf.length);
                s.receive(pack);

                byte x = recvBuf[0];
                byte y = recvBuf[1];

                if(k == x){
                    for (int i = 2; i < recvBuf.length; i++){
                        imageBuf[iSlice++] = recvBuf[i];
                    }
                    k++;
                }

                if(x == y){
                    BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBuf));
                    return bf;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public byte[] recvPointer(){
        byte[] cursorPosi = new byte[4];

        try {
            pack = new DatagramPacket(cursorPosi, cursorPosi.length);
            s.receive(pack);
            if(cursorPosi.length == EXCEPT_BUFFER_LEN){
                return cursorPosi;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
