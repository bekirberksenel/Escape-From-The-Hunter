import java.awt.image.BufferedImage;

public class Textures { //To give different avatars to different characters
    
    public BufferedImage player, hunter;
    
    private SpriteSheet ss;
    
    public Textures(HunterPrey game) {
        ss = new SpriteSheet(game.getSpriteSheet());
        getTextures();
        
    }
    
    private void getTextures() {
        player = ss.grabImage(1, 1, 64, 64);
        hunter = ss.grabImage(4, 6, 64, 64);
    }
    
}
