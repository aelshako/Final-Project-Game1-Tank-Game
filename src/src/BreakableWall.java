package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    private int health = 100;
    private static BufferedImage breakable_wall_img; //32x32
    private boolean dead = false;

    BreakableWall(int x, int y) {
        this.x = x;
        this.y = y;
        this.my_rectangle = new Rectangle(x, y, breakable_wall_img.getWidth(), breakable_wall_img.getHeight());
    }

    private void addHealth(int val) {  //We don't use this, but it has been included in case we add future functionality which may require it, and to follow good programming practices (this could potentially be used in the future if we decide to tick the health up over time in update())
        if (health + val > 100) {
            health = 100;
        } else {
            health += val;
        }

    }

     private void removeHealth(int val) { //private access because only the BreakableWall should be able to removeHealth(in its collision method)
        if (health - val < 0) {
            health = 0; //BreakableWall died
            dead = true;
        } else {
            health -= val;
        }
    }

    int getHealth() {
        return this.health;
    }

    boolean isDead() {
        return dead;
    }

    void setDead(boolean dead) {
        this.dead = dead;
    }

    static void set_breakable_wall_img(BufferedImage image) {
        BreakableWall.breakable_wall_img = image;
    }

    public void update() {

    }

    public void collision() {
        this.removeHealth(50);

    }

    public void drawImage(Graphics2D g2d) {

        if (!dead) {
            g2d.drawImage(breakable_wall_img, x, y, null);
        }

    }


}
