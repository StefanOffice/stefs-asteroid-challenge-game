package stef.asteroidchallenge.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

import stef.asteroidchallenge.util.AnimationCreator;

public class SpaceShip extends RootActor {

    public SpaceShip(float x, float y, Stage stage) {
        super(x, y, stage);
        setAnimation(AnimationCreator.loadTexture("spaceship.png"));
        resize(0.2f);
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

    }

}
