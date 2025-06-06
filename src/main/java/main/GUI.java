package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI {

    GamePanel gp;
    Font arial_40, arial80B;
    BufferedImage keyimg;
    public boolean msgon = false;
    public String msg = "";
    int msgwait = 0;
    public boolean gameFinished = false;

    public GUI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gp);
        keyimg = key.image;
    }

    public void showmsg(String text) {
        msg = text;
        msgon = true;
    }

    public void draw(Graphics2D g2) {

        if (gameFinished) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "Found the Treasure";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);

            g2.setFont(arial80B);
            g2.setColor(Color.yellow);

            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }
        else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyimg, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            if (msgon) {

                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(msg, gp.tileSize/2, gp.tileSize*5);
                msgwait++;

                if (msgwait >120) {
                    msgwait = 0;
                    msgon = !msgon;
                }
            }
        }
    }
}
