package stef.asteroidchallenge.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

import stef.asteroidchallenge.util.AnimationCreator;

public class SpaceShip extends RootActor {

    private final Thrusters thrusters;
    private final Thrusters thrusters2;

    public SpaceShip(float x, float y, Stage stage) {
        super(x, y, stage);
        setAnimation(AnimationCreator.loadTexture("spaceship.png"));
        resize(0.2f);
        //overwrite the default speed settings(from RootActor)
        setAcceleration(50);
        setMaxSpeed(200);
        setDeceleration(50);
        //can't stand perfectly still in space :P
        setMinSpeed(4);

        thrusters = new Thrusters(0, 0);
        thrusters2 = new Thrusters(0,0);
        addActor(thrusters);
        addActor(thrusters2);
        //add thrusters behind the ship on x axis
        //and put vertical center of thrusters, on vertical center of the spaceship
        float heightCenter = getHeight() / 2 - thrusters.getHeight()/2;
        thrusters.setPosition(-thrusters.getWidth(), heightCenter - getHeight()/9f);
        thrusters2.setPosition(-thrusters2.getWidth(), heightCenter + getHeight()/9f);
    }

    @Override
    public void act(float dt){
        super.act(dt);
        //rotation speed
        float degreesPerSecond = 120;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rotateBy(degreesPerSecond * dt);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rotateBy(-degreesPerSecond * dt);
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            addForwardAcceleration();
            thrusters.setVisible(true);
            thrusters2.setVisible(true);
        } else {
            thrusters.setVisible(false);
            thrusters2.setVisible(false);
        }

        applyMovement(dt);
    }

}
