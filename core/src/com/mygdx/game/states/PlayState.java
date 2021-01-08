package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyGame;

import sprites.Bird;
import sprites.Tube;

/**
 * the play state for when the game starts, inherets from th state class
 */
public class PlayState extends State{

    private static final int TUBESPACING = 125;
    private static final int TUBECOUNT = 4;
    private static final int GROUNDYOFFSET = -30;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes; // tubes are in an array so that there can be multiple tubes on screen at once

    /**
     * the constructor for the class
     * @param gsm GameStateManager to manage the state
     */
    public PlayState(GameStateManager gsm) {
        super(gsm); // as in the super class, places the gsm into the state

        //set up the birds location, background and camera location
        bird = new Bird(50,300);
        cam.setToOrtho(false, FlappyGame.WIDTH /2, FlappyGame.HEIGHT/2);
        bg = new Texture("bg.png");

        //places 2 grounds that move to give illusion of movement
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2,GROUNDYOFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUNDYOFFSET);

        // makes 4 tubes and places them at a distance with their set spacing
        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBECOUNT; i++){
            tubes.add(new Tube(i * (TUBESPACING + Tube.TUBEWIDTH)));
        }
    }

    /**
     * what occurs when a touch is detected on the screen, the bird jumps
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    /**
     * what happens on every frame, takes in the current time as well
     *
     * the following happens on every frame:
     * 1. detects if the bird should jump
     * 2. moves the ground
     * 3. update the birds position
     * 4. updates the cameras position
     * 5. places the tubes on screen, and updates their position if they go off screen
     * 6. detects collision between bird and the tubes as well as the gtound
     *
     * @param dlt delta time as a float
     */
    @Override
    public void update(float dlt) {

        //input handling and position managing
        handleInput();
        updateGround();
        bird.update(dlt);
        cam.position.x = bird.getPosition().x + 80;

        // places the tubes on screen and detects if the bird has collided with it
        for(Tube tube: tubes){

            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBEWIDTH + TUBESPACING) * TUBECOUNT));
            }

            if(tube.collides(bird.getHitBox())){
                gsm.set(new PlayState(gsm));
                break;
            }
        }

        // ground collision detection
        if(bird.getPosition().y <= ground.getHeight() + GROUNDYOFFSET){
            gsm.set(new PlayState(gsm));
        }

        // updates the cameras position
        cam.update();
    }


    /**
     * takes in a sprite batch and renders everything on screen
     * @param sb SpriteBatch all images and animations to be used
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined); // moves the camera

        // start to draw all the images on screen like the background, bird
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(),bird.getPosition().x,bird.getPosition().y);

        //places each tube made from the tubes array
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        //place the ground
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    /**
     * disposes all the images made to prevent memory leak
     */
    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();

        //must delete each tube in the array one by one
        for(Tube tube : tubes){
            tube.dispose();
        }

        System.out.println("Play State Disposed");
    }

    /**
     * moves the ground once it has went off screen
     */
    private void updateGround(){

        //move first ground if off screen
        if(cam.position.x - (cam.viewportWidth/2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }

        //move second ground if off screen
        if(cam.position.x - (cam.viewportWidth/2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

}
