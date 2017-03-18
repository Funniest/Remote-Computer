import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class ImageSize {
    public BufferedImage reSizing(BufferedImage imageBuf, int destWidth, int destHeight) throws IOException{
        BufferedImage srcImg = imageBuf;
        Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
        int pixels[] = new int[destWidth * destHeight];
        PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth);
        try {
            pg.grabPixels(); // JEPG 포맷의 경우 오랜 시간이 걸린다.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
        destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);

        return destImg;
    }

    public int[] getSize(BufferedImage imageBuf){
        int[] size = new int[2];
        size[0] = imageBuf.getWidth();
        size[1] = imageBuf.getHeight();

        return size;
    }
}
