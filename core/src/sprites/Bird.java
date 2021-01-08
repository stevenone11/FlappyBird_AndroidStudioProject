package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * everything involving the bird such as its hitbox, position, movement, gravity, and animation
 * overtime
 */
public class Bird {

    private final static int GRAVITY = -15;
    private final static int MOVEMENT = 100; // speed at which the screen moves

    private Vector3 position;
    private Vector3 velocity;

    private Texture bird; // animation (image) of the bird
    private Rectangle birdHitBox;
    private Animation birdAnimation;
    private Texture texture;

    private Sound flap;

    /**
     * constructor method of the bird takes in the coordinates at spawn
     * @param x position at start of game as an int
     * @param y position at start of game as an int
     */
    public Bird(int x, int y){
        position = new Vector3(x,y,0); // though this is a vector 3, we do not need the z vector
        velocity = new Vector3(0,0,0); // no velocity at start of game
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        birdHitBox = new Rectangle(x,y,texture.getWidth() / 3,texture.getHeight()); // the hitbox generated around the bird
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    /**
     * the update method that happens on every frame
     * @param dt delta time, measurement of time throughout the game as a float
     */
    public void update(float dt){
        birdAnimation.update(dt);

        // first check of the bird os on the ground, and if so, then let it stay on the ground
        // by continuously adding gravity to it
        if(position.y > 0){
            velocity.add(0,GRAVITY,0);
        }

        // adds gravity to the velocity
        velocity.add(0,GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0); // allocates for the movement of the bird and applies gravity

        // makes sure the bird does not go below the ground
        if(position.y < 0){
            position.y = 0;
        }

        velocity.scl(1/dt); // reallocates for the scale of time
        birdHitBox.setPosition(position.x, position.y);
    }

    /**
     * gets the position
     * @return position as a vector3
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     *  gets the texture
     * @return the Texture of the bird
     */
    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    /**
     * the action of moving the bird upwards
     */
    public void jump(){
        velocity.y = 500;
        flap.play(.5f);
    }

    /**
     *  gets the bird's hit box
     * @return the bird hitbox as a Rectangle
     */
    public Rectangle getHitBox(){
        return birdHitBox;
    }

    /**
     * deletes the bird from memory to avoid memory leaks
     */
    public void dispose(){
        texture.dispose();
        flap.dispose();
    }
}
