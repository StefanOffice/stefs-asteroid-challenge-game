package stef.asteroidchallenge.actor;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.util.AnimationCreator;

// used to simplify background setup, and reduce reduntant code
// as each screen had a similar pattern for creating backgrounds
public class Background extends RootActor{

    public Background(float x, float y, Stage stage) {
        super(x,y,stage);
    }

    public Background(float x, float y) {
        super(x,y);
    }


    /** All in one constructor to combine most common actions when creating a background and reduce boilerplate code.
     * @param width - desired width of the background
     * @param height - desired height of the background
     * @param imagePath - path to the image to be used
     * @param stage - stage to which the background should be added
     * @param worldBound - true if this background will be used as boundary for actor movement
     */
    public Background(int width, int height, String imagePath, Stage stage, boolean worldBound){
        super(0,0, stage);
        setAnimation(AnimationCreator.loadTexture(imagePath));
        setSize(width, height);

        if(worldBound)
            RootActor.setWorldBounds(this);
    }

    /**
     * Assumes game width and height, then calls the full constructor
     */
    public Background(String imagePath, Stage stage, boolean worldBound){
        this(AsteroidGame.getGameWidth(), AsteroidGame.getGameHeight(), imagePath, stage, worldBound);
    }


    /**
     * Makes the background fade in and out continuously
     * @param startOpacity - starting opacity for the background
     * @param minOpacity - the minimum to which the opacity should be reduced when fading out
     * @param durationToReachMin - the duration over which opacity will go from max to min
     * @param maxOpacity - the maximum to which the opacity should be increased when fading in
     * @param durationToReachMax - the duration over which opacity will go from min to max
     */
    public void addFadeInAndOut(float startOpacity, float minOpacity, float durationToReachMin, float maxOpacity, float durationToReachMax){
        setOpacity(startOpacity);
        Action fadeInOut = Actions.sequence(
                Actions.alpha(maxOpacity, durationToReachMax),
                Actions.alpha(minOpacity, durationToReachMin));
        addAction(Actions.forever(fadeInOut));
    }
}