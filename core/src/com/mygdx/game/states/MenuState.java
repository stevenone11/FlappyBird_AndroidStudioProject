package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyGame;

/**
 * the menu state before the game starts
 */
public class MenuState extends State {

    Texture background;
    Texture playButton;

    /**
     * the constructor method that initializes the menu
     * @param gsm GameStateManager
     */
    public MenuState(GameStateManager gsm) {
        super(gsm);

        //repositions camera and places the background and playbutton
        cam.setToOrtho(false, FlappyGame.WIDTH /2, FlappyGame.HEIGHT/2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");

    }

    /**
     * if the screen is touched, the game will start
     */
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    /**
     * what happens on each frame is to simply detect if the screen is touched
     * @param dlt delta time as a float
     */
    @Override
    public void update(float dlt) {
        handleInput();
    }

    /**
     * renders everything on screen, simply the camera position, background and play button
     * @param sb the SpriteBach all images to be used
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playButton, cam.position.x - playButton.getWidth()/2, cam.position.y);
        sb.end();
    }

    /**
     * deletes al images to prevent memory leak
     */
    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        System.out.println("Menu State disposed");
    }
}
