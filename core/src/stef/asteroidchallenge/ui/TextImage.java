package stef.asteroidchallenge.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;

import stef.asteroidchallenge.actor.RootActor;
import stef.asteroidchallenge.util.AnimationCreator;

public class TextImage extends RootActor {


    public TextImage(float x, float y, Stage s) {
        super(x, y, s);
    }

    public TextImage(float x, float y) {
        super(x, y);
    }

    public TextImage(String texturePath, int rotationDeg, float resizeFactor){
        super(0,0);
        setAnimation(AnimationCreator.loadTexture(texturePath));
        setRotation(rotationDeg);
        resize(resizeFactor);
    }
}
