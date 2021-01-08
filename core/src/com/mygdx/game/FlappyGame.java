package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

/**
 * the game class holding the entire game, details, states and what is in the game
 */
public class FlappyGame extends ApplicationAdapter {

	//the game details
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Flappy Bird";

	private GameStateManager gsm; // class controlling every state of the game
	private SpriteBatch batch; // all the images used in the game

	private Music music; // the music played throughout the game

	/**
	 * creates the game with the sprite batch (all animations),
	 * the game state manager to manage all states of the game,
	 * music
	 */
	@Override
	public void create () {
		batch = new SpriteBatch(); // all images and animations used
		gsm = new GameStateManager(); // game state manager for all states

		//play the music throughout the entirety of the game
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		Gdx.gl.glClearColor(1, 0, 0, 1); // initialize the background colors
		gsm.push(new MenuState(gsm)); // place a menu state on the stack of the game state manager
	}

	/**
	 * renders everything on screen
	 */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch); // places the batch file in the game
	}

	/**
	 * get rid of everything in the class
	 */
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
