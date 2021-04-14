package stef.asteroidchallenge.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.lang.reflect.InvocationTargetException;

import stef.asteroidchallenge.AsteroidGame;
import stef.asteroidchallenge.screen.AbstractScreen;

public class NavigationButton extends Button {

    public <E extends AbstractScreen> NavigationButton(String upTexturePath, String overTexturePath, Class<E> targetScreenClass){
        super();
        ButtonStyle buttonStyle = new ButtonStyle();
        //set up the default button texture
        buttonStyle.up = new TextureRegionDrawable(new Texture(upTexturePath));
        //set up the texture to display when mouse is hovering over the button
        buttonStyle.over = new TextureRegionDrawable(new Texture(overTexturePath));
        //set the style
        setStyle(buttonStyle);
        setSize(getPrefWidth(), getPrefHeight());
        //setup what happens on mouse click
        //in this case it's just used for navigation between different screens
        addListener(e -> {
            if (!isTouchDownEvent(e)) {
                return false;
            }

            //must have a no-args constructor in each screen class for this to work
            try {
                AsteroidGame.setActiveScreen(targetScreenClass.getDeclaredConstructor().newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return false;
        });

    }

    public NavigationButton(String upTexturePath, String overTexturePath){
        super();
        ButtonStyle buttonStyle = new ButtonStyle();
        //set up the default button texture
        buttonStyle.up = new TextureRegionDrawable(new Texture(upTexturePath));
        //set up the texture to display when mouse is hovering over the button
        buttonStyle.over = new TextureRegionDrawable(new Texture(overTexturePath));
        //set the style
        setStyle(buttonStyle);
        setSize(getPrefWidth(), getPrefHeight());
    }

    /**
     * @param event - the event to be evaluated
     * @return - true if the specified event's type is touchDown
     */
    public static boolean isTouchDownEvent(Event event){
        return (event instanceof InputEvent) && ((InputEvent)event).getType().equals(InputEvent.Type.touchDown);
    }

}
