import java.awt.Graphics;
import java.util.Random;

public class Hunter { //Class of hunter
    
    private double x;
    private double y;
    
    private double velX = 0;
    private double velY = 0;
    private double dirID = -1;
        
    Random gen = new Random();
    
    private Textures tex;
    
    public Hunter(double x, double y, Textures tex){
        this.x = x;
        this.y = y;
        this.tex = tex;
    }
    
    public void tick() {
        // random direction selection
        dirID = gen.nextInt(8)+1;
        
        switch ((int)dirID) {
            case 1:
                this.velX = 3;
                this.velY = 0;
                break;
            case 2:
                this.velX = 0;
                this.velY = 3;
                break;
            case 3:
                this.velX = -3;
                this.velY = 0;
                break;
            case 4:
                this.velX = 0;
                this.velY = -3;
                break;
            case 5:
                this.velX = 2;
                this.velY = 2;
                break;
            case 6:
                this.velX = -2;
                this.velY = -2;
                break;
            case 7:
                this.velX = 2;
                this.velY = -2;
                break;
            case 8:
                this.velX = -2;
                this.velY = 2;
                break;    
            default:
                break;
        }
        
        if(x <= 0-15)
            x = 0-15;
        if(x >= HunterPrey.WIDTH-55)
            x = HunterPrey.WIDTH-55;
        if(y <= 0-15)
            y = 0-15;
        if(y >= HunterPrey.HEIGHT-64)
            y= HunterPrey.HEIGHT-64;
        
        x+=velX;
        y+=velY;
        
    }
    
    public void render(Graphics g) {
        
        g.drawImage(tex.hunter, (int)x, (int)y, null);
        
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public void setVelX(double velX){
        this.velX = velX;
    }
    
    public void setVelY(double velY){
        this.velY = velY;
    }
    
}
