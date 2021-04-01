package stef.asteroidchallenge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import stef.asteroidchallenge.screen.AbstractScreen;
import stef.asteroidchallenge.screen.MenuScreen;

public class AsteroidGame extends Game {

	//maintains a static reference to itself in order for other classes to be able to switch screens.
	private static AsteroidGame game;

	@Override
	public void create () {
		//im using multiplexer to allow all stages/screens a chance to react to user input
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		setActiveScreen(new MenuScreen());
	}

	public AsteroidGame(){
		game = this;
	}


	public static void setActiveScreen(AbstractScreen screen){
		game.setScreen(screen);
	}
}
