package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject{

    GamePanel gp;

    public OBJ_Chest(GamePanel gp) {

        this.gp = gp;

        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/objects/chest.png"));
            util.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
