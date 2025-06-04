package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    private BufferedImage loadImage(String path) throws IOException {
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            throw new IOException("Resource not found: " + path);
        }
        return ImageIO.read(stream);
    }

    public void getPlayerImage() {
        try {
            up1 = loadImage("/assets/player/boy_up_1.png");
            up2 = loadImage("/assets/player/boy_up_2.png");
            down1 = loadImage("/assets/player/boy_down_1.png");
            down2 = loadImage("/assets/player/boy_down_2.png");
            left1 = loadImage("/assets/player/boy_left_1.png");
            left2 = loadImage("/assets/player/boy_left_2.png");
            right1 = loadImage("/assets/player/boy_right_1.png");
            right2 = loadImage("/assets/player/boy_right_2.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        int dx = 0;
        int dy = 0;

        // Calculate horizontal movement
        if (keyH.leftPressed) {
            dx -= speed;
        }
        if (keyH.rightPressed) {
            dx += speed;
        }

        // Calculate vertical movement
        if (keyH.upPressed) {
            dy -= speed;
        }
        if (keyH.downPressed) {
            dy += speed;
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

        // Update player position
        x += dx;
        y += dy;

        spriteCounter++;
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
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

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}