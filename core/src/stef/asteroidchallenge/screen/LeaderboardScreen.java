package stef.asteroidchallenge.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.Player;
import stef.asteroidchallenge.actor.Background;
import stef.asteroidchallenge.actor.RootActor;
import stef.asteroidchallenge.util.AnimationCreator;
import stef.asteroidchallenge.util.ScoreManager;

public class LeaderboardScreen extends AbstractScreen {


    @Override
    public void initialize() {
        Background layer2 = new Background("backgrounds/space-2.png", mainStage, true);

        RootActor leaderboardTitle = new RootActor(0,0);
        leaderboardTitle.setAnimation(AnimationCreator.loadTexture("text/leaderboard-title.png"));
        Player[] bestPlayers = ScoreManager.getBestPlayers();
        Arrays.sort(bestPlayers);
        DateFormat formatter = new SimpleDateFormat("dd/MM/YY");

        //add all the elements to the uiTable defined in ScreenCore
        uiTable.pad(10).padLeft(150).padRight(150);
        uiTable.add(leaderboardTitle).colspan(4);

        uiTable.row();
        uiTable.add(new Label("Place", AsteroidGame.labelStyle));
        uiTable.add(new Label("Name",AsteroidGame.labelStyle)).left().padLeft(30).expandX();
        uiTable.add(new Label("Score",AsteroidGame.labelStyle)).padRight(30);
        uiTable.add(new Label("Date",AsteroidGame.labelStyle));

        //add a new row and populate the columns for each player
        for (int i = 0; i < bestPlayers.length; i++) {
            uiTable.row();
            uiTable.add(new Label(i+1+".",AsteroidGame.labelStyle));
            uiTable.add(new Label(bestPlayers[i].name,AsteroidGame.labelStyle)).left().padLeft(30);
            uiTable.add(new Label(bestPlayers[i].score + "",AsteroidGame.labelStyle)).padRight(30);
            uiTable.add(new Label(formatter.format(bestPlayers[i].date),AsteroidGame.labelStyle));
        }

        //standard button setup see MenuScreen for details
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new Texture("text/btn-mainmenu.png"));
        buttonStyle.over = new TextureRegionDrawable(new Texture("text/btn-mainmenu-over.png"));
        Button menuBtn = new Button(buttonStyle);
        menuBtn.addListener(e -> {
            if (!isTouchDownEvent(e))
                return false;
            AsteroidGame.setActiveScreen(new MenuScreen());
            return false;
        });

        uiTable.row();
        uiTable.add(menuBtn).colspan(4).bottom().expandY();
    }

    @Override
    public void update(float dt) {

    }
}

