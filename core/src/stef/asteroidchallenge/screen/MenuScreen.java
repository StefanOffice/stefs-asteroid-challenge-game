package stef.asteroidchallenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.ui.NavigationButton;
import stef.asteroidchallenge.actor.Background;
import stef.asteroidchallenge.util.AnimationCreator;
import stef.asteroidchallenge.actor.RootActor;


public class MenuScreen extends AbstractScreen {

    @Override
    public void initialize() {
        //Overlapping backgrounds fadding in and out to achieve a dynamic background effect
        Background layer1 = new Background( "backgrounds/space-2.png", mainStage, true);
        layer1.addFadeInAndOut(1f, 0, 5, 1, 5);
        Background layer2 = new Background( "backgrounds/starfield.png", mainStage, false);
        layer2.addFadeInAndOut(0.5f, 0.4f, 3, 0.7f, 3);
        Background layer3 = new Background("backgrounds/space-1.png", mainStage, false);
        layer3.addFadeInAndOut(0.5f, 0f, 6, 0.85f, 6);


        RootActor title = new RootActor(0,0, uiStage);
        title.setAnimation(AnimationCreator.loadTexture("text/title-menu.png"));

        NavigationButton btnLeaderboard = new NavigationButton("text/leaderboard-title.png","text/btn-leaderboard-over.png", LeaderboardScreen.class);
        NavigationButton btnControls = new NavigationButton("text/btn-controls.png","text/btn-controls-over.png", InfoScreen.class);

        RootActor msgStart = new RootActor(0, 0, uiStage);
        msgStart.setAnimation(AnimationCreator.loadTexture("text/msg-start.png"));
        msgStart.setScale(0.6f);
        msgStart.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(2), Actions.fadeIn(2))));


        uiTable.pad(10);
        uiTable.add(title);

        uiTable.row();
        uiTable.add().top().expandY();

        uiTable.row();
        uiTable.add(btnControls);

        uiTable.row();
        uiTable.add(btnLeaderboard);

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
