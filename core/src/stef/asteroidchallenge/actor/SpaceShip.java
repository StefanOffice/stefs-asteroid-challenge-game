package stef.asteroidchallenge.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

import stef.asteroidchallenge.util.AnimationCreator;

public class SpaceShip extends RootActor {

    private final Thruster thruster1;
    private final Thruster thruster2;
    private final Shield shield;

    public SpaceShip(float x, float y, Stage stage) {
        super(x, y, stage);
        setAnimation(AnimationCreator.loadTexture("spaceship.png"));
        resize(0.2f);
        //overwrite the default speed settings(from RootActor)
        setAcceleration(300);
        setMaxSpeed(250);
        setDeceleration(50);
        //can't stand perfectly still in space :P
        setMinSpeed(4);

        thruster1 = new Thruster(0, 0);
        thruster2 = new Thruster(0,0);
        addActor(thruster1);
        addActor(thruster2);
        //add thrusters behind the ship on x axis
        //and put vertical center of thrusters, on vertical center of the spaceship
        float heightCenter = getHeight() / 2 - thruster1.getHeight()/2;
        thruster1.setPosition(-thruster1.getWidth(), heightCenter - getHeight()/9f);
        thruster2.setPosition(-thruster2.getWidth(), heightCenter + getHeight()/9f);

        shield = new Shield(0,0);
        addActor(shield);
        //center the shield on the spaceship center
        //the shields position is relative to the ship therefore can't use center at actor
        shield.centerAtPosition(getWidth()/2, getHeight()/2);
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
            thruster1.setVisible(true);
            thruster2.setVisible(true);
        } else {
            thruster1.setVisible(false);
            thruster2.setVisible(false);
        }

        applyMovement(dt);
        wrapAroundWorld();
    }

    /**
     * Fires a laser in a direction that the spaceship is facing
     * that flies straight until it hits something or loses power
     */
    public void shoot(){
        if(getStage() == null)
            return;

        Laser laser = new Laser(0, 0, this.getStage());
        //set the laser staring position at the center of the ship
        laser.centerAtActor(this);
        //rotate it and set the movement angle so that it flies out in a straight line
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(this.getRotation());

    }

    public Shield getShield() {
        return shield;
    }
}
