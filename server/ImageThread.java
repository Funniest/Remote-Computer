import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MulticastSocket;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class ImageThread extends Thread{
    private MulticastSocket s;
    private image_window iw;

    private boolean status;

    private Communication imageSock;
    private BufferedImage imageBuf;

    private ImageSize is;
    private int wdith;
    private int height;

    private String group;
    private int port;

    public ImageThread(MulticastSocket s, image_window iw, int wdith, int height){
        this.s = s;
        this.iw = iw;

        this.imageSock = new Communication(s, group, port);

        this.is = new ImageSize();
        this.wdith = wdith;
        this.height = height;

        this.status = true;
    }

    public void stopThread(){
        status = false;
    }

    public void run(){
        while(status) {
            imageBuf = imageSock.recvImage();

            wdith = iw.getWIndowWidth();
            height = iw.getWindowHeight();

            try {
                if(!(imageBuf.getWidth() == wdith && imageBuf.getHeight() == height))
                   imageBuf = is.reSizing(imageBuf, wdith, height);
            }catch (IOException e){
                e.printStackTrace();
            }

            iw.initBackground(imageBuf);
        }
    }
}
