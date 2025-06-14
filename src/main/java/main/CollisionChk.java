package main;

import entity.Entity;
import java.awt.Rectangle;

public class CollisionChk {

    GamePanel gp;

    public CollisionChk(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int dx = 0;
        int dy = 0;

        switch (entity.direction) {
            case "up": dy -= entity.speed; break;
            case "down": dy += entity.speed; break;
            case "left": dx -= entity.speed; break;
            case "right": dx += entity.speed; break;
        }

        checkTile(entity, dx, dy);
    }

    public void checkTile(Entity entity, int dx, int dy) {
        int entityLeftWorldX = entity.worldx + entity.solidHitbox.x + dx;
        int entityRightWorldX = entity.worldx + entity.solidHitbox.x + entity.solidHitbox.width + dx;
        int entityTopWorldY = entity.worldy + entity.solidHitbox.y + dy;
        int entityBottomWorldY = entity.worldy + entity.solidHitbox.y + entity.solidHitbox.height + dy;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        if (dx < 0) {
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } else if (dx > 0) {
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }

        if (dy < 0) {
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } else if (dy > 0) {
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int dx = 0;
        int dy = 0;

        switch (entity.direction) {
            case "up": dy -= entity.speed; break;
            case "down": dy += entity.speed; break;
            case "left": dx -= entity.speed; break;
            case "right": dx += entity.speed; break;
        }

        return checkObject(entity, player, dx, dy);
    }

    public int checkObject(Entity entity, boolean player, int dx, int dy) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                Rectangle entityHitbox = new Rectangle(
                        entity.worldx + entity.solidHitbox.x + dx,
                        entity.worldy + entity.solidHitbox.y + dy,
                        entity.solidHitbox.width,
                        entity.solidHitbox.height
                );

                Rectangle objHitbox = new Rectangle(
                        gp.obj[i].worldX + gp.obj[i].solidHitbox.x,
                        gp.obj[i].worldY + gp.obj[i].solidHitbox.y,
                        gp.obj[i].solidHitbox.width,
                        gp.obj[i].solidHitbox.height
                );

                if (entityHitbox.intersects(objHitbox)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] targets) {
        int dx = 0, dy = 0;
        switch (entity.direction) {
            case "up": dy -= entity.speed; break;
            case "down": dy += entity.speed; break;
            case "left": dx -= entity.speed; break;
            case "right": dx += entity.speed; break;
        }

        int index = 999; // default no collision

        for (int i = 0; i < targets.length; i++) {
            Entity target = targets[i];
            if (target != null && target != entity) {

                // Create hitbox for entity at next position (after movement)
                Rectangle entityHitbox = new Rectangle(
                        entity.worldx + entity.solidHitbox.x + dx,
                        entity.worldy + entity.solidHitbox.y + dy,
                        entity.solidHitbox.width,
                        entity.solidHitbox.height
                );

                // Create hitbox for target NPC (stationary or moving separately)
                Rectangle targetHitbox = new Rectangle(
                        target.worldx + target.solidHitbox.x,
                        target.worldy + target.solidHitbox.y,
                        target.solidHitbox.width,
                        target.solidHitbox.height
                );

                if (entityHitbox.intersects(targetHitbox)) {
                    entity.collisionOn = true;
                    index = i;
                    break; // stop checking after first collision found
                }
            }
        }

        return index;
    }
    public void checkPlayer(Entity entity) {
        int dx = 0, dy = 0;

        switch (entity.direction) {
            case "up":
                dy -= entity.speed;
                break;
            case "down":
                dy += entity.speed;
                break;
            case "left":
                dx -= entity.speed;
                break;
            case "right":
                dx += entity.speed;
                break;
        }

        Rectangle entityHitbox = new Rectangle(
                entity.worldx + entity.solidHitbox.x + dx,
                entity.worldy + entity.solidHitbox.y + dy,
                entity.solidHitbox.width,
                entity.solidHitbox.height
        );

        Rectangle playerHitbox = new Rectangle(
                gp.player.worldx + gp.player.solidHitbox.x,
                gp.player.worldy + gp.player.solidHitbox.y,
                gp.player.solidHitbox.width,
                gp.player.solidHitbox.height
        );

        if (entityHitbox.intersects(playerHitbox)) {
            entity.collisionOn = true;
            // You can also do something here like set player.damage = true;
        }
    }
}