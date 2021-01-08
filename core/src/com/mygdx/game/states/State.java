package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * An abstract class that will be extended by other states per menu such as (game state, menu state, etc..)
 */
public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm; // the way we can place sub states such as pause state etc...

    /**
     * the constructor class for the state class, similar for every inheriting class
     * @param gsm takes in a GameStateManager object to control the state
     */
    protected State(GameStateManager gsm){
        this.gsm = gsm;
        this.cam = new OrthographicCamera(); // makes the camera
        mouse = new Vector3(); // the camera's location

    }

    protected abstract void handleInput(); // what occurs once an input is detected
    public abstract void update(float dlt); // takes in a delta time which is the differnce between each frame rendered
    public abstract void render(SpriteBatch sb); // all textures needed to be rendered into the screen
    public abstract void dispose(); // gets rid of everything once the game is over
}
