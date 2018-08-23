import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Controller { //To control the logic of the game and to check whatever there is in game field and take control according to it 
    
    ArrayList<Hunter> h = new ArrayList<Hunter>();
    ArrayList<Hunter> distHolder = new ArrayList<Hunter>();
    int locXh,locYh;
    int locXp,locYp;
    double distBtwHP;
    double maxDist = 0;
    double minDist = 10;
    int tempIdx = -1;
    boolean removeProb;
    
    Random gen = new Random();
    
    Hunter TempHunter;
    Hunter DistantHunter;
    Player player;
    
    HunterPrey game;
    Textures tex;
    
    public Controller(HunterPrey game, Textures tex, Player player){
        this.game = game;
        this.tex = tex;
        this.player = player;
        locXp = (int)player.getX();
        locYp = (int)player.getY();
        for(int i = 0; i<5; i++){
            locXh = gen.nextInt(game.WIDTH);
            locYh = gen.nextInt(game.HEIGHT);
            distBtwHP = Math.sqrt(Math.pow((locXp-locXh), 2) + Math.pow(
                    (locYp-locYh), 2));
            // force a min. dist of 10px
            while(distBtwHP < 10){
                locXh = gen.nextInt(game.WIDTH);
                locYh = gen.nextInt(game.HEIGHT);
                distBtwHP = Math.sqrt(Math.pow((locXp-locXh), 2) + Math.pow(
                        (locYp-locYh), 2));
//                System.out.println("Generating initial random hunter locations");
            }
            addHunter(new Hunter(locXh, locYh, tex));
        }
    }
    
    public void tick(){
        locXp = (int)player.getX();
        locYp = (int)player.getY();
        
        for(int i = 0; i < h.size(); i++){
            TempHunter = h.get(i);
            TempHunter.tick();
            locXh = (int)TempHunter.getX();
            locYh = (int)TempHunter.getY();
            distBtwHP = Math.sqrt(Math.pow((locXp-locXh), 2) + Math.pow(
                    (locYp-locYh), 2));
            // find the most distant hunter
            if(distBtwHP>maxDist){
                maxDist = distBtwHP;
                tempIdx = i;
            }
            if(distBtwHP<minDist){
                JFrame frame = new JFrame();
                JLabel aLabel = new JLabel("GAME OVER!!!");
                frame.add(aLabel);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                game.gameStop();
            }
        }
        DistantHunter = h.get(tempIdx);
        // 1/5 = 0.2 probability of disappear, too much!!!
        removeProb = gen.nextInt(60)==0;
        if(removeProb){
//            System.out.println("Removing most distant hunter"+"h-size:"+h.size()+"idx:"+tempIdx);
            removeHunter(DistantHunter);
            locXh = gen.nextInt(game.WIDTH);
            locYh = gen.nextInt(game.HEIGHT);
            distBtwHP = Math.sqrt(Math.pow((locXp-locXh), 2) + Math.pow(
                    (locYp-locYh), 2));
            // [12,30] too small!!!
            while(distBtwHP < 30 || distBtwHP > 50){
                locXh = gen.nextInt(game.WIDTH);
                locYh = gen.nextInt(game.HEIGHT);
                distBtwHP = Math.sqrt(Math.pow((locXp-locXh), 2) + Math.pow(
                        (locYp-locYh), 2));
//                System.out.println("Generating random hunter location");
            }
            addHunter(new Hunter(locXh, locYh, tex));
        }
    }
    
    public void render(Graphics g){
        for(int i = 0; i < h.size(); i++){
            TempHunter = h.get(i);
            TempHunter.render(g);
        }
    }
    
    public void addHunter(Hunter hunter){
        h.add(hunter);
    }
    
    public void removeHunter(Hunter hunter){
        h.remove(hunter);
    }
    
    public int getHunterNumber() {
        return h.size();
    }
}
