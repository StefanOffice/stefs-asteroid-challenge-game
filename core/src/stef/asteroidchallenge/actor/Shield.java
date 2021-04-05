package stef.asteroidchallenge.actor;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.util.AnimationCreator;

public class Shield extends RootActor{

    private static final float STARTING_OPACITY = 0.65f;
    private int shieldPower;


    public Shield(float x, float y){
        super(x,y);
        setAnimation(AnimationCreator.loadTexture("shield.png"));
        //adjust texture size
        resize(2.2f);

        //make the shield appear to grow and shrink - pulse
        Action pulse = Actions.sequence(
                Actions.scaleTo(1.05f, 1.05f, 1),
                Actions.scaleTo(0.95f, 0.95f, 1));

        //and rotate
        addAction(Actions.forever(Actions.rotateBy(30, 1)));
        addAction(Actions.forever(pulse));
        setOpacity(STARTING_OPACITY);
        shieldPower = 100;
    }


    @Override
    public void act(float dt) {
        super.act(dt);
        setOpacity((shieldPower/100f)*STARTING_OPACITY);
    }

    /**
     * Modifies the shield power for the given amount,
     * negative values can be used to reduce power
     * and positive to increase
     */
    public void modifyPower(int amount){
        shieldPower += amount;
    }

    public int getPower() {
        return shieldPower;
    }
}
