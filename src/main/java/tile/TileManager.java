package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldRow];

        getTileImage();
        loadMap("/assets/maps/map01.txt");
    }

    private BufferedImage loadImage(String path) throws IOException {
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            throw new IOException("Resource not found: " + path);
        }
        return ImageIO.read(stream);
    }

    public void getTileImage () {

        try {

            tile[0] = new Tile();
            tile[0].image = loadImage("/assets/tiles/grass01.png");

            tile[1] = new Tile();
            tile[1].image = loadImage("/assets/tiles/wall.png");
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = loadImage("/assets/tiles/water00.png");
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = loadImage("/assets/tiles/earth.png");

            tile[4] = new Tile();
            tile[4].image = loadImage("/assets/tiles/tree.png");
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = loadImage("/assets/tiles/sand.png");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap (String Path) {

        try {
            InputStream is = getClass().getResourceAsStream(Path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col] [row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();


        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol] [worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldx + gp.player.screenX;
            int screenY = worldY - gp.player.worldy + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldx - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldx + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldy - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldy + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
