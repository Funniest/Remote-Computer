import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class CaptureThread extends Thread {
    private ScreenShot screenShot;

    private Image cursor;
    private int x;
    private int y;

    private Graphics2D graphics2D;

    public CaptureThread() {
        try {
            cursor = ImageIO.read(new File("./image/Cursor.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        screenShot = new ScreenShot();
    }

    public boolean different(byte[] image, byte[] temp){
        if(temp == null) return true;
        for(int i = 0; i < temp.length; i++){
            if(temp[i] != image[i])
                return true;
        }
        return false;
    }

    @Override
    public void run() {
        BufferedImage capture;
        byte[] image = null;
        byte[] temp = null;

        boolean check = true;

        while(true) {

            capture = screenShot.getScreenshot();

            x = MouseInfo.getPointerInfo().getLocation().x;
            y = MouseInfo.getPointerInfo().getLocation().y;

            graphics2D = capture.createGraphics();
            graphics2D.drawImage(cursor, x, y, 16, 16, null);
            try {
                ImageIO.write(capture, "PNG", new File("./image/temp.png"));
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                image = screenShot.bufferedImageToByteArray(capture);

                if(different(image, temp)) {
                    check = true;
                }else{
                    check = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(check == true) {
                Main.sendThread.insertImage(image);
            }
        }
    }
}
