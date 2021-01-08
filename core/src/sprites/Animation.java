package sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * the animation class for the birds movement, dealing with frames and the amount of time a frame
 * is on screen
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime; // how long frame stays in view
    private float currentFrameTime;
    private int frameCount; // the total frames in the image
    private int frame;

    /**
     * constructor class that initializes all variables
     * @param region the entire texture images as a TextureRegion
     * @param frameCount the total frames in the animation as an int
     * @param cycleTime the total amount of time an animation is played
     */
    public Animation(TextureRegion region, int frameCount, float cycleTime) {

        // the frames
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        //looks through all regions of the frame region to make individual frames per region
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;

    }

    /**
     * updates the frame to the next one, resets if it reaches the end
     * @param dt (delta time as a float)
     */
    public void update(float dt){
        currentFrameTime += dt;

        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount) {
            frame = 0;
        }
    }

    /**
     * gets the current frame on screen
     * @return the current frame on screen as a TextureRegion
     */
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
