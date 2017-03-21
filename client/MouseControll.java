import java.awt.*;

/**
 * Created by Minsungkim on 2017-03-19.
 */
public class MouseControll {
    private Robot r;

   public MouseControll(){
       try {
           r = new Robot();
       }catch (AWTException e){
           e.printStackTrace();
       }
   }

    void MouseContorl(int x, int y){
        r.mouseMove(x, y);
    }
}
