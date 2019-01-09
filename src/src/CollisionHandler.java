package src;

import java.awt.*;
import java.util.ArrayList;

class CollisionHandler {
    //using default constructor

    ArrayList<GameObject> HandleCollisions(ArrayList<GameObject> game_objects) {

        for (int i = 0; i < game_objects.size(); i++) {

            for (int j = i; j < game_objects.size(); j++) {
                GameObject obj_at_i = game_objects.get(i);
                GameObject obj_at_j = game_objects.get(j);

                if (i != j) {


                    //NOTE: we are imposing a 5 update iteration delay in the Bullet class if it collided to ensure that the explosion image has sufficient time to display. So we check if collided is true in each if statement below involving bullets to ensure we don't call the bullet collision function more than once.
                    //this prevents us from having to remove the bullet once it collides, thus not giving us enough time to show the explosion image
                    if (obj_at_i instanceof Bullet && obj_at_j instanceof Tank && !(((Bullet) obj_at_i).getOwner().equals(((Tank) obj_at_j).getTag())) && !((Bullet) obj_at_i).collided) { // we make sure the bullet hasn't already collided(so we don't call collisions again)
                        if (obj_at_i.my_rectangle.intersects(obj_at_j.my_rectangle)) {
                            obj_at_i.collision();
                            ((Bullet) obj_at_i).setSmall_explosion(false); // we use large explosion image
                            obj_at_j.collision();
                        }


                    }
                    if (obj_at_i instanceof Tank && obj_at_j instanceof Bullet && !((Bullet) obj_at_j).getOwner().equals(((Tank) obj_at_i).getTag()) && !((Bullet) obj_at_j).collided) {
                        if (obj_at_i.my_rectangle.intersects(obj_at_j.my_rectangle)) {
                            ((Bullet) obj_at_j).setSmall_explosion(false); // we use large explosion image
                            obj_at_j.collision();
                            obj_at_i.collision();
                        }

                    }

                    if (((obj_at_j instanceof Bullet && obj_at_i instanceof BreakableWall && !((Bullet) obj_at_j).collided))) {
                        if (obj_at_i.my_rectangle.intersects(obj_at_j.my_rectangle)) {
                            obj_at_j.collision();
                            obj_at_i.collision();
                        }

                    }


                    if (obj_at_i instanceof Tank && obj_at_j instanceof BreakableWall) {
                        Rectangle r1 = ((Tank) obj_at_i).getOffsetBounds();
                        if (r1.intersects(obj_at_j.my_rectangle)) {

                            ((Tank) obj_at_i).setdont_move(true);

                        }

                    }

                    if (obj_at_i instanceof BreakableWall && obj_at_j instanceof Tank) {

                        Rectangle r2 = ((Tank) obj_at_j).getOffsetBounds();
                        if (r2.intersects(obj_at_i.my_rectangle)) {  //intersection occurs

                            ((Tank) obj_at_j).setdont_move(true);

                        }

                    }

                    if (obj_at_i instanceof Tank && obj_at_j instanceof PowerUp) {
                        if (obj_at_i.my_rectangle.intersects(obj_at_j.my_rectangle)) {
                            if (((PowerUp) obj_at_j).isHealthBoost) {
                                ((Tank) obj_at_i).setHealth(100);
                                System.out.println("health power up picked up");
                                game_objects.remove(j);

                            }
                            if (((PowerUp) obj_at_j).isSpeedBoost) {
                                ((Tank) obj_at_i).setSpeedBoost(System.currentTimeMillis()); //setting speed boost pick up time
                                ((Tank) obj_at_i).setSpeed_boosted(true); //turning on flag to denote that tank is speed boosted
                                System.out.println("Speed boost power up picked up");
                                game_objects.remove(j);
                            }
                        }

                    }
                }

            }
        }


        return game_objects;
    }
}
