import java.io.IOException;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 * Created by Minsungkim on 2017-03-17.
 */
public class SendThread extends Thread {

    private Object imageListLock;

    private ArrayList<byte[]> imageList;
    private Communication communication;

    public SendThread(Communication communication) throws IOException {
        imageListLock = new Object();

        imageList = new ArrayList<>();

        if (communication != null) {
            this.communication = communication;
        } else {
            throw new IOException();
        }
    }

    public void insertImage(byte[] image) {
        synchronized (imageListLock) {
            imageList.add(image);
        }
    }

    @Override
    public void run() {
        byte[] image;

        while(true) {

            synchronized (imageListLock) {

                for(int i = 0; i < imageList.size(); i++) {
                    image = imageList.get(i);

                    try {
                        communication.sendImage(image);
                    } catch (IOException e) {
                        System.out.println("서버에 이미지 전송에 실패했습니다.");
                        e.printStackTrace();
                    }

                    imageList.remove(i);
                }
            }


        }
    }
}
