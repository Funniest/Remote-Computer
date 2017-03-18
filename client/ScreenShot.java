import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Minsungkim on 2017-03-16.
 */
public class ScreenShot {
    private Robot robot;
    private Rectangle screenRect;

    private String OUTPUT_FORMAT = "gif";

    public ScreenShot(){
        /*Full Screen shot!*/
        this.screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try{
           this.robot = new Robot();
        }catch (AWTException e){
            e.printStackTrace();
        }
    }

    /*get Screen shot and return Buffered Image*/
    public BufferedImage getScreenshot(){
        BufferedImage bufImage = null;
        bufImage = robot.createScreenCapture(screenRect);
        return bufImage;
    }

    /*Buffered Image to byte*/
    public byte[] bufferedImageToByteArray(BufferedImage image) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, OUTPUT_FORMAT, byteArrayOutputStream);
        byteArrayOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }
}
