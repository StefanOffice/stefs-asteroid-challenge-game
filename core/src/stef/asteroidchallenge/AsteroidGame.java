package stef.asteroidchallenge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class AsteroidGame extends Game {

	private static AsteroidGame game;

	@Override
	public void create () {
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
