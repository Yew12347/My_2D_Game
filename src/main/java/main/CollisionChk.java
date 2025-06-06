package main;

import entity.Entity;

public class CollisionChk {

    GamePanel gp;

    public CollisionChk(GamePanel gp) {
        this.gp = gp;
    }

    // Modified to accept dx and dy to check collision at next position
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

        // Check tiles based on movement direction
        if (dx < 0) { // Moving left
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } else if (dx > 0) { // Moving right
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }

        if (dy < 0) { // Moving up
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        } else if (dy > 0) { // Moving down
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
    }

    // Modified to accept dx and dy to check object collision at next position
    public int checkObject(Entity entity, boolean player, int dx, int dy) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // Save original hitbox positions
                int entityHitboxX = entity.solidHitbox.x;
                int entityHitboxY = entity.solidHitbox.y;
                int objHitboxX = gp.obj[i].solidHitbox.x;
                int objHitboxY = gp.obj[i].solidHitbox.y;

                // Update hitbox positions to next position
                entity.solidHitbox.x = entity.worldx + entity.solidHitbox.x + dx;
                entity.solidHitbox.y = entity.worldy + entity.solidHitbox.y + dy;

                gp.obj[i].solidHitbox.x = gp.obj[i].worldX + gp.obj[i].solidHitbox.x;
                gp.obj[i].solidHitbox.y = gp.obj[i].worldY + gp.obj[i].solidHitbox.y;

                if (entity.solidHitbox.intersects(gp.obj[i].solidHitbox)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                // Restore original hitbox positions
                entity.solidHitbox.x = entityHitboxX;
                entity.solidHitbox.y = entityHitboxY;
                gp.obj[i].solidHitbox.x = objHitboxX;
                gp.obj[i].solidHitbox.y = objHitboxY;
            }
        }
        return index;
    }
}