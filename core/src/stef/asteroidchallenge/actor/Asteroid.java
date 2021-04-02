package stef.asteroidchallenge.actor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.util.AnimationCreator;

public class Asteroid extends RootActor{

    public static final String[] ASTEROID_FILE_NAMES = {"asteroid-1.png","asteroid-2.png","asteroid-3.png","asteroid-4.png"};

    public Asteroid(float x, float y, Stage stage){
        super(x,y, stage);
        //pick a random image to use as a texture
        setAnimation(AnimationCreator.loadTexture(ASTEROID_FILE_NAMES[(int)(Math.random() * 4)]));
        float rotationVariance = MathUtils.random(30);
        float sizeVariance = MathUtils.random(0.4f);
        float speedVariance = MathUtils.random(50f);
        //add rotation based on basevalue and random variance generated above
        addAction(Actions.forever(Actions.rotateBy(30 + rotationVariance,1)));
        //set the size of asteroid with base value 0.4 and random size variance generated above
        resize(0.4f + sizeVariance);
        setSpeed(50 + speedVariance);
        setMaxSpeed(400);
        setDeceleration(0);

        setMotionAngle(MathUtils.random(360));
    }

    @Override
    public void act(float dt){
        super.act(dt);
        applyMovement(dt);
    }
}
