package tile;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[50];
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

        setup(0, "grass00", false);
        setup(1, "grass00", true);
        setup(2, "grass00", true);
        setup(3, "grass00", false);
        setup(4, "grass00", true);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water01", false);
        setup(15, "water03", true);
        setup(16, "grass00", false);
        setup(17, "grass00", false);
        setup(18, "grass00", false);
        setup(19, "grass00", false);
        setup(20, "grass00", false);
        setup(21, "grass00", false);
        setup(22, "grass00", false);
        setup(23, "grass00", true);
        setup(24, "grass00", true);
        setup(25, "grass00", false);
        setup(26, "grass00", false);
        setup(27, "grass00", true);
        setup(28, "grass00", false);
        setup(29, "grass00", false);
        setup(30, "grass00", false);
        setup(31, "grass00", true);
        setup(32, "grass00", false);
        setup(33, "grass00", false);
        setup(34, "grass00", false);
        setup(35, "grass00", false);
        setup(36, "grass00", true);
        setup(37, "grass00", false);
        setup(38, "grass00", false);
        setup(39, "grass00", true);
        setup(40, "grass00", false);
        setup(41, "grass00", false);
        setup(42, "grass00", true);
        setup(43, "grass00", false);
        setup(44, "grass00", false);
        setup(45, "grass00", false);
        setup(46, "grass00", false);
        setup(47, "grass00", true);
        setup(48, "grass00", false);
        setup(49, "grass00", false);
        setup(50, "grass00", false);
        setup(51, "grass00", false);

    }
    public void setup(int index, String imagePath, boolean collision) {

        Utility util = new Utility();

        try {
            tile[index] = new Tile();
            tile[index].image = loadImage("/assets/tiles/" + imagePath +".png");
            tile[index].image = util.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;


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
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
