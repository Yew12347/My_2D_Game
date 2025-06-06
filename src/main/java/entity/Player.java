package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidHitbox = new Rectangle();
        solidHitbox.x = 8;
        solidHitbox.y = 16;
        solidHitboxDefaultX = solidHitbox.x;
        solidHitboxDefaultY = solidHitbox.y;
        solidHitbox.width = 32;
        solidHitbox.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldx = 1056;
        worldy = 1056;
        speed = 4;
        direction = "down";
        kbDirection = "down";
    }

    private BufferedImage loadImage(String path){

        Utility util = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/player/" + path +".png"));
            image = util.scaledImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getPlayerImage() {
        up1 = loadImage("boy_up_1");
        up2 = loadImage("boy_up_2");
        down1 = loadImage("boy_down_1");
        down2 = loadImage("boy_down_2");
        left1 = loadImage("boy_left_1");
        left2 = loadImage("boy_left_2");
        right1 = loadImage("boy_right_1");
        right2 = loadImage("boy_right_2");
    }

    public void update() {
        int dx = 0;
        int dy = 0;

        // Calculate horizontal movement
        if (keyH.leftPressed) {
            dx -= 1;
        }
        if (keyH.rightPressed) {
            dx += 1;
        }

        // Calculate vertical movement
        if (keyH.upPressed) {
            dy -= 1;
        }
        if (keyH.downPressed) {
            dy += 1;
        }

        // Determine direction for sprite facing (left/right prioritized on diagonals)
        if (dx < 0) {
            direction = "left";
        } else if (dx > 0) {
            direction = "right";
        } else if (dy < 0) {
            direction = "up";
        } else if (dy > 0) {
            direction = "down";
        }

        // Normalize movement if moving diagonally
        double length = Math.sqrt(dx * dx + dy * dy);
        double ndx = 0;
        double ndy = 0;

        if (length != 0) {
            ndx = dx / length;
            ndy = dy / length;
        }

        // Multiply by speed and convert to integer movement
        int moveX = (int) Math.round(ndx * speed);
        int moveY = (int) Math.round(ndy * speed);

        // Check horizontal collision and move if no collision
        collisionOn = false;
        gp.cChk.checkTile(this, moveX, 0);
        int objIndexX = gp.cChk.checkObject(this, true, moveX, 0);
        pickUpObj(objIndexX);

        if (!collisionOn) {
            worldx += moveX;
        }

        // Check vertical collision and move if no collision
        collisionOn = false;
        gp.cChk.checkTile(this, 0, moveY);
        int objIndexY = gp.cChk.checkObject(this, true, 0, moveY);
        pickUpObj(objIndexY);

        if (!collisionOn) {
            worldy += moveY;
        }

        // Sprite animation
        spriteCounter++;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }else {
            spriteNum = 1;
        }
    }

    public void pickUpObj(int i) {

        if (i != 999) {

            String objName = gp.obj[i].name;

            switch (objName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.gui.showmsg("Got key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.gui.showmsg("Door opened!");
                    }else {
                        gp.gui.showmsg("Need a Key!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.gui.showmsg("Speed lover huh?");
                    break;
                case "Chest":
                    gp.gui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY, null);
    }
}
