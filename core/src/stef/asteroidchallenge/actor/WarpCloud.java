package stef.asteroidchallenge.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.util.AnimationCreator;

public class WarpCloud extends RootActor{

    public WarpCloud(float x, float y, Stage stage){
        super(x,y,stage);
        setAnimation(AnimationCreator.createAnimationFromSheet("actors/warp-cloud.png", 4,5,0.06f, true));

        setScale(1.2f);
        setOpacity(0.7f);

        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));
    }
}
