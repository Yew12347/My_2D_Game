package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    KeyHandler keyH;

    GamePanel gp;
    public int worldx, worldy;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction, kbDirection;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidHitbox = new Rectangle(8, 8, 32, 32);
    public int solidHitboxDefaultX, solidHitboxDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldx - gp.player.worldx + gp.player.screenX;
        int screenY = worldy - gp.player.worldy + gp.player.screenY;

        if (worldx + gp.tileSize > gp.player.worldx - gp.player.screenX &&
                worldx - gp.tileSize < gp.player.worldx + gp.player.screenX &&
                worldy + gp.tileSize > gp.player.worldy - gp.player.screenY &&
                worldy - gp.tileSize < gp.player.worldy + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    } else if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    } else if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    } else if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    } else if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            if (GamePanel.debug) {
                if (gp.debugging) {
                    g2.setColor(Color.RED);
                    g2.drawRect(screenX + solidHitbox.x, screenY + solidHitbox.y, solidHitbox.width, solidHitbox.height);
                }
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage loadImage(String path){

        Utility util = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + path +".png"));
            image = util.scaledImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChk.checkTile(this);
        gp.cChk.checkPlayer(this);

        if (!collisionOn) {
            switch (direction) {
                case "up": worldy -= speed; break;
                case "down": worldy += speed; break;
                case "left": worldx -= speed; break;
                case "right": worldx += speed; break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
