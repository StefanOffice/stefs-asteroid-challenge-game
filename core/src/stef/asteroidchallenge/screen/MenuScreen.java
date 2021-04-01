package stef.asteroidchallenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.util.AnimationCreator;
import stef.asteroidchallenge.actor.RootActor;


public class MenuScreen extends AbstractScreen {

    @Override
    public void initialize() {
        RootActor space = new RootActor(0,0, mainStage);
        space.setAnimation(AnimationCreator.loadTexture("space-1.png"));
        space.setSize(1280, 720);

        RootActor title = new RootActor(0,0, uiStage);
        title.setAnimation(AnimationCreator.loadTexture("title-menu.png"));

        RootActor msgStart = new RootActor(0, 0, uiStage);
        msgStart.setAnimation(AnimationCreator.loadTexture("msg-start.png"));
        msgStart.setScale(0.6f);
        msgStart.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(2), Actions.fadeIn(2))));


        uiTable.pad(10);
        uiTable.add(title);
        uiTable.row();
        uiTable.add().top().expandY();
        uiTable.row();
        uiTable.add(msgStart).top();
    }

    @Override
    public void update(float dt){}

    @Override
    public boolean keyDown(int keycode) {
        //if enter is pressed switch start the game
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            AsteroidGame.setActiveScreen(new LevelScreen());
        }
        return false;
    }

}
