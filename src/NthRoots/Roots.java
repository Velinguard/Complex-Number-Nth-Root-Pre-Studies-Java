/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NthRoots;

import static NthRoots.Roots.HEIGHT;
import static NthRoots.Roots.SCALER;
import static NthRoots.Roots.WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Sam
 */
public class Roots extends JPanel {
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    public static int SCALER = 1;
    public static double milliSecondTimer;
    public static double delta;
    public static ArrayList<Integer> keysDown;
    public static ArrayList<Rectangle> balls;
    public static int n;
    public static boolean one;
    public static Root r;
    
    public Roots(){
        //Init
        keysDown = new ArrayList<Integer>();
        balls = new  ArrayList<Rectangle>();
        n = 4;
        one = false;
        r = new Root(n, one);

        //Other
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);   
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("nth roots of 1, -1");
        Roots app = new Roots();
        frame.setSize((int)(WIDTH * SCALER),(int)(HEIGHT * SCALER));
        frame.add(app);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.requestFocus();
        long lastLoopTime = System.nanoTime();
        int fps = 0, lastFpsTime = 0, lastMilliSecondTimer = 0, count = 1;
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        //Game Loop
        while(true){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            delta = updateLength / ((double)OPTIMAL_TIME);
            lastFpsTime += updateLength;
            lastMilliSecondTimer += updateLength;
            fps++;
            if (lastFpsTime > 100000000 * count){
               milliSecondTimer += 0.1;
               count++;
            }
            if (lastFpsTime >= 1000000000){
                System.out.println("(FPS: "+fps+")");
                //milliSecondTimer += 1;
                lastFpsTime = 0;
                fps = 0;
                count = 1;
            }
            app.repaint();
            Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
        }
    }
    //Window Painter
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.setFocusable(true);
        this.requestFocusInWindow();

        //Game loop, but everything time related * delta to get seconds.
        int k;
        g2d.setColor(Color.CYAN);
        int w = 10 * SCALER;
        int rows = HEIGHT / w;
        int columns = WIDTH / w;
        for (k = 0; k < rows; k++) {
            g2d.drawLine(0, k * w, WIDTH, k * w);
        }
        for (k = 0; k < columns; k++) {
            g2d.drawLine(k * w, 0, k * w, HEIGHT);
        }
        
        g2d.setColor(Color.BLACK);
        Rectangle x = new Rectangle(WIDTH / 2 - 1, HEIGHT / 2 - 500 , 2 , 1000);
        x.paint(g2d);
        Rectangle y = new Rectangle((WIDTH / 2) - 500, HEIGHT / 2 - 1 , 1000 , 2);
        y.paint(g2d);
        
        for (double i = -1; i <= 1 ; i += 0.25){
            g2d.drawString(Double.toString(i),(int) ((WIDTH / 2) + (500 * i)), (int) HEIGHT / 2 + 12);
        }
        for (double i = -1; i <= 1 ; i += 0.25){
            g2d.drawString(Double.toString(i) + "i",(int) ((WIDTH / 2) + 3), (int) ((HEIGHT / 2) - (500 * i)));
        }        
        g2d.drawString("Number of roots = " + n, 5, 51);
        g2d.drawString("Press up to increase number of roots", 5, 15);
        g2d.drawString("Press down to decrease number of roots", 5, 27);
        g2d.drawString("Press space to switch between root of 1 and -1", 5, 39);
        
        g2d.setFont(new Font("Times New Roman",Font.PLAIN, 18));
        g2d.setColor(Color.blue);
        
        DecimalFormat df = new DecimalFormat("#0.00"); 
        for (int i = 0; i < n; i++){
            g2d.drawLine(WIDTH / 2, HEIGHT / 2, (int) (WIDTH / 2 + (r.points[i].real * 500)), (int) (HEIGHT /2  - (r.points[i].complex * 500)));
            g2d.drawString("(" + df.format(r.points[i].real) + ", " + df.format(r.points[i].complex) + "i)", (int) (WIDTH / 2 + (r.points[i].real * 500)), (int) (HEIGHT /2  - (r.points[i].complex * 500)));
        }
        
    }    
    //Listens for button presses
    public class MyKeyListener implements KeyListener{

        public void action(){
            if (keysDown.contains(KeyEvent.VK_UP)){
                n += 1;
                r = new Root(n , one);
            }
            if (keysDown.contains(KeyEvent.VK_DOWN)){
                n -= 1;
                r = new Root(n , one);
            }
            if (keysDown.contains(KeyEvent.VK_SPACE)){
                one = !one;
                r = new Root(n , one);
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {
                       
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!keysDown.contains(e.getKeyCode())){
               keysDown.add(e.getKeyCode()); 
            }
            action();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysDown.remove(new Integer(e.getKeyCode()));
        }
    }
}
//Define other objects.

class Rectangle extends Rectangle2D.Float {

    Color colour;

    public Rectangle(int x, int y, int rx, int ry) {
        super(x, y, rx, ry);
        this.colour = Color.black;
    }

    public void paint(Graphics2D g) {
        g.setColor(colour);
        g.fill(this);
    }
}
class Ball extends Ellipse2D.Float {

    Color colour;
    double ha; //horizontal acceleration, + = -->
    double va; //vertical acceleration, + = ^
    double vSpeed; //vertical speed, + = ^
    double hSpeed; //horizontal speed, + = -->
    int mass;

    public Ball(float x, float y, float r) {
        super(x, y, r, r);
        colour = Color.blue;
        this.va = (int) 9.81;
        this.ha = (int) 0;
        this.vSpeed = 0;
        this.hSpeed = 0;
        this.mass = 3;
    }

    public void move(int d, int s) {
        switch (d) {
            //if within the screen then move s up
            case 0:
                if (s < 0) {
                    if (HEIGHT * SCALER - super.height > super.getCenterY() + s) {
                        super.y -= s;
                    }
                } else {
                    if (super.height < super.getCenterY() - s) {
                        super.y -= s;
                    }
                }
                break;
            case 1:
                //if within the screen then move s to the right
                if (s < 0) {
                    if (super.width < super.getCenterX() - s) {
                        super.x -= s;
                    }
                } else {
                    if (WIDTH * SCALER - super.width > super.getCenterX() + s) {
                        super.x += s;
                    }
                }
                break;
        }
    }

    public void paint(Graphics2D g) {
        g.setColor(colour);
        g.fill(this);
    }
}

/* Useful shortcuts:
grid (tab) = grid layout every 10 pixels, on a 100 pixel = 1 metre scale, that is 10 cm.
grid2 (tab) = grid layout every 100 pixels, on a 100 pixel = 1 metre scale, that is 1m.
Ball (tab) = creates the Ball class.
Rectangle (tab) = creates the Rectangle class.
*/