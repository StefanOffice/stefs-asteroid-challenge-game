package stef.asteroidchallenge.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.util.AnimationCreator;

public class Laser extends RootActor{

    public Laser(float x, float y, Stage stage){
        super(x,y,stage);
        setAnimation(AnimationCreator.createAnimationFromSheet("laser.png", 2, 1, 0.05f, true));

        resize(0.3f);
        setOpacity(0);
        addAction(Actions.sequence(
                Actions.delay(0.05f),
                Actions.fadeIn(0.2f),
                Actions.delay(0.5f),
                Actions.fadeOut(0.3f),
                Actions.removeActor()));

        setSpeed(400);
        setMaxSpeed(400);
        setDeceleration(0);
    }

    @Override
    public void act(float dt){
        super.act(dt);
        applyMovement(dt);
        wrapAroundWorld();
    }
}
