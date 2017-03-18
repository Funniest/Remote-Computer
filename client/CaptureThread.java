import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class CaptureThread extends Thread {
    private ScreenShot screenShot;

    public CaptureThread() {
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

        while(true) {

            capture = screenShot.getScreenshot();

            try {
                if(different(image, temp)) {
                    image = screenShot.bufferedImageToByteArray(capture);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Main.sendThread.insertImage(image);
        }
    }
}
