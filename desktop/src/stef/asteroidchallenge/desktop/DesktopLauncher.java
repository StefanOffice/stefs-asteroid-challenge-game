package stef.asteroidchallenge.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import stef.asteroidchallenge.AsteroidGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		//set this to false for windowed mode
		config.fullscreen = true;
		new LwjglApplication(new AsteroidGame(config.width, config.height), config);
	}
}
