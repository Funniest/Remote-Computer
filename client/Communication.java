import java.awt.image.BufferedImage;
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
    private int HEADER_SIZE = 2;

    //cursor buffer size
    private int EXCEPT_BUFFER_LEN = 4;

    //PORT, IP
    private int port;
    private String group;

    MulticastSocket s;
    DatagramPacket pack;

    public Communication(MulticastSocket s, String group, int port){
        this.s = s;
        this.group = group;
        this.port = port;
    }

    public void sendImage(byte[] imageBuf) throws IOException{
        //send Image value
        int imageLength = imageBuf.length;
        int dataLength = DATA_MAX_SIZE - HEADER_SIZE;
        int packageNumber = imageLength / dataLength;

        if(imageLength % dataLength != 0)
            packageNumber++;

        int imgBuffIndex = 0;
        byte[] imageTemp = null;

        for(int i = 0; i < packageNumber; i++){
            imageTemp = new byte[DATA_MAX_SIZE];
            //package number
            imageTemp[0] = (byte)(i + 1);
            //number of packages
            imageTemp[1] = (byte)packageNumber;

            //Coping image data into current package
            for(int j = 0; j < dataLength && imgBuffIndex < imageLength; j++){
                imageTemp[j+2] = imageBuf[imgBuffIndex++];
            }
            //WTF
            //System.out.println();
            //Create and send datagram package
            pack = new DatagramPacket(imageTemp, imageTemp.length, InetAddress.getByName(group), port);
            s.send(pack);
        }

        //s.close();
    }

    public byte[] recvPointer(){
        byte[] cursorPosi = new byte[4];

        try {
            pack = new DatagramPacket(cursorPosi, cursorPosi.length);
            System.out.println("Recv wait");
             s.receive(pack);
            System.out.println("len : " + cursorPosi[0]);
            if(cursorPosi.length == EXCEPT_BUFFER_LEN){
                return cursorPosi;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
