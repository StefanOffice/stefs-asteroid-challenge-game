package stef.asteroidchallenge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

import stef.asteroidchallenge.screen.AbstractScreen;
import stef.asteroidchallenge.screen.MenuScreen;
import stef.asteroidchallenge.util.ScoreManager;

public class AsteroidGame extends Game {

	//maintains a static reference to itself in order for other classes to be able to switch screens.
	private static AsteroidGame game;

	//used to set screen resolution
	private static int gameWidth;
	private static int gameHeight;

	//a default style to use for labels
	public static Label.LabelStyle labelStyle;
	public static BitmapFont gameFont;
	public static Skin skin;

	public AsteroidGame(){
		this(1280, 720);
	}

	public AsteroidGame(int gameWidth, int gameHeight){
		setGameWidth(gameWidth);
		setGameHeight(gameHeight);
		game = this;
	}


	@Override
	public void create () {
		//im using multiplexer to allow all stages/screens a chance to react to user input
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		setActiveScreen(new MenuScreen());
		//used for labels to reduce boilerplate code
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

		//skin is used for creating some of the uiElements
		skin = new Skin(Gdx.files.internal("default-skin/uiskin.json"));

		ScoreManager.loadHighScores("leaderboard.txt");
	}

	public static void setActiveScreen(AbstractScreen screen){
		game.setScreen(screen);
	}

	@Override
	public void dispose() {
		super.dispose();
		//when the game is getting ready to be closed save the scores to the file
		ScoreManager.saveHighScores("leaderboard.txt");
	}

	public static int getGameWidth() {
		return gameWidth;
	}

	public static void setGameWidth(int gameWidth) {
		AsteroidGame.gameWidth = gameWidth;
	}

	public static int getGameHeight() {
		return gameHeight;
	}

	public static void setGameHeight(int gameHeight) {
		AsteroidGame.gameHeight = gameHeight;
	}
}
