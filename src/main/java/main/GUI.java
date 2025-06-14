package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.SplittableRandom;

public class GUI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial80B;
   // BufferedImage keyimg;
    public boolean msgon = false;
    public String msg = "";
    int msgwait = 0;
    public boolean gameFinished = false;

    public GUI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gp);
        //keyimg = key.image;
    }

    public void showmsg(String text) {
        msg = text;
        msgon = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {

        }

        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }
    public void drawPauseScreen() {

        g2.setFont(gp.getFont().deriveFont(Font.PLAIN,120));
        String text = "PAUSED";
        int x = getXforCenteredtext(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public int getXforCenteredtext(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x =  gp.screenWidth/2 - length/2;
        return x;
    }
}
