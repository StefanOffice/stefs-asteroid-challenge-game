package stef.asteroidchallenge.actor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.util.AnimationCreator;

public class Asteroid extends RootActor{

    public static final String[] ASTEROID_FILE_NAMES = {"actors/asteroid-1.png","actors/asteroid-2.png","actors/asteroid-3.png","actors/asteroid-4.png"};

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

    /** Specialized constructor used when breaking an asteroid
     * to simplify the process of spawning new asteroids
     * @param parent - the asteroid from which new asteroids are formed
     * @param motionAngle - angle of motion for the new asteroid
     */
    public Asteroid(Asteroid parent, float motionAngle, Stage stage){
        this(0,0, stage);
        //mostly doubling the parent values, and reducing the size to half the parent size
        while(getWidth() * 2 > parent.getWidth())
            resize(0.8f);
        setSpeed(parent.getSpeed()*2);
        setDeceleration(5);
        setMotionAngle(motionAngle);
        centerAtActor(parent);
        setMinSpeed(250);
    }

    @Override
    public void act(float dt){
        super.act(dt);
        applyMovement(dt);
        wrapAroundWorld();
    }
}
