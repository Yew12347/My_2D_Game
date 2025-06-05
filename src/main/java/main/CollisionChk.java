package main;

import entity.Entity;

public class CollisionChk {

    GamePanel gp;

    public CollisionChk(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldx + entity.solidHitbox.x;
        int entityRightWorldX = entity.worldx + entity.solidHitbox.x + entity.solidHitbox.width;
        int entityTopWorldY = entity.worldy + entity.solidHitbox.y;
        int entityBottomWorldY = entity.worldy + entity.solidHitbox.y + entity.solidHitbox.height;
        int entityLeftCol = (entityLeftWorldX + entity.solidHitbox.width / 2) / gp.tileSize;
        int entityRightCol = (entityRightWorldX - entity.solidHitbox.width / 2) / gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;
        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
               entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {

        int index = 999;



        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                entity.solidHitbox.x = entity.worldx + entity.solidHitbox.x;
                entity.solidHitbox.y = entity.worldy + entity.solidHitbox.y;

                gp.obj[i].solidHitbox.x = gp.obj[i].worldX + gp.obj[i].solidHitbox.x;
                gp.obj[i].solidHitbox.y = gp.obj[i].worldY + gp.obj[i].solidHitbox.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidHitbox.y -= entity.speed;
                        if (entity.solidHitbox.intersects(gp.obj[i].solidHitbox)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidHitbox.y += entity.speed;
                        if (entity.solidHitbox.intersects(gp.obj[i].solidHitbox)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidHitbox.x -= entity.speed;
                        if (entity.solidHitbox.intersects(gp.obj[i].solidHitbox)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidHitbox.x += entity.speed;
                        if (entity.solidHitbox.intersects(gp.obj[i].solidHitbox)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidHitbox.x = entity.solidHitboxDefaultX;
                entity.solidHitbox.y = entity.solidHitboxDefaultY;
                gp.obj[i].solidHitbox.x = gp.obj[i].solidHitboxDefaultX;
                gp.obj[i].solidHitbox.y = gp.obj[i].solidHitboxDefaultY;
            }
        }
        return index;
    }
}