package stef.asteroidchallenge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import stef.asteroidchallenge.screen.AbstractScreen;
import stef.asteroidchallenge.screen.MenuScreen;

public class AsteroidGame extends Game {

	//maintains a static reference to itself in order for other classes to be able to switch screens.
	private static AsteroidGame game;

	//a default style to use for labels
	public static Label.LabelStyle labelStyle;
	public static BitmapFont gameFont;

	@Override
	public void create () {
		//im using multiplexer to allow all stages/screens a chance to react to user input
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		setActiveScreen(new MenuScreen());

		labelStyle = new Label.LabelStyle();
		//default font created is size 15 arial included in the libgdx libraries

		//font creation, each line is self explanatory
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("bangers.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameters.size = 30;
		fontParameters.color = Color.WHITE;
		fontParameters.borderWidth = 2;
		fontParameters.borderColor = Color.BLACK;
		fontParameters.borderStraight = true;
		fontParameters.minFilter = Texture.TextureFilter.Linear;
		fontParameters.magFilter = Texture.TextureFilter.Linear;
		gameFont = fontGenerator.generateFont(fontParameters);

		labelStyle.font = gameFont;
	}

	public AsteroidGame(){
		game = this;
	}

	public static void setActiveScreen(AbstractScreen screen){
		game.setScreen(screen);
	}
}
