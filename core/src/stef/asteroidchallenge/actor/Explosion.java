package stef.asteroidchallenge.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

import stef.asteroidchallenge.util.AnimationCreator;

public class Explosion extends RootActor{

    public Explosion(float x, float y, Stage stage){
        super(x,y,stage);
        setAnimation(AnimationCreator.createAnimationFromSheet("explosion.png", 1 , 8, 0.05f,false));
    }

    public void act(float dt){
        super.act(dt);
        if(isAnimationFinished())
            remove();
    }
}
