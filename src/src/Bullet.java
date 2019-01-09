package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Bullet extends GameObject {
    private String bullet_owner; //which tank owns the bullet?
    private boolean isInActive = false;
    private boolean small_explosion = true; // this variable controls if we print the large explosion(for collisions with tank) or the small explosion for collisions with walls
    boolean collided = false; //denotes whether the bullet should explode
    static private BufferedImage bullet_img; //24x24
    static private BufferedImage explosion_img;
    static private BufferedImage big_explosion_img;
    private int iterations_since_collided = 0;

    String getOwner() {
        return this.bullet_owner;
    }

    void setOwner(String owner) {
        this.bullet_owner = owner;
    }

    boolean getIsInactive() {
        return this.isInActive;
    }

    void setIsInactive(boolean val) {
        this.isInActive = val;
    }

    static void setBufferedImage(BufferedImage img) { // used to set the static bullet image
        bullet_img = img;
    }

    static void setExplosionImage(BufferedImage exp) { // used to set the explosion image
        explosion_img = exp;
    }

    static void setBig_explosion_img(BufferedImage e){
        big_explosion_img = e;
    }

    void setSmall_explosion(boolean val){ //if val is true, we use the small explosion image, if val is false, we use the big explosion image
        this.small_explosion = val;
    }
    boolean getSmall_explosion(){
        return this.small_explosion;
    }

    Bullet(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.vx = (int) Math.round(3 * Math.cos(Math.toRadians(angle)));
        this.vy = (int) Math.round(3 * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
        this.my_rectangle = new Rectangle(x, y, bullet_img.getWidth(), bullet_img.getHeight());
    }

    public void update() {
        if (!collided) {  //if its not collided, keep moving, else dont move the bounds
            this.x = x + vx;
            this.y = y + vy;
            this.checkBorder(); //this allows us to mark the bullet for deletion if it is out of bounds
        } else {
            iterations_since_collided++;
        }
        this.my_rectangle.setLocation(x,y);
    }


    public void drawImage(Graphics2D g2d) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), bullet_img.getWidth() / 2.0, bullet_img.getHeight() / 2.0);

        if (collided && small_explosion) {

            g2d.drawImage(explosion_img, rotation, null);

            if (iterations_since_collided >= 5) { //this allows us to make sure that there is enough time to see the bullet exploding
                this.isInActive = true;
            }

        }else if(collided && !small_explosion){ // large explosion
            g2d.drawImage(big_explosion_img, rotation, null);

            if (iterations_since_collided >= 5) { //this allows us to make sure that there is enough time to see the bullet exploding
                this.isInActive = true;
            }
        } else {
            g2d.drawImage(bullet_img, rotation, null);
        }
    }

    public void collision() {
        collided = true;
    }

    private void checkBorder() { //this allows us to mark the bullet as inactive(so it can later be removed) if out of map bounds
        //variables used to identify limits
        int left_limit = 30;
        if (x < left_limit) {
            this.isInActive = true;
        }
        int right_limit = GameWorld.WORLD_WIDTH - 65;
        if (x >= right_limit) {
            this.isInActive = true;
        }
        int lower_limit = 40;
        if (y < lower_limit) {
            this.isInActive = true;
        }
        int upper_limit = GameWorld.WORLD_HEIGHT - 60;
        if (y >= upper_limit) {
            this.isInActive = true;
        }
    }
}
