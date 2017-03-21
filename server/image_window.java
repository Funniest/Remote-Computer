/**
 * Created by Minsungkim on 2017-03-15.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.text.html.ImageView;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;

public class image_window extends JFrame{
    private int width, height;

    private ImageIcon im; //타이틀 바의 이미지를 가지는 객체

    //cursor
    private JLabel cursor;
    private Dimension size;

    private int x, y;
    private boolean bEnter;

    //Menubar
    private JMenuBar menuBar = new JMenuBar();
    private JMenu sizeMenu = new JMenu("화면 크기");
    private JMenuItem sizeItem_1 = new JMenuItem("1280x768");
    private JMenuItem sizeItem_2 = new JMenuItem("1360x768");
    private JMenuItem sizeItem_3 = new JMenuItem("1400x1050");
    private JMenuItem sizeItem_4 = new JMenuItem("1680x1050");
    private JMenuItem sizeItem_5 = new JMenuItem("1920x1080");
    private JMenuItem sizeItem_6 = new JMenuItem("Full Screen");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    MouseEventListener mouseListener;

    public image_window(int width, int height) {
        this.width = width;
        this.height = height;
        createAndShowGUI();
    }

    public void initBackground(BufferedImage imageBuf) {
        setContentPane(new JLabel(new ImageIcon(imageBuf)));

        cursor.setBounds(x, y, size.width, size.height);
        add(cursor);

        // Set some layout, say FlowLayout
        setLayout(null);

        setVisible(true);
    }

    /*init*/
    private void createAndShowGUI()
    {
        setTitle("Backdoor ver 0.1");
        //Title image
        im = new ImageIcon("./image/title.png");
        setIconImage(im.getImage());

        // Set title and default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set a background for JFrame
        setContentPane(new JLabel(new ImageIcon("./image/X.png")));

        // Set some layout, say FlowLayout
        setLayout(null);

        //cursor iamge set
        cursor = new JLabel(new ImageIcon("./image/Cursor.png"));
        add(cursor);

        size = cursor.getPreferredSize();
        this.x = 0;
        this.y = 0;
        cursor.setBounds(this.x, this.y, size.width, size.height);

        setMenuBar();

        mouseListener = new MouseEventListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        // Set the size of the JFrame and
        // make it visible
        setSize(width, height);
        setVisible(true);
    }

    //menuBar
    public void setMenuBar(){
        setJMenuBar(menuBar);
        menuBar.add(sizeMenu);

        sizeMenu.add(sizeItem_1);
        sizeMenu.add(sizeItem_2);
        sizeMenu.add(sizeItem_3);
        sizeMenu.add(sizeItem_4);
        sizeMenu.add(sizeItem_5);
        sizeMenu.add(sizeItem_6);

        sizeItem_1.addActionListener((ActionEvent e) ->{
            setSize(1280, 768);
        });
        sizeItem_2.addActionListener((ActionEvent e) ->{
            setSize(1360, 768);
        });
        sizeItem_3.addActionListener((ActionEvent e) ->{
            setSize(1400, 1050);
        });
        sizeItem_4.addActionListener((ActionEvent e) ->{
            setSize(1680, 1050);
        });
        sizeItem_5.addActionListener((ActionEvent e) ->{
            setSize(1920, 1080);
        });
        sizeItem_6.addActionListener((ActionEvent e) ->{
            setSize(screenSize.width, screenSize.height);
        });

        setResizable(false);
    }

    //get width height
    public int getWIndowWidth(){
        width = getWidth();
        return width;
    }

    public int getWindowHeight(){
        height = getHeight();
        return height;
    }

    //Mouse Pointer
    public int getMouse_x(){
        return x;
    }

    public int getMouse_y(){
        return  y;
    }

    public boolean getMouseStatus(){
        return bEnter;
    }

    class MouseEventListener implements MouseInputListener {
        Point origin;

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("Enter");
            bEnter = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("Exit");
            bEnter = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
    }
}
