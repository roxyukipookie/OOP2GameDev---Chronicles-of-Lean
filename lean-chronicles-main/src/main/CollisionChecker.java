package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity) {
        //worldX&Y coordinates of the players solidArea. Detects collision based on the solid area
        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        //column and row of the worldX&Y coordinates
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        //check 2 tiles for each direction, left and right of the solid area
        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                //subtract players solid area worldY from speed
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; //predicts where player is after moving
                tileNum1 = gp.objTileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.objTileManager.mapTileNum[entityRightCol][entityTopRow];

                //uses tileNum1 and tileNum2 as index to check if the tile is solid or not
                if(gp.objTileManager.tile[tileNum1].collision == true || gp.objTileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.objTileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.objTileManager.mapTileNum[entityRightCol][entityBottomRow];

                if(gp.objTileManager.tile[tileNum1].collision == true || gp.objTileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.objTileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.objTileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if(gp.objTileManager.tile[tileNum1].collision == true || gp.objTileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.objTileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.objTileManager.mapTileNum[entityRightCol][entityBottomRow];

                if(gp.objTileManager.tile[tileNum1].collision == true || gp.objTileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}