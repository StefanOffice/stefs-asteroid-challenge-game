package stef.asteroidchallenge.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

import stef.asteroidchallenge.util.AnimationCreator;

//used as a part of the spaceship to display fire when the player is accelerating
public class Thruster extends RootActor{

    public Thruster(float x, float y){
        super(x,y);
        setAnimation(AnimationCreator.createAnimationFromFiles(new String[]{"thrusters-1.png", "thrusters-2.png"},0.1f, true));

        resize(0.2f);
    }
}
