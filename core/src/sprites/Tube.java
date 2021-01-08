package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * the class for the tube, collision, movement and positions
 */
public class Tube {

    //the width of the tubes
    public static final int TUBEWIDTH = 52;

    private static final int FLUCTUATION = 140; // amount a tube can move between 0 and 130
    private static final int TUBEGAP = 150; // the space between each tube
    private static final int LOWESTOPENING = 80; // lowest part the tube can be

    private Rectangle hitboxTop, hitboxBot; // the hit box of the tubes

    private Texture topTube, bottomTube; // the images of the tubes

    private Vector2 posTopTube, posBotTube; // the positions of the tubes
    private Random rand; //  random position that the tube will be in the y axis

    /**
     * after taking in the next position of a tube (x) it will create the top and bottom tubes
     * with a small consistient space between the tubes for the bird to fly through
     * @param x (the x position where a tube will spawn as a float)
     */
    public Tube(float x){
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random(); // random position for y axis

        posTopTube = new Vector2(x,rand.nextInt(FLUCTUATION) + TUBEGAP + LOWESTOPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBEGAP - bottomTube.getHeight());

        hitboxTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        hitboxBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    /**
     * getter method for the top tube texture
     * @return the Texture of the top tube
     */
    public Texture getTopTube() {
        return topTube;
    }

    /**
     * getter method for the bottom tube texture
     * @return the Texture of the bottom tube
     */
    public Texture getBottomTube() {
        return bottomTube;
    }

    /**
     * getter method for the top position of the tube
     * @return Vector2 of the position of the top tube
     */
    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    /**
     * getter method for the position of the bottom tube
     * @return Vector2 of the bottom tube
     */
    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    /**
     * moves the tubes overtime
     * @param x (the position x in the current time asa  float)
     */
    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBEGAP + LOWESTOPENING);
        posBotTube.set(x, posTopTube.y - TUBEGAP - bottomTube.getHeight());

        hitboxTop.setPosition(posTopTube.x, posTopTube.y);
        hitboxBot.setPosition(posBotTube.x, posBotTube.y);
    }

    /**
     * retuns true if the player has hit the top or bottom tube
     * @param playerbox (the hitbox of the player as a Rectangle)
     * @return boolean (true of player has collided with the top/bottom tube) false otherwise
     */
    public boolean collides(Rectangle playerbox){
        return playerbox.overlaps(hitboxBot) || playerbox.overlaps(hitboxTop);
    }

    /**
     * disposes of al the objects in the class to prevent memory leak (delete after off of screen)
     */
    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }

}
