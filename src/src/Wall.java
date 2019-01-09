package src;

import java.awt.*;
//import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {      //NOTE: this class is only used to handle border(unbreakable) walls and background walls(it handles both, because they have identical functionality), breakable walls are handled in the BreakableWall class

    private boolean is_background;          //denotes if the wall is a background wall
    private static BufferedImage background_img;
    private static BufferedImage unbreakable_wall_img; //32x32


    public static void set_unbreakable_wall_img(BufferedImage image) {
        unbreakable_wall_img = image;
    }


    public static void setBackground_img(BufferedImage image) {
        background_img = image;
    }

    public Wall() {

    }

    public Wall(int x, int y, boolean is_background) {
        this.x = x;
        this.y = y;
        this.is_background = is_background;
        this.my_rectangle = new Rectangle(x, y, 32, 32);

    }

    public void update() {

    }

    public void collision() { //we only call this if the wall is breakable

    }

    public void drawImage(Graphics2D g2d) {

        if (this.is_background) { //it is a background image

            g2d.drawImage(background_img, x, y, null);

        } else {              //unbreakable wall
            g2d.drawImage(unbreakable_wall_img, x, y, null);
        }

    }


}
