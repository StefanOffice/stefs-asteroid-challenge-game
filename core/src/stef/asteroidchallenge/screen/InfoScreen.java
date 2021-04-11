package stef.asteroidchallenge.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.actor.Background;
import stef.asteroidchallenge.actor.RootActor;
import stef.asteroidchallenge.ui.NavigationButton;
import stef.asteroidchallenge.ui.TextImage;
import stef.asteroidchallenge.util.AnimationCreator;

public class InfoScreen extends AbstractScreen{

    @Override
    public void initialize() {
        //pretty standard UI setup, see MenuScreen for details about elements used here
        //only new element here is a label, used to store text

        Background bg = new Background("backgrounds/space-2.png", mainStage, true);

        Label instructions = new Label("The objective of the game is to destroy all asteroids. " +
                "Six lasers are initially available and they recharge over time. " +
                "If you are in a tight spot try to warp out of it. " +
                "Warping is not without risk however, as there is a chance to warp to " +
                "the same position where an Asteroid is, and collide with it. Controls:", AsteroidGame.labelStyle);
        instructions.setWrap(true);
        Label rotateInfo = new Label("- rotate left/right", AsteroidGame.labelStyle);
        Label accelerateInfo = new Label("- accelerate forward", AsteroidGame.labelStyle);
        Label laserInfo = new Label("- shoot a laser in the direction that the ship is facing", AsteroidGame.labelStyle);
        laserInfo.setWrap(true);
        Label warpInfo = new Label("- warp to a random location", AsteroidGame.labelStyle);

        TextImage upArrow = new TextImage("keys/arrow.png", 90, 0.7f);
        TextImage leftArrow = new TextImage("keys/arrow.png", 180, 0.7f);
        TextImage rightArrow = new TextImage("keys/arrow.png", 0, 0.7f);
        TextImage laserKey = new TextImage("keys/space-key.png", 0, 0.7f);
        TextImage warpKey = new TextImage("keys/r-key.png", 0, 0.7f);

        NavigationButton btnMenu = new NavigationButton("text/btn-mainmenu.png", "text/btn-mainmenu-over.png", MenuScreen.class);

        uiTable.pad(20);
        uiTable.pack();
        float tableWidth = uiTable.getWidth();
        uiTable.add(instructions).width(tableWidth * 0.9f).colspan(3).expandY();

        uiTable.row();
        uiTable.add(upArrow).colspan(2).bottom();
        uiTable.add(accelerateInfo).left();

        uiTable.row();
        uiTable.add(leftArrow);
        uiTable.add(rightArrow);
        uiTable.add(rotateInfo).left().expandX();

        uiTable.row();
        uiTable.add(warpKey).colspan(2);
        uiTable.add(warpInfo).left();

        uiTable.row();
        uiTable.add(laserKey).colspan(2).padLeft(20);
        uiTable.pack();
        float columnWidt = uiTable.getColumnWidth(2);
        uiTable.add(laserInfo).left().width(columnWidt);

        uiTable.row();
        uiTable.add(btnMenu).colspan(3).bottom().expandY();
        uiTable.pack();
    }

    @Override
    public void update(float dt){}
}
