package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16;
    final int scale = 3;
    public int tileSize = originalTileSize * scale; // 48

    public int maxScreenCol = 26; // 26 * 48 = 1248px
    public int maxScreenRow = 15; // 15 * 48 = 720px

    public int screenWidth = tileSize * maxScreenCol; // 1248
    public int screenHeight = tileSize * maxScreenRow; // 720

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public static boolean debug = true;
    boolean debugging = false;

    int FPS = 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    Sound music = new Sound();
    public CollisionChk cChk = new CollisionChk(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public GUI gui = new GUI(this);
    Thread gameThread;
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
               // System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }

    }
    public void update() {  player.update(); }
    public void paintComponent(Graphics g) {

        //debug stuff
        long drawstart = 0;
        if (debug) {
            drawstart = System.nanoTime();
        }

        //tile
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        player.draw(g2);

        gui.draw(g2);

        //also debug sutff

        if (debug) {
            if (keyH.debugon) {
                    debugging = true;
            }else {
                debugging = false;
            }
            if (debugging) {
                long drawend = System.nanoTime();
                long passed = drawend - drawstart;
                g2.setColor(Color.white);
                g2.drawString("Drawtime:" + passed, 10, 400);
                System.out.println(passed);
            }

        }

        //not debug anymore

        g2.dispose();
    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }
}
