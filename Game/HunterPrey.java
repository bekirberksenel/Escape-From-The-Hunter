import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JButton;

public class HunterPrey extends Canvas implements Runnable { //The main function and render/tick thread is here
    
    public static final long serialVersionUID = 1L;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int SCALE = 1;
    public static final double VEL_PREY = 3;
    public final String TITLE = "2D Hunter-Prey Game";
    
    private boolean running = false;
    private Thread thread;
    
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteHunter =null;
    
    private Controller c;
    private Player p;
    private Textures tex;
    
    public void init() {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            spriteSheet = loader.loadImage("mon2_sprite_base.png");
        }catch(IOException e){
            e.printStackTrace();
        }
        addKeyListener(new KeyInput(this));
        tex = new Textures(this);
        p = new Player(200,200,tex);
        c = new Controller(this,tex,p);
    }
    
    private synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
    
    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        int roughSec = 0;
        
        while(running) {
            // game loop here
            
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                tick();
                updates++; 
                delta--;
            }
            
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + "Ticks, fps"  +frames);
                
                updates = 0;
                frames = 0;
                
                roughSec++;
            }
            render(roughSec);
        }
        stop();
    }
    
    private void tick() {
        
        p.tick();
        c.tick();
        
    }
    
    public void gameStop(){
        stop();
    }
    
    private void render(int time2disp) {
        
        BufferStrategy bs = this.getBufferStrategy();
        
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        //////////////////////////////////
        
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        p.render(g);
        c.render(g);    
        
        g.setColor(Color.red);
        g.drawString("Time:"+""+time2disp+"secs", 25, 25);
        //////////////////////////////////
        g.dispose();
        bs.show();
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_RIGHT:
                p.setVelX(VEL_PREY);
                break;
            case KeyEvent.VK_LEFT:
                p.setVelX(-VEL_PREY);
                break;
            case KeyEvent.VK_UP:
                p.setVelY(-VEL_PREY);
                break;
            case KeyEvent.VK_DOWN:
                p.setVelY(VEL_PREY);
                break;
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_RIGHT:
                p.setVelX(0);
                break;
            case KeyEvent.VK_LEFT:
                p.setVelX(0);
                break;
            case KeyEvent.VK_UP:
                p.setVelY(0);
                break;
            case KeyEvent.VK_DOWN:
                p.setVelY(0);
                break;
            default:
                break;
        }
    }
    
    public static void main(String args[]) {
        
        final HunterPrey game = new HunterPrey();
        
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        JFrame frame = new JFrame(game.TITLE);
        JButton startButton = new JButton("Click to start.");
        startButton.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               game.start();
           } 
        });
 
        frame.getContentPane().setLayout(new FlowLayout());
        frame.add(startButton);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
    
}
