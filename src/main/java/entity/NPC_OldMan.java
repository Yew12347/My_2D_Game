package entity;

import main.GamePanel;
import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidHitbox.x = 8;
        solidHitbox.y = 8;
        solidHitboxDefaultX = solidHitbox.x;
        solidHitboxDefaultY = solidHitbox.y;
        solidHitbox.width = 32;
        solidHitbox.height = 32;

        getimage();
    }

    public void getimage() {
        up1 = loadImage("/npc/oldman_up_1");
        up2 = loadImage("/npc/oldman_up_2");
        down1 = loadImage("/npc/oldman_down_1");
        down2 = loadImage("/npc/oldman_down_2");
        left1 = loadImage("/npc/oldman_left_1");
        left2 = loadImage("/npc/oldman_left_2");
        right1 = loadImage("/npc/oldman_right_1");
        right2 = loadImage("/npc/oldman_right_2");
    }

    public void setAction() {
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        actionLockCounter++;
    }
}
