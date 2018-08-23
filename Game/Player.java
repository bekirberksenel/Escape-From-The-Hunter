import java.awt.Graphics;

public class Player { //Class of player
    
    private double x;
    private double y;
    
    private double velX = 0;
    private double velY = 0;
    
    private Textures tex;
    
    public Player(double x, double y, Textures tex) {
        this.x = x;
        this.y = y;
        this.tex = tex;
    }
    
    public void tick() {
        x+=velX;
        y+=velY;
        
        if(x <= 0-15)
            x = 0-15;
        if(x >= HunterPrey.WIDTH-55)
            x = HunterPrey.WIDTH-55;
        if(y <= 0-15)
            y = 0-15;
        if(y >= HunterPrey.HEIGHT-64)
            y= HunterPrey.HEIGHT-64;
    }
    
    public void render(Graphics g) {
        
        g.drawImage(tex.player, (int)x, (int)y, null);
        
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
