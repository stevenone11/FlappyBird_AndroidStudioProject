package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * to manage states for example to layer pause in front of the game and then focus on that state
 * instead of the game state
 */
public class GameStateManager {
    private Stack<State> states;

    /**
     * constructor function (no arguments needed)
     * simply creates a stack of all states that will be placed on top of one another
     */
    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * to push a state on top of another state
     * @param state the current state that overlaps
     */
    public void push(State state){
        states.push(state);
    }

    /**
     * pops and disposes the current state
     */
    public void pop(){
        states.pop().dispose();
    }

    /**
     * gets rid of the last element and additionally, replaces it with a new one
    * does not return anything
     */
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    /**
     * updates the state at the top of the stack
     * @param dt delta time as a float
     */
    public void update(float dt){
        states.peek().update(dt);
    }

    /**
     * renders the state at the top of the stack
     * @param sb SprietBatch that contains all images to be used
     */
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

}
